package com.macgongmon.inssa.activity

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.TextView

import com.macgongmon.inssa.adapter.MainListAdapter
import com.macgongmon.inssa.R
import com.macgongmon.inssa.MainActivityMVP
import com.macgongmon.inssa.Presenter.MainActivityPresenter
import com.macgongmon.inssa.db.RealmHelper
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), MainActivityMVP.View, PopupMenu.OnMenuItemClickListener {

    private val TAG = javaClass.simpleName
    lateinit var presenter: MainActivityPresenter
    companion object {
        lateinit var realmHelper: RealmHelper
    }

    fun initFont() {
        val font = Typeface.createFromAsset(this.assets, "NotoSansCJKkr-Bold_0.otf")
        main_total.typeface = font
    }

    override fun setTotalScore(score: Int) {
        my_point.text = score.toString()
    }

    override fun getContext(): Context {
        return getContext()
    }

    override fun startSettingActivity() {
        val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
        startActivity(intent)
    }

    // 메뉴창 열리기
    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.main_activity_actions, popup.menu)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }

    override fun getTotalPoint(): Int {
        return Integer.parseInt(my_point.text.toString())
    }

    // RecyclerView LayoutManager 설정
    private fun initRecyclerView() {
        var layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.setOrientation(androidx.recyclerview.widget.LinearLayoutManager.VERTICAL)
        layoutManager.scrollToPosition(0)
        list_main_recycler.layoutManager = layoutManager

    }

    override fun showDeleteDialog() {
        AlertDialog.Builder(this)
                .setTitle("내 기록 삭제")
                .setMessage("정말로 나의 데이터를 삭제하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("삭제") { dialogInterface, i ->
                    presenter.onClickedMenuDelete()
                }
                .setNegativeButton("취소") { dialogInterface, i -> dialogInterface.cancel() }.show()
    }

    override fun showReadyDialog(){
        AlertDialog.Builder(this)
                .setMessage("준비중입니다.")
                .setCancelable(false)
                .setPositiveButton("확인"){dialogInterface, i -> dialogInterface.cancel() }.show()
    }


    private fun initEvent(){
        // 메뉴버튼 눌렀을때
        btn_menu.setOnClickListener { view ->
            showPopupMenu(view)
        }

        // 리스트뷰의 당겨서 새로고침 기능
        refresh_layout.setOnRefreshListener {
            presenter.refreshData()
            refresh_layout.isRefreshing = false // 로딩표시 제거
        }
    }

    private fun initData(){
        //DB로드
        Realm.init(this);
        realmHelper = RealmHelper()
        // DB로드(과거 데이터 로드)
        presenter.initRealm(realmHelper)
    }

    private fun initView(){
        presenter = MainActivityPresenter(this)

        initRecyclerView();
        initFont()

        // 메인화면의 리스트뷰 어댑터 설정
        // 초기화면에 토탈포인트 설정
        presenter.refreshData()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initView()
        initEvent()

    }

    override fun setAdapter() {
        val listViewAdapter = MainListAdapter(realmHelper.getAllData())
        list_main_recycler.adapter = listViewAdapter
        list_main_recycler.adapter?.notifyItemChanged(0)
    }


    override fun onMenuItemClick(item: MenuItem): Boolean {
        presenter.onMenuItemClick(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    /*
    // 자신의 점수를 서버에보내 상위 %를 알아내는 쓰레드
    private inner class threadScore : AsyncTask<Int, Int, String>() {
        internal var progressDialog: ProgressDialog
        internal var alertDialogBuilder: AlertDialog.Builder
        protected override fun doInBackground(vararg scores: Int): String? {
            var result: String? = null
            try {
                val str_url = "http://168.188.127.132:8000/inssa/score?score=" + scores[0]

                val url = URL(str_url)
                val urlConnection = url.openConnection() as HttpURLConnection
                val `in` = BufferedInputStream(urlConnection.inputStream)
                result = getStringFromInputStream(`in`)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPreExecute() {
            // 서버에서 응답이 오기까지 로딩 Dialog화면 만들어 놓기
            super.onPreExecute()
            progressDialog = ProgressDialog(this@MainActivity)
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progressDialog.setCancelable(false)
            progressDialog.setMessage("티어확인 진행중 입니다")
            progressDialog.show()

        }

        override fun onPostExecute(result: String) {
            /**
             * 서버에서 응답이와서 처리가 끝나면 로딩화면 숨기고
             * 결과를 알려주는 화면을 띄우기
             */
            var jsonObject: JSONObject? = null
            var percentage: Int? = -1
            var ranking: Int? = -1
            var total: Int? = -1
            var level: String? = null
            progressDialog.hide()
            try {
                jsonObject = JSONObject(result)
                percentage = jsonObject.getInt("percentage")
                ranking = jsonObject.getInt("ranking")
                total = jsonObject.getInt("total")

                if (percentage <= 30) {
                    level = "A"
                } else if (percentage <= 60) {
                    level = "B"
                } else {
                    level = "C"
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }


            alertDialogBuilder = AlertDialog.Builder(this@MainActivity)
            alertDialogBuilder.setMessage("당신은 전체 " + total + " 명중 " + ranking + "등으로 상위 " + percentage + "% 입니다\n" + level + "클래스에 속합니다!").setPositiveButton("확인") { dialogInterface, i -> dialogInterface.cancel() }.show()

            super.onPostExecute(result)
        }
    }
    */
}
