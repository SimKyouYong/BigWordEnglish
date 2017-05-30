package co.kr.bigwordenglish;

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

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyAdView;
import com.fsn.cauly.CaulyAdViewListener;

import java.util.ArrayList;

import co.kr.bigwordenglish.obj.Mianobj;

public class MainActivity extends AppCompatActivity implements CaulyAdViewListener{

	private LinearLayout mCategoryLay;
	private TextView mTitleTv;
	public LayoutInflater mLayoutInflater;


	private LinearLayout adWrapper = null;
	private CaulyAdView xmlAdView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		initCauly();
		onClickEvent();
	}
	private void initCauly(){
		// CloseAd 초기화
		CaulyAdInfo closeAdInfo = new CaulyAdInfoBuilder("modukcJI").build();
		// 선택사항: XML의 AdView 항목을 찾아 Listener 설정
		xmlAdView = (CaulyAdView) findViewById(R.id.xmladview);
		xmlAdView.setAdViewListener(this);

		adWrapper = (LinearLayout) findViewById(R.id.adWrapper);
	}

	@Override
	public void onReceiveAd(CaulyAdView adView, boolean isChargeableAd) {
		// 광고 수신 성공 & 노출된 경우 호출됨.
		// 수신된 광고가 무료 광고인 경우 isChargeableAd 값이 false 임.
		if (isChargeableAd == false) {
			Log.e("SKY", "free banner AD received.");
		}
		else {
			Log.e("SKY", "normal banner AD received.");
		}
	}

	@Override
	public void onFailedToReceiveAd(CaulyAdView adView, int errorCode, String errorMsg) {
		// 배너 광고 수신 실패할 경우 호출됨.
		Log.e("SKY", "failed to receive banner AD.");
		adWrapper.setVisibility(View.GONE);
	}

	@Override
	public void onShowLandingScreen(CaulyAdView adView) {
		// 광고 배너를 클릭하여 랜딩 페이지가 열린 경우 호출됨.
		Log.e("SKY", "banner AD landing screen opened.");
	}

	@Override
	public void onCloseLandingScreen(CaulyAdView adView) {
		// 광고 배너를 클릭하여 열린 랜딩 페이지가 닫힌 경우 호출됨.
		Log.e("SKY", "banner AD landing screen closed.");
	}
	private void onClickEvent() {
		((Button) findViewById(R.id.btn_setting))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent i = new Intent(MainActivity.this,
								MainSettingActivity.class);
						startActivity(i);
					}
				});
		((Button) findViewById(R.id.btn_menu1))
		.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this,
						MainWordSelect.class);
				startActivity(i);
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

	/**
	 * 메인 리스트 레이아웃
	 * 
	 * @param item
	 *            메인 리스트 데이터
	 * @param categorylay
	 *            메인 화면
	 */
	public void MainListLayout(final ArrayList<Mianobj> item,
			final LinearLayout categorylay) {

		categorylay.removeAllViews();

		for (int i = 0; i < item.size(); i++) {
			final int pos = i;
			View view = mLayoutInflater.inflate(R.layout.little_categorylist,
					null);

			mTitleTv = (TextView) view.findViewById(R.id.title_tv);
			mTitleTv.setText(item.get(i).getCategory());
			mTitleTv.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = null;
					// Toast.makeText(MainActivity.this, "클릭한 포지션 --> " + pos,
					// Toast.LENGTH_SHORT).show();
					Log.e("SKY",
							"item.get(pos).getCategory() :: "
									+ item.get(pos).getCategory());
					if (item.get(pos).getCategory().equals("영화")
							|| item.get(pos).getCategory().equals("드라마")) {
						// 영화나 드라마 일경우 디비셀렉 다시해서 재호출
						SELECT_SUBLabel("EgDb.db", "where Category_Sub_Key = '"
								+ item.get(pos).getKey_index() + "'");
						intent = new Intent(MainActivity.this,
								MainSubActivity.class);
						intent.putExtra("OBJ", item.get(pos).getKey_index());
						startActivity(intent);
					} else {
						intent = new Intent(MainActivity.this,
								MainDetailActivity.class);
						intent.putExtra("OBJ", item.get(pos));
						startActivity(intent);
						Toast.makeText(MainActivity.this,
								"이거슨 인텐트--> " + item.get(pos),
								Toast.LENGTH_SHORT).show();
					}

				}
			});

			categorylay.addView(view);
		}

	}
}
