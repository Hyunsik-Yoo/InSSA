package com.macgongmon.inssa;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    public final String TAG = getClass().getSimpleName();
    public static DBOpenHelper dbOpenHelper;
    Button btnSearch, btnDelete;
    ListView listView;
    SwipeRefreshLayout refreshLayout;

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

        final MainListAdapter listviewAdapter = new MainListAdapter(dbOpenHelper.getAllData());
        //ArrayAdapter listviewAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,getAllItem());
        listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(listviewAdapter);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NotificationListener.refresh();
                listviewAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_mypoint:

            case R.id.action_refresh:

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public boolean isServiceRunningCheck() {
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            Log.d(TAG,service.service.getClassName());
        }
        return false;
    }

    public List getAllItem(){
        List itemList = new ArrayList(dbOpenHelper.getAllData());
        Iterator iterator = itemList.iterator();
        List result = new ArrayList();

        while(iterator.hasNext()){
            ArrayList<String> countPair = (ArrayList<String>)iterator.next();
            String date = countPair.get(0);
            String count = countPair.get(1);
            result.add(date + "\t - \t" + count + "점");
        }
        return result;
    }
}
