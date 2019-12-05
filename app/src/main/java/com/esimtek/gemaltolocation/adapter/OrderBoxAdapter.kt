package com.esimtek.gemaltolocation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.esimtek.gemaltolocation.R
import com.esimtek.gemaltolocation.model.LocationOrder
import kotlinx.android.synthetic.main.item_order.view.*

class OrderBoxAdapter constructor(
        val list: List<LocationOrder.DataBean.TableBean>
) : RecyclerView.Adapter<OrderBoxAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.number.text = (position + 1).toString()
        holder.esl.text = "ESL条码：".plus(list[position].rfidLabel_EslCode)
        holder.time.text = list[position].statusTable_UpDataTime.substring(0..18).replace("T"," ")
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val number: TextView = view.tv_number
        val esl: TextView = view.tv_esl
        val time: TextView = view.tv_time

    }
}