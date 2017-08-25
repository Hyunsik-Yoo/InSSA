package com.macgongmon.inssa;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity{

    public final String TAG = getClass().getSimpleName();
    public static DBOpenHelper dbOpenHelper;
    ListView listView;
    SwipeRefreshLayout refreshLayout;
    TextView myPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPoint = (TextView)findViewById(R.id.my_point);
        //isServiceRunningCheck();

        MainListAdapter listviewAdapter = new MainListAdapter(dbOpenHelper.getAllData());
        listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(listviewAdapter);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NotificationListener.refresh();
                MainListAdapter listViewAdapter = new MainListAdapter(dbOpenHelper.getAllData());
                listView.setAdapter(listViewAdapter);
                myPoint.setText("나의 인싸지수 : " + dbOpenHelper.getMyPoint() +  "점");
                refreshLayout.setRefreshing(false); // 로딩표시 제거
            }
        });

        myPoint.setText("나의 인싸지수 : " + dbOpenHelper.getMyPoint() +  "점");



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder alertDialogBuilder;
        switch (item.getItemId()){
            case R.id.action_mypoint:
                alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("준비중입니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
                break;

            case R.id.action_delete_all:
                alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("내 기록 삭제");
                alertDialogBuilder
                        .setMessage("정말로 나의 데이터를 삭제하시겠습니까?")
                        .setCancelable(false)
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbOpenHelper.deleteAll();
                        Log.d(TAG,"delete all");
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
                NotificationListener.messageCount = 0;

            case R.id.action_refresh:
                NotificationListener.refresh();
                MainListAdapter listViewAdapter = new MainListAdapter(dbOpenHelper.getAllData());
                listView.setAdapter(listViewAdapter);
                myPoint.setText("나의 인싸지수 : " + dbOpenHelper.getMyPoint() +  "점");
                break;

            case R.id.action_auth:
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
