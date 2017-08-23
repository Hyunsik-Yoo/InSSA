package com.macgongmon.inssa;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    public final String TAG = getClass().getSimpleName();
    public static DBOpenHelper dbOpenHelper;
    Button btnSearch, btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        //startActivity(intent);
        //isServiceRunningCheck();

        dbOpenHelper = new DBOpenHelper(this).open();

        btnSearch = (Button)findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List result = new ArrayList<>(dbOpenHelper.getAllData());
                Iterator iterator = result.iterator();
                StringBuilder stringBuilder = new StringBuilder();

                while(iterator.hasNext()){
                    ArrayList<String> countPair = (ArrayList<String>)iterator.next();
                    stringBuilder.append("Date : " + countPair.get(0) + " Count : " + countPair.get(1)+"\n");
                }
                Toast.makeText(getApplicationContext(),stringBuilder.toString(),Toast.LENGTH_LONG).show();


            }
        });

        btnDelete = (Button)findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbOpenHelper.deleteAll();
                Log.d(TAG,"delete all");
            }
        });


    }

    public boolean isServiceRunningCheck() {
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            Log.d(TAG,service.service.getClassName());
        }
        return false;
    }
}
