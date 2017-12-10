package com.macgongmon.inssa.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView


import java.util.ArrayList
import java.util.Collections

import butterknife.BindView
import butterknife.ButterKnife
import com.macgongmon.inssa.R

/**
 * Created by hyunsikyoo on 24/08/2017.
 */

class MainListAdapter(input: List<*>) : BaseAdapter() {
    var listItems: List<*>

    init {
        listItems = input
        Collections.reverse(listItems)
    }

    override fun getCount(): Int {
        return listItems.size
    }

    override fun getItem(position: Int): Any? {
        return listItems.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder

        // 폰트 가져오기 위해 부모 그룹 가져옴
        val context = parent.context
        val font = Typeface.createFromAsset(context.assets, "NotoSansCJKkr-Bold_0.otf")
        if (convertView != null) {
            holder = convertView.tag as ViewHolder
        } else {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.main_listview, parent, false)
            holder = ViewHolder(convertView)
            convertView!!.tag = holder
        }
        val current = listItems.get(position) as ArrayList<*>
        //holder.date = convertView.findViewById(R.id.main_list_date)
        holder.date!!.typeface = font

        //holder.count = convertView.findViewById(R.id.main_list_count)
        holder.count!!.typeface = font

        holder.date!!.text = current[0] as String
        holder.count!!.text = current[1] as String

        //holder.icon = convertView.findViewById(R.id.main_list_icon)
        if (getItem(position) == getItem(0)) {
            // 가장 첫번째 날짜는 글자 크기 크게
            holder.icon!!.setImageDrawable(ContextCompat.getDrawable(convertView.context, R.drawable.today))
            holder.date!!.setTypeface(font, Typeface.BOLD)
            holder.date!!.setTextColor(Color.parseColor("#0E9B63"))
            holder.date!!.textSize = 20f

            holder.count!!.setTypeface(font, Typeface.BOLD)
            holder.count!!.setTextColor(Color.parseColor("#0E9B63"))
            holder.count!!.textSize = 20f
            return convertView
        }

        if (position == count - 1) {
            holder.icon!!.setImageDrawable(ContextCompat.getDrawable(convertView.context, R.drawable.line))
            return convertView
        }

        val prev = listItems.get(position + 1) as ArrayList<*>

        /**
         * 과거데이터와 비교하여 증가했으면 상승아이콘
         * 동일하다면 flat아이콘
         * 감소했으면 감소아이콘
         */
        if (Integer.parseInt(current[1] as String) < Integer.parseInt(prev[1] as String))
            holder.icon!!.setImageDrawable(ContextCompat.getDrawable(convertView.context, R.drawable.down))
        else if (Integer.parseInt(current[1] as String) == Integer.parseInt(prev[1] as String))
            holder.icon!!.setImageDrawable(ContextCompat.getDrawable(convertView.context, R.drawable.line))
        else
            holder.icon!!.setImageDrawable(ContextCompat.getDrawable(convertView.context, R.drawable.up))

        return convertView
    }

    internal class ViewHolder(view: View) {
        var icon: ImageView = view.findViewById(R.id.main_list_icon)
        var date: TextView = view.findViewById(R.id.main_list_date)
        var count: TextView = view.findViewById(R.id.main_list_count)
    }


}
