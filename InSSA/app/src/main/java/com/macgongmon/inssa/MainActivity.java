package com.macgongmon.inssa;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity implements PopupMenu.OnMenuItemClickListener{

    public final String TAG = getClass().getSimpleName();
    public static DBOpenHelper dbOpenHelper;
    ImageButton btnMenu;
    ListView listView;
    SwipeRefreshLayout refreshLayout;
    TextView myPoint,mainTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface font = Typeface.createFromAsset(this.getAssets(), "NotoSansCJKkr-Bold_0.otf");




        dbOpenHelper = new DBOpenHelper(this).open();

        myPoint = (TextView)findViewById(R.id.my_point);
        listView = (ListView)findViewById(R.id.list_view);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_layout);
        btnMenu = (ImageButton)findViewById(R.id.btn_menu);
        mainTotal = (TextView)findViewById(R.id.main_total);


        mainTotal.setTypeface(font);
        myPoint.setTypeface(font);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });


        // 메인화면의 리스트뷰 어댑터 설정
        MainListAdapter listviewAdapter = new MainListAdapter(dbOpenHelper.getAllData());
        listView.setAdapter(listviewAdapter);

        // 리스트뷰의 당겨서 새로고침 기능
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NotificationListener.refresh();
                MainListAdapter listViewAdapter = new MainListAdapter(dbOpenHelper.getAllData());
                listView.setAdapter(listViewAdapter);
                myPoint.setText(dbOpenHelper.getMyPoint()+"");
                refreshLayout.setRefreshing(false); // 로딩표시 제거
            }
        });

        // 초기화면에 토탈포인트 설정
        myPoint.setText(dbOpenHelper.getMyPoint()+"");

        final TypedArray styledAttributes = getApplicationContext().getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize });
        int mActionBarSize = (int) styledAttributes.getDimension(0, 0);
        Log.d(TAG,mActionBarSize+"");
        styledAttributes.recycle();
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
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
                myPoint.setText(dbOpenHelper.getMyPoint()+"");
                break;

            case R.id.action_auth:
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
