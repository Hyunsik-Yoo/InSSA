package com.macgongmon.inssa.adapter

import android.content.Context
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import java.util.Collections

import com.macgongmon.inssa.R
import com.macgongmon.inssa.model.Score
import kotlinx.android.synthetic.main.main_listview.view.*

/**
 * Created by hyunsikyoo on 24/08/2017.
 */

class MainListAdapter(input: List<*>) : RecyclerView.Adapter<MainListAdapter.MainListHolder>() {
    private var listItems = input
    lateinit var font: Typeface
    lateinit var context: Context

    init {
        Collections.reverse(listItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListHolder {
        context = parent.context
        font = Typeface.createFromAsset(context.assets, "NotoSansCJKkr-Bold_0.otf")

        return MainListHolder(parent)
    }

    override fun onBindViewHolder(holder: MainListHolder, position: Int) {
        val current = listItems[position] as Score

        holder.date.typeface = font
        holder.count.typeface = font

        holder.date.text = current.date as String
        holder.count.text = current.count as String
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

    class MainListHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.main_listview, parent, false)) {
        val icon = itemView.main_list_icon
        val date = itemView.main_list_date
        val count = itemView.main_list_count
    }



}
