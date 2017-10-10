package co.kr.bigwordenglish;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyAdView;
import com.fsn.cauly.CaulyAdViewListener;
import com.gomfactory.adpie.sdk.AdPieError;
import com.gomfactory.adpie.sdk.AdView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import co.kr.bigwordenglish.common.Check_Preferences;
import co.kr.bigwordenglish.common.CommonUtil;
import co.kr.bigwordenglish.common.DBManager;



public class LockScreenActivity extends Activity implements TextToSpeech.OnInitListener,CaulyAdViewListener {
	@Override
	public void onResume() {
		super.onResume();
		Log.v("ifeelbluu", "onResume =====");
	}
    TimerTask myTask;
	boolean timer = true;
	String countkey = "";
	public static int getRows = 0;
	private CaulyAdView xmlAdView;
	private AdView adPieView;
	private LinearLayout adWrapper = null;

	@Override
	protected void onPause() {
		super.onPause();
		Log.v("ifeelbluu", "onPause ======");
	}

	@Override
	protected void onUserLeaveHint() {
		Log.v("ifeelbluu", "onUserLeaveHint ======");
		finish();
		super.onUserLeaveHint();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lockscreen);

		//광고
		adPieView = (AdView) findViewById(R.id.ad_view);
		xmlAdView = (CaulyAdView) findViewById(R.id.xmladview);
		if (Check_Preferences.getAppPreferences(this , "adview").equals("cauly")){
			initCauly();
		}else{
			initAdpie();
		}

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		Log.v("ifeelbluu", "onCreate =====");

//		ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
//		setGlobalFont(root);

		myTTS = new TextToSpeech(LockScreenActivity.this, LockScreenActivity.this);

		String q1 = EgsMyPreferences.getAppPreferences(this,"select01","Egs");
		String q2 = EgsMyPreferences.getAppPreferences(this,"select02","Egs");


		Log.v("ifeelbluu","q1 === " + q1);

		if(q1 != null && q1.equals("") == false){
			countkey = "where" + q1.split(",")[1];
		}

		if(q2 != null && q2.equals("") == false){
			if(countkey.equals("")){
				countkey = "where" + q2.split(",")[1];
			}else{
				countkey += " and" + q2.split(",")[1];
			}
		}

		DBManager dbm = new DBManager(LockScreenActivity.this);
		String getData = dbm.getWordCount(countkey);
		String[] spStr = getData.split("-=-=");
		((TextView)findViewById(R.id.txt_ls_en)).setText(spStr[0]);
		((TextView)findViewById(R.id.txt_ls_ko)).setText(spStr[1]);
		((TextView)findViewById(R.id.txt_ls_en_exam)).setText(spStr[2]);
		((TextView)findViewById(R.id.text_count)).setText(spStr[4]);
		((TextView)findViewById(R.id.text_level)).setText(spStr[5]);
		((TextView)findViewById(R.id.txt_ls_en_info)).setText("["+spStr[6]+"]");

		Calendar c = GregorianCalendar.getInstance();
		Date d = c.getTime();
		SimpleDateFormat f1 = new SimpleDateFormat("MM-dd");
		SimpleDateFormat f2 = new SimpleDateFormat("HH:mm");
		String time = f1.format(d);
		String time2 = f2.format(d);
		String[] time1 = time.split("-");
		((TextView) findViewById(R.id.txt_ls_time_01)).setText(time1[0]+"월 "+time1[1]+"일");
		((TextView) findViewById(R.id.txt_ls_time_02)).setText(time2);



		((Button) findViewById(R.id.btn_ls_check)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				CommonUtil.isHome = true;
//				CommonUtil.isLock = true;
				finish();
			}
		});

		((TextView) findViewById(R.id.text_speak)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				speakEnglish(((TextView)findViewById(R.id.txt_ls_en)).getText().toString().trim());
			}
		});

		myTask = new TimerTask() {
			public void run() {
                while (timer) {
                    try {
                        Thread.sleep(2000);
                        Message msg = new Message();
                        msg.what = 200;
                        mResponseHandler.sendMessage(msg);
                    } catch (InterruptedException e) {
                    }
                }
            }
		};

        Timer timer = new Timer();
        timer.schedule(myTask, 0);


		((ImageView)findViewById(R.id.btn_prev)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(getRows > 0){
					getRows -=1;
				}else{
					getRows = -1;
				}
				DBManager dbm = new DBManager(LockScreenActivity.this);
				String getData = dbm.getWordNext(countkey, getRows);
				String[] spStr = getData.split("-=-=");
				((TextView)findViewById(R.id.txt_ls_en)).setText(spStr[0]);
				((TextView)findViewById(R.id.txt_ls_ko)).setText(spStr[1]);
				((TextView)findViewById(R.id.txt_ls_en_exam)).setText(spStr[2]);
				((TextView)findViewById(R.id.text_count)).setText(spStr[4]);
				((TextView)findViewById(R.id.text_level)).setText(spStr[5]);
				((TextView)findViewById(R.id.txt_ls_en_info)).setText("["+spStr[6]+"]");

			}
		});
		((ImageView)findViewById(R.id.btn_next)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					getRows +=1;
					DBManager dbm = new DBManager(LockScreenActivity.this);
					String getData = dbm.getWordNext(countkey, getRows);
					String[] spStr = getData.split("-=-=");
					((TextView)findViewById(R.id.txt_ls_en)).setText(spStr[0]);
					((TextView)findViewById(R.id.txt_ls_ko)).setText(spStr[1]);
					((TextView)findViewById(R.id.txt_ls_en_exam)).setText(spStr[2]);
					((TextView)findViewById(R.id.text_count)).setText(spStr[4]);
					((TextView)findViewById(R.id.text_level)).setText(spStr[5]);
					((TextView)findViewById(R.id.txt_ls_en_info)).setText("["+spStr[6]+"]");
				}catch (Exception e){
//					getRows -=1;
					getRows = 0;
					DBManager dbm = new DBManager(LockScreenActivity.this);
					String getData = dbm.getWordNext(countkey, getRows);
					String[] spStr = getData.split("-=-=");
					((TextView)findViewById(R.id.txt_ls_en)).setText(spStr[0]);
					((TextView)findViewById(R.id.txt_ls_ko)).setText(spStr[1]);
					((TextView)findViewById(R.id.txt_ls_en_exam)).setText(spStr[2]);
					((TextView)findViewById(R.id.text_count)).setText(spStr[4]);
					((TextView)findViewById(R.id.text_level)).setText(spStr[5]);
					((TextView)findViewById(R.id.txt_ls_en_info)).setText("["+spStr[6]+"]");
				}

			}
		});
	}
	Handler mResponseHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if (msg.what == 200) {
                Calendar c = GregorianCalendar.getInstance();
                Date d = c.getTime();
                SimpleDateFormat f1 = new SimpleDateFormat("MM-dd");
                SimpleDateFormat f2 = new SimpleDateFormat("HH:mm");
                String time = f1.format(d);
                String time2 = f2.format(d);
                String[] time1 = time.split("-");
                ((TextView) findViewById(R.id.txt_ls_time_01)).setText(time1[0]+"월 "+time1[1]+"일");
                ((TextView) findViewById(R.id.txt_ls_time_02)).setText(time2);
			}else if(msg.what == -99){ //에러
			}
			return false;
		}
	});

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		myTTS = new TextToSpeech(LockScreenActivity.this, LockScreenActivity.this);

