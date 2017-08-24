package com.macgongmon.inssa;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * Created by hyunsikyoo on 24/08/2017.
 */

public class MainListItem {
    private Drawable icon;
    private TextView date, count;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getDate() {
        return (String)date.getText();
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    public Integer getCount() {
        return Integer.parseInt(count.getText().toString());
    }

    public void setCount(TextView count) {
        this.count = count;
    }
}
