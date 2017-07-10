package co.kr.bigwordenglish;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyAdView;
import com.fsn.cauly.CaulyAdViewListener;
import com.gomfactory.adpie.sdk.AdPieError;
import com.gomfactory.adpie.sdk.AdPieSDK;
import com.gomfactory.adpie.sdk.AdView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.kr.bigwordenglish.common.Check_Preferences;
import co.kr.bigwordenglish.common.CommonUtil;
import co.kr.bigwordenglish.common.DBManager;
import co.kr.bigwordenglish.common.VO_Item_Level_02;
import co.kr.bigwordenglish.service.ScreenService;
import co.kr.sky.AccumThread;

public class MainActivity extends AppCompatActivity implements CaulyAdViewListener{

	private LinearLayout mCategoryLay;
	private TextView mTitleTv;
	public LayoutInflater mLayoutInflater;


	private LinearLayout adWrapper = null;
	private CaulyAdView xmlAdView;
	private AdView adPieView;

	CommonUtil dataSet = CommonUtil.getInstance();

	AccumThread mThread;
	Map<String, String> map = new HashMap<String, String>();

	@Override
	protected void onResume() {
		super.onResume();



		CommonUtil.getLevel_03_Q = "";
		if(CommonUtil.isHome){
			CommonUtil.isHome = false;
		}

		if(CommonUtil.isLock){
			CommonUtil.isLock = false;
//			moveTaskToBack(true);
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
		}
	}

	int isMainRoot = 0;
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(isMainRoot == 1){
			CommonUtil.isMainActivity = false;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		AdPieSDK.getInstance().initialize(getApplicationContext(), getString(R.string.mid));
		adWrapper = (LinearLayout) findViewById(R.id.adWrapper);


		String isSetApp = EgsMyPreferences.getAppPreferences(MainActivity.this,"setApp","Egs");
		if(isSetApp == null || isSetApp.equals("")){
			EgsMyPreferences.setAppPreferences(MainActivity.this,"setApp","true","Egs");
		}

		if(CommonUtil.isMainActivity == false){
			CommonUtil.isMainActivity = true;
			isMainRoot = 1;
		}else{
			finish();
			return;
		}

		Intent i = new Intent(this,IntroActivity.class);
		startActivity(i);


		onClickEvent();

		CommonUtil.setFont(this);
		((TextView) findViewById(R.id.btn_allview)).setTypeface(CommonUtil.font);
		((TextView) findViewById(R.id.btn_view_set)).setTypeface(CommonUtil.font);

		//광고
		adPieView = (AdView) findViewById(R.id.ad_view);
		xmlAdView = (CaulyAdView) findViewById(R.id.xmladview);



		String isLockScreen = EgsMyPreferences.getAppPreferences(this,"LockScreen","Egs");
		if(isLockScreen == null || isLockScreen.equals("")){
			EgsMyPreferences.setAppPreferences(this,"LockScreen","off","Egs");
			isLockScreen = "off";
		}

		if(isLockScreen.equals("on")){
			//서비스 On
			Intent intent = new Intent(this, ScreenService.class);
			startService(intent);
		}else{
			//서비스 Off
			Intent intent = new Intent(this, ScreenService.class);
			stopService(intent);
		}
		map.clear();
		map.put("url", dataSet.SERVER + "Adview.php");
		// 스레드 생성
		mThread = new AccumThread(MainActivity.this, mAfterAccum, map, 0, 0, null);
		mThread.start(); // 스레드 시작!!

	}
	private Handler mHandler = new Handler();
	private Runnable mMyTask = new Runnable() { @Override public void run() {
		if (Check_Preferences.getAppPreferences(MainActivity.this , "adview").equals("cauly")){
			initCauly();
		}else{
			initAdpie();
		}
	} };


		Handler mAfterAccum = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.arg1 == 0) {
				String res = (String) msg.obj;
				Log.e("CHECK", "RESULT  -> " + res);

				mHandler.postDelayed(mMyTask, 3000); // 3초후에 실행
				Check_Preferences.setAppPreferences(MainActivity.this ,"adview" ,res.trim().replace(" " ,""));

			}
		}
	};
	private void initAdpie() {
		xmlAdView.setVisibility(View.GONE);
		// Insert your AdPie-Slot-ID
		adPieView.setSlotId(getString(R.string.banner_sid));
		adPieView.setAdListener(new AdView.AdListener() {

			@Override
			public void onAdLoaded() {
				Log.e("SKY", "AdView onAdLoaded");
			}

			@Override
			public void onAdFailedToLoad(int errorCode) {
				Log.e("SKY", "AdView onAdFailedToLoad "	+ AdPieError.getMessage(errorCode));
				initCauly();
			}

			@Override
			public void onAdClicked() {
				Log.e("SKY", "AdView onAdClicked");

			}
		});
		adPieView.load();
	}
	private void onClickEvent() {
		//검색 버튼
		((Button) findViewById(R.id.btn_search)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MainSearchActivity.class);
				startActivity(i);
			}
		});
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
		((Button) findViewById(R.id.btn_allview)).setOnClickListener(btnListener);
		((Button) findViewById(R.id.btn_view_set)).setOnClickListener(btnListener);
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
			Intent i = new Intent(MainActivity.this, MainAllListActivity.class);
			switch (v.getId()) {
			case R.id.setting_btn:
				Log.e("SKY", "-- setting_btn --");
				break;
			case R.id.btn_allview:
				i.putExtra("type","1");
				startActivity(i);
				break;
			case R.id.btn_view_set:
				i.putExtra("type","2");
				startActivity(i);
				break;
			}
		}
	};

	private void onClickLevel_02(int subkey){
		Intent i = new Intent(this, MainSubActivity.class);
		i.putExtra("SubKey", subkey+"");
		startActivity(i);
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
		adPieView.setVisibility(View.GONE);
		// CloseAd 초기화
		CaulyAdInfo closeAdInfo = new CaulyAdInfoBuilder("modukcJI").build();
		// 선택사항: XML의 AdView 항목을 찾아 Listener 설정
		xmlAdView.setAdViewListener(this);

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
		//adWrapper.setVisibility(View.GONE);
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