//		ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
//		setGlobalFont(root);
		String q1 = EgsMyPreferences.getAppPreferences(this,"select01","Egs");
		String q2 = EgsMyPreferences.getAppPreferences(this,"select02","Egs");


		Log.v("ifeelbluu","q1 === " + q1);
		String countkey = "";
		if(q1 != null && q1.equals("") == false){
			countkey = "where" + q1.split(",")[1];
		}

		if(q2 != null && q2.equals("") == false){
			if(countkey.equals("")){
				countkey = "where" + q2.split(",")[1];
			}else{
				countkey += " and" + q2.split(",")[1];
			}
		}

		DBManager dbm = new DBManager(LockScreenActivity.this);
		String getData = dbm.getWordCount(countkey);
		String[] spStr = getData.split("-=-=");
		((TextView)findViewById(R.id.txt_ls_en)).setText(spStr[0]);
		((TextView)findViewById(R.id.txt_ls_ko)).setText(spStr[1]);
		((TextView)findViewById(R.id.txt_ls_en_exam)).setText(spStr[2]);
		((TextView)findViewById(R.id.text_count)).setText(spStr[4]);
		((TextView)findViewById(R.id.text_level)).setText(spStr[5]);
		((TextView)findViewById(R.id.txt_ls_en_info)).setText("["+spStr[6]+"]");



		Calendar c = GregorianCalendar.getInstance();
		Date d = c.getTime();
		SimpleDateFormat f1 = new SimpleDateFormat("MM-dd");
		SimpleDateFormat f2 = new SimpleDateFormat("HH:mm");
		String time = f1.format(d);
		String time2 = f2.format(d);
		String[] time1 = time.split("-");
		((TextView) findViewById(R.id.txt_ls_time_01)).setText(time1[0]+"월 "+time1[1]+"일");
		((TextView) findViewById(R.id.txt_ls_time_02)).setText(time2);
		((Button) findViewById(R.id.btn_ls_check)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		((TextView) findViewById(R.id.text_speak)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				speakEnglish(((TextView)findViewById(R.id.txt_ls_en)).getText().toString().trim());
			}
		});

        myTask = new TimerTask() {
            public void run() {
                while (timer) {
                    try {
                        Thread.sleep(2000);
                        Message msg = new Message();
                        msg.what = 200;
                        mResponseHandler.sendMessage(msg);
                    } catch (InterruptedException e) {
                    }
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(myTask, 0);
	}

	TextToSpeech myTTS;
	public String tts_str = "";
	public void speakEnglish(final String str) {
		tts_str = str;
		myTTS.setLanguage(Locale.US);                                    //언어 설정.
		myTTS.speak(tts_str, TextToSpeech.QUEUE_FLUSH, null);    //해당 언어로 텍스트 음성 출력
	}

	@Override
	public void onInit(int i) {
//        Toast.makeText(MainSubListActivity.this,"TTS준비",Toast.LENGTH_LONG).show();
	}

	public void setGlobalFont(ViewGroup root){
		CommonUtil.setLSFont(this);
		for(int i=0; i< root.getChildCount(); i++){
			View child = root.getChildAt(i);
			if(child instanceof TextView)((TextView)child).setTypeface(CommonUtil.lsfont);
			else if(child instanceof ViewGroup) setGlobalFont((ViewGroup)child);
		}
	}
	private void initAdpie() {
		xmlAdView.setVisibility(View.GONE);
		adPieView.setVisibility(View.VISIBLE);
        adPieView.setScaleUp(true);
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
	/*****************************
	 @카울리
	 *****************************/
	private void initCauly(){
		xmlAdView.setVisibility(View.VISIBLE);
		adPieView.setVisibility(View.GONE);
		// CloseAd 초기화
		CaulyAdInfo closeAdInfo = new CaulyAdInfoBuilder("modukcJI").build();
		// 선택사항: XML의 AdView 항목을 찾아 Listener 설정
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
		//adWrapper.setVisibility(View.GONE);
		initAdpie();
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
