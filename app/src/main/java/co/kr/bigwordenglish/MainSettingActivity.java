package co.kr.bigwordenglish;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import co.kr.bigwordenglish.common.CommonUtil;
import co.kr.bigwordenglish.obj.Mianobj;

public class MainSettingActivity extends AppCompatActivity {

	private LinearLayout mCategoryLay;
	private TextView mTitleTv;
	public LayoutInflater mLayoutInflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_set);

		// mLayoutInflater = (LayoutInflater)
		// getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// mCategoryLay = (LinearLayout) findViewById(R.id.category_lay);
		//
		//
		// MainListLayout(SELECT_MainLabel("EgDb.db" , ""), mCategoryLay);
		//
		//
		// findViewById(R.id.setting_btn).setOnClickListener(btnListener);

		((Button) findViewById(R.id.btn_setting)).setVisibility(View.GONE);
		((Button) findViewById(R.id.btn_home))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						CommonUtil.isHome = true;
						finish();
					}
				});
	}

	// 버튼 리스너 구현 부분
	View.OnClickListener btnListener = new View.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.setting_btn:
				Log.e("SKY", "-- setting_btn --");

				break;
			}
		}
	};

	private ArrayList<Mianobj> SELECT_MainLabel(String DB, String Where) // 디비 값
																			// 조회해서
																			// 저장하기
	{
		ArrayList<Mianobj> arr = new ArrayList<Mianobj>();
		try {
			// db파일 읽어오기
			SQLiteDatabase db = openOrCreateDatabase(DB, Context.MODE_PRIVATE,
					null);
			// 쿼리로 db의 커서 획득
			Cursor cur = db.rawQuery("SELECT * FROM `Main_Category`;", null);
			// 처음 레코드로 이동
			while (cur.moveToNext()) {
				// 읽은값 출력
				Log.e("SKY", cur.getString(0) + "/" + cur.getString(1));
				arr.add(new Mianobj(cur.getString(0), cur.getString(1)));
			}
			cur.close();
			db.close();

		} catch (SQLException se) {
			// TODO: handle exception
			Log.e("selectData()Error! : ", se.toString());
		}
		return arr;

	}

	private ArrayList<Mianobj> SELECT_SUBLabel(String DB, String Where) // 디비 값
																		// 조회해서
																		// 저장하기
	{
		ArrayList<Mianobj> arr = new ArrayList<Mianobj>();
		try {
			// db파일 읽어오기
			SQLiteDatabase db = openOrCreateDatabase(DB, Context.MODE_PRIVATE,
					null);
			// 쿼리로 db의 커서 획득
			String sql = "SELECT * FROM `Category_Sub` " + Where;
			Log.e("SKY", "sql :: " + sql);
			Cursor cur = db.rawQuery(sql, null);
			// 처음 레코드로 이동
			while (cur.moveToNext()) {
				// 읽은값 출력
				// [JSW] 여기서부터 처리해......
				Log.e("SKY", cur.getString(0) + "/" + cur.getString(1) + "/"
						+ cur.getString(2));
			}
			cur.close();
			db.close();

		} catch (SQLException se) {
			// TODO: handle exception
			Log.e("selectData()Error! : ", se.toString());
		}
		return arr;

	}


}
