package co.kr.bigwordenglish;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyAdView;
import com.fsn.cauly.CaulyAdViewListener;

import co.kr.bigwordenglish.common.Check_Preferences;
import co.kr.bigwordenglish.common.CommonUtil;
import co.kr.bigwordenglish.obj.Mianobj;
import co.kr.bigwordenglish.service.ScreenService;

public class MainSettingActivity extends AppCompatActivity implements CaulyAdViewListener {

	private LinearLayout mCategoryLay;
	private TextView mTitleTv;
	public LayoutInflater mLayoutInflater;

	private LinearLayout adWrapper = null;
	private CaulyAdView xmlAdView;

	@Override
	protected void onResume() {
		super.onResume();
		if(CommonUtil.isHome){
			finish();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_set);
		initCauly();
		((Button) findViewById(R.id.btn_setting)).setVisibility(View.GONE);
		((Button) findViewById(R.id.btn_home))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						CommonUtil.isHome = true;
						finish();
					}
				});

		//검색 버튼
		((Button) findViewById(R.id.btn_search)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainSettingActivity.this, MainSearchActivity.class);
				startActivity(i);
			}
		});

		((LinearLayout) findViewById(R.id.btn_notice_list)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(MainSettingActivity.this,MainSettingNoticeList.class);
				startActivity(i);
			}
		});

		((LinearLayout) findViewById(R.id.btn_tip)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://snap40.cafe24.com/BigWordEgs/img/help.png"));
                startActivity(i);
			}
		});

		String q1 = EgsMyPreferences.getAppPreferences(this,"select01","Egs");
		String q2 = EgsMyPreferences.getAppPreferences(this,"select02","Egs");
		String q3 = EgsMyPreferences.getAppPreferences(this,"select03","Egs");

		String index = "";
		String index2 = "";
		String index3 = "";
		if(q1 != null && q1.equals("") == false){
			index = q1.split(",")[0];
			if(index.equals("1")){
				((CheckBox)findViewById(R.id.btn_set_01)).setChecked(true);
			}else if(index.equals("2")){
				((CheckBox)findViewById(R.id.btn_set_02)).setChecked(true);
			}else if(index.equals("3")){
				((CheckBox)findViewById(R.id.btn_set_03)).setChecked(true);
			}else if(index.equals("4")){
				((CheckBox)findViewById(R.id.btn_set_04)).setChecked(true);
			}else if(index.equals("5")){
				((CheckBox)findViewById(R.id.btn_set_05)).setChecked(true);
			}else if(index.equals("6")){
				((CheckBox)findViewById(R.id.btn_set_06)).setChecked(true);
			}
		}

		if(q2 != null && q2.equals("") == false){
			index2 = q2.split(",")[0];
			if(index2.equals("7")){
				((CheckBox)findViewById(R.id.btn_set_07)).setChecked(true);
			}else if(index2.equals("8")){
				((CheckBox)findViewById(R.id.btn_set_08)).setChecked(true);
			}else if(index2.equals("9")){
				((CheckBox)findViewById(R.id.btn_set_09)).setChecked(true);
			}
		}

		if(q3 != null && q3.equals("") == false){
			index3 = q3.split(",")[0];
			if(index3.equals("10")){
				((CheckBox)findViewById(R.id.btn_set_10)).setChecked(true);
			}else if(index3.equals("11")){
				((CheckBox)findViewById(R.id.btn_set_11)).setChecked(true);
			}else if(index3.equals("12")){
				((CheckBox)findViewById(R.id.btn_set_12)).setChecked(true);
			}else if(index3.equals("13")){
				((CheckBox)findViewById(R.id.btn_set_13)).setChecked(true);
			}
		}


		((CheckBox)findViewById(R.id.btn_set_01)).setOnCheckedChangeListener(check01);
		((CheckBox)findViewById(R.id.btn_set_02)).setOnCheckedChangeListener(check01);
		((CheckBox)findViewById(R.id.btn_set_03)).setOnCheckedChangeListener(check01);
		((CheckBox)findViewById(R.id.btn_set_04)).setOnCheckedChangeListener(check01);
		((CheckBox)findViewById(R.id.btn_set_05)).setOnCheckedChangeListener(check01);
		((CheckBox)findViewById(R.id.btn_set_06)).setOnCheckedChangeListener(check01);
		((CheckBox)findViewById(R.id.btn_set_07)).setOnCheckedChangeListener(check02);
		((CheckBox)findViewById(R.id.btn_set_08)).setOnCheckedChangeListener(check02);
		((CheckBox)findViewById(R.id.btn_set_09)).setOnCheckedChangeListener(check02);

		//순차
		((CheckBox)findViewById(R.id.btn_set_10)).setOnCheckedChangeListener(check03);
		((CheckBox)findViewById(R.id.btn_set_11)).setOnCheckedChangeListener(check03);
		((CheckBox)findViewById(R.id.btn_set_12)).setOnCheckedChangeListener(check03);
		((CheckBox)findViewById(R.id.btn_set_13)).setOnCheckedChangeListener(check03);
		String isLockScreen = EgsMyPreferences.getAppPreferences(this,"LockScreen","Egs");

		if(isLockScreen.equals("on")){
			((Switch)findViewById(R.id.switch_ls)).setChecked(true);
		}else{
			((Switch)findViewById(R.id.switch_ls)).setChecked(false);
		}
		((Switch)findViewById(R.id.switch_ls)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				if(b == true){
					EgsMyPreferences.setAppPreferences(MainSettingActivity.this,"LockScreen","on","Egs");
					//서비스 On
					Intent intent = new Intent(MainSettingActivity.this, ScreenService.class);
					startService(intent);
				}else{
					EgsMyPreferences.setAppPreferences(MainSettingActivity.this,"LockScreen","off","Egs");
					//서비스 Off
					Intent intent = new Intent(MainSettingActivity.this, ScreenService.class);
					stopService(intent);
				}
			}
		});
	}

	CompoundButton.OnCheckedChangeListener check01 = new CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
			if(compoundButton.getId() != R.id.btn_set_01){
				((CheckBox)findViewById(R.id.btn_set_01)).setChecked(false);
			}
			if(compoundButton.getId() != R.id.btn_set_02){
				((CheckBox)findViewById(R.id.btn_set_02)).setChecked(false);
			}
			if(compoundButton.getId() != R.id.btn_set_03){
				((CheckBox)findViewById(R.id.btn_set_03)).setChecked(false);
			}
			if(compoundButton.getId() != R.id.btn_set_04){
				((CheckBox)findViewById(R.id.btn_set_04)).setChecked(false);
			}
			if(compoundButton.getId() != R.id.btn_set_05){
				((CheckBox)findViewById(R.id.btn_set_05)).setChecked(false);
			}
			if(compoundButton.getId() != R.id.btn_set_06){
				((CheckBox)findViewById(R.id.btn_set_06)).setChecked(false);
			}
			if(b == true){
				addSelect_01(compoundButton.getId());
			}else{
				addSelect_01(-1);
			}
			compoundButton.setChecked(b);
		}
	};
	public void addSelect_01(int id){
		if(id == -1){
			EgsMyPreferences.setAppPreferences(MainSettingActivity.this,"select01","","Egs");
			return;
		}
		String q = "";
		String index = "0";
		if(id == R.id.btn_set_01){
			q = " col_10 = '20'";
			index = "1";
		}else if(id == R.id.btn_set_02){
			q = " col_10 = '12'";
			index = "2";
		}else if(id == R.id.btn_set_03){
			q = " col_10 = '27'";
			index = "3";
		}else if(id == R.id.btn_set_04){
			q = " col_10 = '6'";
			index = "4";
		}else if(id == R.id.btn_set_05){
			q = " col_10 = '25'";
			index = "5";
		}else if(id == R.id.btn_set_06){
			q = " col_10 = '19'";
			index = "6";
		}
		EgsMyPreferences.setAppPreferences(MainSettingActivity.this,"select01",index+","+q,"Egs");
	}

	CompoundButton.OnCheckedChangeListener check02 = new CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
			if(compoundButton.getId() != R.id.btn_set_07){
				((CheckBox)findViewById(R.id.btn_set_07)).setChecked(false);
			}
			if(compoundButton.getId() != R.id.btn_set_08){
				((CheckBox)findViewById(R.id.btn_set_08)).setChecked(false);
			}
			if(compoundButton.getId() != R.id.btn_set_09){
				((CheckBox)findViewById(R.id.btn_set_09)).setChecked(false);
			}
			if(b == true){
				addSelect_02(compoundButton.getId());
			}else{
				addSelect_02(-1);
			}
			compoundButton.setChecked(b);
		}
	};

	public void addSelect_02(int id){
		if(id == -1){
			EgsMyPreferences.setAppPreferences(MainSettingActivity.this,"select02","","Egs");
			return;
		}
		String q = "";
		String index = "0";
		if(id == R.id.btn_set_07){
			q = " col_6 = '상'";
			index="7";
		}else if(id == R.id.btn_set_08){
			q = " col_6 = '중'";
			index="8";
		}else if(id == R.id.btn_set_09){
			q = " col_6 = '하'";
			index="9";
		}
		EgsMyPreferences.setAppPreferences(MainSettingActivity.this,"select02",index+","+q,"Egs");
	}

	CompoundButton.OnCheckedChangeListener check03 = new CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
			if(compoundButton.getId() != R.id.btn_set_10){
				((CheckBox)findViewById(R.id.btn_set_10)).setChecked(false);
			}
			if(compoundButton.getId() != R.id.btn_set_11){
				((CheckBox)findViewById(R.id.btn_set_11)).setChecked(false);
			}
			if(compoundButton.getId() != R.id.btn_set_12){
				((CheckBox)findViewById(R.id.btn_set_12)).setChecked(false);
			}
			if(compoundButton.getId() != R.id.btn_set_13){
				((CheckBox)findViewById(R.id.btn_set_13)).setChecked(false);
			}

			if(b == true){
				addSelect_03(compoundButton.getId());
			}else{
				addSelect_03(-1);
			}
			compoundButton.setChecked(b);
		}
	};

//	ABC순
//	select * from Word ORDER BY col_2
//	출제횟수 오름차순
//	select * from Word ORDER BY col_4
//	출제횟수 내림차순
//	select * from Word ORDER BY col_4 desc
//	난이도순
//	select * from Word ORDER BY col_6
	public void addSelect_03(int id){
		if(id == -1){
			EgsMyPreferences.setAppPreferences(MainSettingActivity.this,"select03","","Egs");
			return;
		}
		String q = "";
		String index = "0";
		if(id == R.id.btn_set_10){
			q = " ORDER BY col_2";
			index="10";
		}else if(id == R.id.btn_set_11){
			q = " ORDER BY col_4";
			index="11";
		}else if(id == R.id.btn_set_12){
			q = " ORDER BY col_6";
			index="12";
		}else if(id == R.id.btn_set_13){
			q = " ORDER BY col_4 desc";
			index="13";
		}
		EgsMyPreferences.setAppPreferences(MainSettingActivity.this,"select03",index+","+q,"Egs");
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
