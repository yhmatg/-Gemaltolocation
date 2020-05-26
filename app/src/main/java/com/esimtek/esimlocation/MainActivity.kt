package com.esimtek.esimlocation

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.text.InputType
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.esimtek.esimlocation.model.*
import com.esimtek.esimlocation.network.Api
import com.esimtek.esimlocation.network.HttpClient
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.inputmethod.EditorInfo


class MainActivity : BaseActivity() {

    lateinit var searchView: SearchView
    lateinit var searchAutoComplete: SearchView.SearchAutoComplete

    private var input: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.inflateMenu(R.menu.toolbar_menu_search)
        //1023 start
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            if(input == null || "" == input){
                onBackPressed()
            }else{
                searchView.onActionViewCollapsed()
                searchView.imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI
            }
        })
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            val params = window.attributes
            params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
            window.attributes = params
        }
        //1023 end
        searchByOrder.setOnClickListener { locationByOrderNumber() }
        searchByCode.setOnClickListener { locationByPLCode() }
        refresh.setOnClickListener {
            searchView.setQuery("", true)
            location()
        }
        set.setOnClickListener { startActivity(Intent(this, SetActivity::class.java)) }
        location()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_search, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchAutoComplete = searchView.findViewById(R.id.search_src_text) as SearchView.SearchAutoComplete
        searchAutoComplete.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        searchAutoComplete.setHintTextColor(ContextCompat.getColor(this, android.R.color.white))
        //1023 start
        searchView.inputType = InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT
        //键盘不全屏
        searchView.imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI
        //1023 start
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchType.visibility = if (newText.isNullOrEmpty()) View.GONE else View.VISIBLE
                searchByOrder.text = "搜订单：".plus(newText)
                searchByCode.text = "搜条码：".plus(newText)
                input = newText
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun location() {
        hudDialog.show()
        HttpClient().provideRetrofit().create(Api::class.java).location().enqueue(object : Callback<LocationAll> {
            override fun onResponse(call: Call<LocationAll>, response: Response<LocationAll>) {
                hudDialog.dismiss()
                if (response.body()?.isSuccess == true) {
                    implantation1.text = "植入1：".plus(response.body()?.data?.implantationLine1
                            ?: "0")
                    implantation2.text = "植入2：".plus(response.body()?.data?.implantationLine2
                            ?: "0")
                    wait_implantation1.text = "待植入1：".plus(response.body()?.data?.waitImplantation1
                            ?: "0")
                    wait_implantation2.text = "待植入2：".plus(response.body()?.data?.waitImplantation2
                            ?: "0")
                    hot_stamping1.text = "烫印1：".plus(response.body()?.data?.hotstampingLine1
                            ?: "0")
                    hot_stamping2.text = "烫印2：".plus(response.body()?.data?.hotstampingLine2
                            ?: "0")
                    wait_package_line.text = "待包装：".plus(response.body()?.data?.waitPackaging
                            ?: "0")

                    wait_implantation1.visibility = View.VISIBLE
                    wait_implantation2.visibility = View.VISIBLE
                    hot_stamping1.visibility = View.VISIBLE
                    hot_stamping2.visibility = View.VISIBLE
                    implantation1.visibility = View.VISIBLE
                    implantation2.visibility = View.VISIBLE
                    wait_package_line.visibility = View.VISIBLE

                    wait_implantation1.isClickable = false
                    wait_implantation2.isClickable = false
                    hot_stamping1.isClickable = false
                    hot_stamping2.isClickable = false
                    implantation1.isClickable = false
                    implantation2.isClickable = false
                    wait_package_line.isClickable = false
                } else {
                    Toast.makeText(this@MainActivity, response.body()?.msg, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LocationAll>, t: Throwable) {
                hudDialog.dismiss()
                t.printStackTrace()
                Toast.makeText(this@MainActivity, getString(R.string.network_error), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun locationByOrderNumber() {
        hudDialog.show()
        HttpClient().provideRetrofit().create(Api::class.java).locationByOrderNumber(OrderNumber(input)).enqueue(object : Callback<LocationOrder> {
            override fun onResponse(call: Call<LocationOrder>, response: Response<LocationOrder>) {
                hudDialog.dismiss()
                if (response.body()?.isSuccess == true) {
                    searchView.clearFocus()
                    searchAutoComplete.clearFocus()
                    val implantation1List = response.body()?.data?.table?.filter { "ImplantationLine1" == it.rfidLabel_RealTimePosition }
                    val implantation2List = response.body()?.data?.table?.filter { "ImplantationLine2" == it.rfidLabel_RealTimePosition }
                    val waitImplantation1List = response.body()?.data?.table?.filter { "WaitImplantation1" == it.rfidLabel_RealTimePosition }
                    val waitImplantation2List = response.body()?.data?.table?.filter { "WaitImplantation2" == it.rfidLabel_RealTimePosition }
                    val hotStamping1List = response.body()?.data?.table?.filter { "HotstampingLine1" == it.rfidLabel_RealTimePosition }
                    val hotStamping2List = response.body()?.data?.table?.filter { "HotstampingLine2" == it.rfidLabel_RealTimePosition }
                    val waitPackagingList = response.body()?.data?.table?.filter { "WaitPackaging" == it.rfidLabel_RealTimePosition }

                    implantation1.text = "植入1：".plus(implantation1List?.size ?: 0)
                    implantation2.text = "植入2：".plus(implantation2List?.size ?: 0)
                    wait_implantation1.text = "待植入1：".plus(waitImplantation1List?.size ?: 0)
                    wait_implantation2.text = "待植入2：".plus(waitImplantation2List?.size ?: 0)
                    hot_stamping1.text = "烫印1：".plus(hotStamping1List?.size ?: 0)
                    hot_stamping2.text = "烫印2：".plus(hotStamping2List?.size ?: 0)
                    wait_package_line.text = "待包装：".plus(waitPackagingList?.size ?: 0)

                    implantation1.visibility = if (true == implantation1List?.isNotEmpty()) View.VISIBLE else View.GONE
                    implantation2.visibility = if (true == implantation2List?.isNotEmpty()) View.VISIBLE else View.GONE
                    wait_implantation1.visibility = if (true == waitImplantation1List?.isNotEmpty()) View.VISIBLE else View.GONE
                    wait_implantation2.visibility = if (true == waitImplantation2List?.isNotEmpty()) View.VISIBLE else View.GONE
                    hot_stamping1.visibility = if (true == hotStamping1List?.isNotEmpty()) View.VISIBLE else View.GONE
                    hot_stamping2.visibility = if (true == hotStamping2List?.isNotEmpty()) View.VISIBLE else View.GONE
                    wait_package_line.visibility = if (true == waitPackagingList?.isNotEmpty()) View.VISIBLE else View.GONE

                    implantation1.isClickable = true
                    implantation2.isClickable = true
                    wait_implantation1.isClickable = true
                    wait_implantation1.isClickable = true
                    hot_stamping1.isClickable = true
                    hot_stamping2.isClickable = true
                    wait_package_line.isClickable = true

                    val intent = Intent(this@MainActivity, ListActivity::class.java)
                    implantation1.setOnClickListener {
                        intent.putExtra("data", Gson().toJson(implantation1List))
                        startActivity(intent)
                    }
                    implantation2.setOnClickListener {
                        intent.putExtra("data", Gson().toJson(implantation2List))
                        startActivity(intent)
                    }
                    wait_implantation1.setOnClickListener {
                        intent.putExtra("data", Gson().toJson(waitImplantation1List))
                        startActivity(intent)
                    }
                    wait_implantation2.setOnClickListener {
                        intent.putExtra("data", Gson().toJson(waitImplantation2List))
                        startActivity(intent)
                    }
                    hot_stamping1.setOnClickListener {
                        intent.putExtra("data", Gson().toJson(hotStamping1List))
                        startActivity(intent)
                    }
                    hot_stamping2.setOnClickListener {
                        intent.putExtra("data", Gson().toJson(hotStamping2List))
                        startActivity(intent)
                    }
                    wait_package_line.setOnClickListener {
                        intent.putExtra("data", Gson().toJson(hotStamping2List))
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this@MainActivity, response.body()?.msg, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LocationOrder>, t: Throwable) {
                hudDialog.dismiss()
                t.printStackTrace()
                Toast.makeText(this@MainActivity, getString(R.string.network_error), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun locationByPLCode() {
        hudDialog.show()
        HttpClient().provideRetrofit().create(Api::class.java).locationByPLCode(PLCode(input)).enqueue(object : Callback<LocationCode> {
            override fun onResponse(call: Call<LocationCode>, response: Response<LocationCode>) {
                hudDialog.dismiss()
                if (response.body()?.isSuccess == true) {
                    searchView.clearFocus()
                    searchAutoComplete.clearFocus()
                    wait_implantation1.visibility = View.GONE
                    wait_implantation2.visibility = View.GONE
                    hot_stamping1.visibility = View.GONE
                    hot_stamping2.visibility = View.GONE
                    implantation1.visibility = View.GONE
                    implantation2.visibility = View.GONE
                    wait_package_line.visibility = View.GONE

                    when (response.body()?.data?.table?.get(0)?.rfidLabel_RealTimePosition) {
                        "ImplantationLine1" -> {
                            implantation1.text = "植入1"
                            implantation1.visibility = View.VISIBLE
                        }
                        "ImplantationLine2" -> {
                            implantation2.text = "植入2"
                            implantation2.visibility = View.VISIBLE
                        }
                        "WaitImplantation1" -> {
                            wait_implantation1.text = "待植入1"
                            wait_implantation1.visibility = View.VISIBLE
                        }
                        "WaitImplantation2" -> {
                            wait_implantation2.text = "待植入2"
                            wait_implantation2.visibility = View.VISIBLE
                        }
                        "HotstampingLine1" -> {
                            hot_stamping1.text = "烫印1"
                            hot_stamping1.visibility = View.VISIBLE
                        }
                        "HotstampingLine2" -> {
                            hot_stamping2.text = "烫印2"
                            hot_stamping2.visibility = View.VISIBLE
                        }
                        "WaitPackaging" -> {
                            wait_package_line.text = "待包装"
                            wait_package_line.visibility = View.VISIBLE
                        }
                    }

                    wait_implantation1.isClickable = false
                    wait_implantation2.isClickable = false
                    hot_stamping1.isClickable = false
                    hot_stamping2.isClickable = false
                    implantation1.isClickable = false
                    implantation2.isClickable = false
                    wait_package_line.isClickable = false
                } else {
                    Toast.makeText(this@MainActivity, response.body()?.msg, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LocationCode>, t: Throwable) {
                hudDialog.dismiss()
                t.printStackTrace()
                Toast.makeText(this@MainActivity, getString(R.string.network_error), Toast.LENGTH_SHORT).show()
            }
        })
    }
}
