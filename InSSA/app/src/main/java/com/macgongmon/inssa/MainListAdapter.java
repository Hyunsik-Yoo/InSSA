package com.macgongmon.inssa;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hyunsikyoo on 24/08/2017.
 */

public class MainListAdapter extends BaseAdapter {
    private List listItems = new ArrayList();

    public MainListAdapter(List input) {
        listItems = input;
        Collections.reverse(listItems);
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        Typeface font = Typeface.createFromAsset(context.getAssets(), "NotoSansCJKkr-Bold_0.otf");
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.main_listview,parent,false);
        }
        ArrayList current = (ArrayList)listItems.get(position);

        ImageView icon = convertView.findViewById(R.id.main_list_icon);
        TextView date = convertView.findViewById(R.id.main_list_date);
        date.setTypeface(font);
        TextView count = convertView.findViewById(R.id.main_list_count);
        count.setTypeface(font);

        date.setText((String)current.get(0));
        count.setText((String)current.get(1));

        if(position == 0){
            // 가장 첫번째 날짜는 글자 크기 크게
            icon.setImageDrawable(ContextCompat.getDrawable(convertView.getContext(),R.drawable.today));
            date.setTypeface(font, Typeface.BOLD);
            date.setTextColor(Color.parseColor("#0E9B63"));
            date.setTextSize(20);

            count.setTypeface(font, Typeface.BOLD);
            count.setTextColor(Color.parseColor("#0E9B63"));
            count.setTextSize(20);
            return convertView;
        }

        if(position == getCount()-1){
            icon.setImageDrawable(ContextCompat.getDrawable(convertView.getContext(),R.drawable.line));
            return convertView;
        }

        ArrayList prev = (ArrayList)listItems.get(position+1);

        /**
         * 과거데이터와 비교하여 증가했으면 상승아이콘
         * 동일하다면 flat아이콘
         * 감소했으면 감소아이콘
          */
        if(Integer.parseInt((String)current.get(1)) < Integer.parseInt((String)prev.get(1)))
            icon.setImageDrawable(ContextCompat.getDrawable(convertView.getContext(),R.drawable.down));
        else if(Integer.parseInt((String)current.get(1)) == Integer.parseInt((String)prev.get(1)))
            icon.setImageDrawable(ContextCompat.getDrawable(convertView.getContext(),R.drawable.line));
        else
            icon.setImageDrawable(ContextCompat.getDrawable(convertView.getContext(),R.drawable.up));

        return convertView;
    }
}
