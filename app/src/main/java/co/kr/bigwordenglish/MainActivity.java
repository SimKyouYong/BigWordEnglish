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

import co.kr.bigwordenglish.common.DBManager;
import co.kr.bigwordenglish.common.VO_Item_Level_02;
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

	private void onClickEvent() {
		//설정 버튼
		((Button) findViewById(R.id.btn_setting)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MainSettingActivity.class);
				startActivity(i);
			}
		});

		((Button) findViewById(R.id.btn_menu1)).setOnClickListener(btnListenerLevel_02);
		((Button) findViewById(R.id.btn_menu2)).setOnClickListener(btnListenerLevel_02);
		((Button) findViewById(R.id.btn_menu3)).setOnClickListener(btnListenerLevel_02);
		((Button) findViewById(R.id.btn_menu4)).setOnClickListener(btnListenerLevel_02);
		((Button) findViewById(R.id.btn_menu5)).setOnClickListener(btnListenerLevel_02);
		((Button) findViewById(R.id.btn_menu6)).setOnClickListener(btnListenerLevel_02);
	}

	// 버튼 리스너 구현 부분
	View.OnClickListener btnListenerLevel_02 = new View.OnClickListener() {
		public void onClick(View v) {
			int subkey = 0;
			switch (v.getId()) {
				case R.id.btn_menu1:
					subkey = 1;
					break;
				case R.id.btn_menu2:
					subkey = 2;
					break;
				case R.id.btn_menu3:
					subkey = 3;
					break;
				case R.id.btn_menu4:
					subkey = 4;
					break;
				case R.id.btn_menu5:
					subkey = 5;
					break;
				case R.id.btn_menu6:
					subkey = 6;
					break;
			}
			onClickLevel_02(subkey);
		}
	};

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

	private void onClickLevel_02(int subkey){
		Intent i = new Intent(this, MainSubActivity.class);
		i.putExtra("SubKey", subkey+"");
		startActivity(i);

//		ArrayList<VO_Item_Level_02> temp = getItem_Level2(subkey);
//		for(int i=0; i<temp.size(); i++){
//			Log.v("ifeelbluu",temp.get(i).getLevel2_Index() + " / " + temp.get(i).getLevel2_C_Name());
//		}
	}

	//레벨2가져오기
	private ArrayList<VO_Item_Level_02> getItem_Level2(int subkey){
		ArrayList<VO_Item_Level_02> arr = new ArrayList<VO_Item_Level_02>();
		try {
			DBManager dbm = new DBManager(this);
			arr = dbm.selectData_Level2(subkey);
		} catch (SQLException se) {
			Log.e("ifeelbluu", se.toString());
		}
		return arr;
	}

	/*****************************
	  @카울리
	 *****************************/
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
}
