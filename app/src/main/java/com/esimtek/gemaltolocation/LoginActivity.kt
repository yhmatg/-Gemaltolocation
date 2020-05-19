package com.esimtek.gemaltolocation

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.esimtek.gemalto.util.PreferenceUtil
import com.esimtek.gemaltolocation.model.LoggedBean
import com.esimtek.gemaltolocation.model.LoginBean
import com.esimtek.gemaltolocation.model.ResultBean
import com.esimtek.gemaltolocation.model.UserManagerBean
import com.esimtek.gemaltolocation.network.Api
import com.esimtek.gemaltolocation.network.HttpClient
import com.esimtek.gemaltolocation.util.EncryptionUtil
import com.esimtek.gemaltolocation.util.RegexUtil
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity(), PasswordModifyFragment.Listener {

    lateinit var userName: String
    //add 20191227 start
    private var savedName by PreferenceUtil("username", "")
    //add 20191227 end

    override fun onModifyClicked(password: String) {
        val unEncryptBuffer = StringBuffer()
        unEncryptBuffer.append(EncryptionUtil.SECRET_KEY).append(HttpClient.staffId).append(userName).append(password)
        val passwordBuffer = StringBuffer()
        unEncryptBuffer.toList().sorted().forEach { passwordBuffer.append(it) }
        val userManagerBean = UserManagerBean(userName, EncryptionUtil.encrypt(passwordBuffer.toString(), "MD5"), 2)
        userManager(userManagerBean)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        set.setOnClickListener { startActivity(Intent(this, SetActivity::class.java)) }
        login.setOnClickListener {
            userName = userNameEditText.text.toString().trim()
            if (userName.isEmpty()) {
                Toast.makeText(this@LoginActivity, "请输入用户名", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val password = passwordEditText.text.toString().trim()
            if (!RegexUtil.isPassword(password)) {
                Toast.makeText(this@LoginActivity, "密码不符合规范", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            login(userName, password)
        }
        //add 20191227 start
        userNameEditText.text = Editable.Factory.getInstance().newEditable(savedName);
        //add 20191227 end
    }

    private fun login(userName: String, password: String) {
        hudDialog.show()
        val unEncryptBuffer = StringBuffer()
        unEncryptBuffer.append(EncryptionUtil.SECRET_KEY).append(HttpClient.staffId).append(userName).append(password)
        val passwordBuffer = StringBuffer()
        unEncryptBuffer.toList().sorted().forEach { passwordBuffer.append(it) }
        val loginBean = LoginBean(userName, EncryptionUtil.encrypt(passwordBuffer.toString(), "MD5"))
        HttpClient().provideRetrofit().create(Api::class.java).login(loginBean).enqueue(object : Callback<LoggedBean> {
            override fun onResponse(call: Call<LoggedBean>, response: Response<LoggedBean>) {
                hudDialog.dismiss()
                if (response.body()?.isSuccess == true) {
                    GemaltoApplication.instance.logged = response.body()
                    val isPasswordExpire = response.body()?.data?.isPasswordExpire ?: false
                    if (isPasswordExpire) {
                        PasswordModifyFragment().show(supportFragmentManager, "PasswordModifyDialog")
                        return
                    }
                    //1005 更换设备登录
                    if (response.body()?.code == 1005) {
                        Toast.makeText(this@LoginActivity, response.body()?.msg, Toast.LENGTH_SHORT).show()
                    }
                    //add 20191227 start
                    savedName = userName;
                    //add 20191227 end
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, response.body()?.msg, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoggedBean>, t: Throwable) {
                hudDialog.dismiss()
                t.printStackTrace()
                Toast.makeText(this@LoginActivity, getString(R.string.network_error), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun userManager(bean: UserManagerBean) {
        hudDialog.show()
        HttpClient().provideRetrofit().create(Api::class.java).userManager(bean).enqueue(object : Callback<ResultBean> {
            override fun onResponse(call: Call<ResultBean>, response: Response<ResultBean>) {
                hudDialog.dismiss()
                if (response.body()?.isSuccess == true) {
                    Toast.makeText(this@LoginActivity, getString(R.string.modify_user_success), Toast.LENGTH_SHORT).show()
                    passwordEditText.setText("")
                } else {
                    Toast.makeText(this@LoginActivity, response.body()?.msg, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResultBean>, t: Throwable) {
                hudDialog.dismiss()
                t.printStackTrace()
                Toast.makeText(this@LoginActivity, getString(R.string.network_error), Toast.LENGTH_SHORT).show()
            }
        })
    }
}

