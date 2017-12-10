package com.macgongmon.inssa.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView


import java.util.ArrayList
import java.util.Collections

import com.macgongmon.inssa.R

/**
 * Created by hyunsikyoo on 24/08/2017.
 */

class MainListAdapter(input: List<*>) : RecyclerView.Adapter<MainListAdapter.MainListViewHolder>() {
    var listItems = input
    lateinit var font: Typeface
    lateinit var context: Context

    init {
        Collections.reverse(listItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder {
        context = parent.context
        font = Typeface.createFromAsset(context.assets, "NotoSansCJKkr-Bold_0.otf")

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = inflater.inflate(R.layout.main_listview, parent, false)

        return MainListViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: MainListViewHolder, position: Int) {
        val current = listItems.get(position) as ArrayList<*>

        holder.date.typeface = font
        holder.count.typeface = font

        holder.date.text = current[0] as String
        holder.count.text = current[1] as String
        holder.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.up))
        /*
        if (getItem(position) == getItem(0)) {
            // 가장 첫번째 날짜는 글자 크기 크게
            holder.icon.setImageDrawable(ContextCompat.getDrawable(convertView.context, R.drawable.today))
            holder.date.setTypeface(font, Typeface.BOLD)
            holder.date.setTextColor(Color.parseColor("#0E9B63"))
            holder.date.textSize = 20f

            holder.count.setTypeface(font, Typeface.BOLD)
            holder.count.setTextColor(Color.parseColor("#0E9B63"))
            holder.count.textSize = 20f
            */
    }

    override fun getItemCount(): Int {
        return listItems.size
    }


    class MainListViewHolder(view: View): RecyclerView.ViewHolder(view){

        var icon: ImageView = view.findViewById(R.id.main_list_icon)
        var date: TextView = view.findViewById(R.id.main_list_date)
        var count: TextView = view.findViewById(R.id.main_list_count)
    }


}
