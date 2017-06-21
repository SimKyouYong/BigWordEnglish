package co.kr.bigwordenglish;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import co.kr.bigwordenglish.common.CommonUtil;
import co.kr.bigwordenglish.common.DBManager;

public class MainSearchActivity extends Activity {

	@Override
	public void onResume() {
		super.onResume();


		if(CommonUtil.isLock){
			CommonUtil.isLock = false;
//			moveTaskToBack(true);
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
		}
	}

	WebView wv_search;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_search);

		wv_search = (WebView)findViewById(R.id.wv_search);
		wv_search.setWebChromeClient(new WebChromeClient());
		wv_search.getSettings().setUseWideViewPort(false);
		wv_search.getSettings().setLoadWithOverviewMode(true);
		wv_search.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		wv_search.setScrollbarFadingEnabled(true);

		wv_search.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode,
										String description, String failingUrl) {
			}

			@Override
			public void onPageStarted(WebView view, String url,Bitmap favicon) {
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				Log.i("ifeelbluu", "onPageFinished");
			}
		});
		((Button)findViewById(R.id.btn_web_serach)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String searchkey = ((EditText)findViewById(R.id.edt_web_search)).getText().toString();
				wv_search.loadUrl("http://endic.naver.com/search.nhn?sLn=kr&isOnlyViewEE=N&query=" + searchkey);
			}
		});

		//홈버튼
		((Button) findViewById(R.id.btn_home)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				CommonUtil.isHome = true;
				finish();
			}
		});
	}
}
