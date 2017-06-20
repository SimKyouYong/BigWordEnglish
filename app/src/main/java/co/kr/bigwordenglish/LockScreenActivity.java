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
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import co.kr.bigwordenglish.common.CommonUtil;
import co.kr.bigwordenglish.common.DBManager;

public class LockScreenActivity extends Activity implements TextToSpeech.OnInitListener {
	@Override
	public void onResume() {
		super.onResume();
	}
    TimerTask myTask;
	boolean timer = true;
	String countkey = "";
	public static int getRows = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lockscreen);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);


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
				CommonUtil.isHome = true;
				CommonUtil.isLock = true;
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

}
