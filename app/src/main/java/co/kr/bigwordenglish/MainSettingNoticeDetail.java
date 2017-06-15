package co.kr.bigwordenglish;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyAdView;
import com.fsn.cauly.CaulyAdViewListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import co.kr.bigwordenglish.common.CommonUtil;

public class MainSettingNoticeDetail extends AppCompatActivity implements CaulyAdViewListener {


    private LinearLayout mCategoryLay;
    private TextView mTitleTv;
    public LayoutInflater mLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notice_detail);
		initCauly();

		String data = getIntent().getStringExtra("data");
		String[] title = data.split(",");

		Log.v("ifeelbluu", title[0] + " / " + title[1]);
		((TextView)findViewById(R.id.txt_notice_title)).setText(title[0]);

		WebView wv_search = (WebView)findViewById(R.id.wv_notice_detail);
		wv_search.setWebChromeClient(new WebChromeClient());
		wv_search.getSettings().setUseWideViewPort(true);
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

		wv_search.loadUrl("http://snap40.cafe24.com/BigWordEgs/admin/notice/" + data.split(",")[1]);


		//홈버튼
		((Button) findViewById(R.id.btn_home)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				CommonUtil.isHome = true;
				finish();
			}
		});
    }

	/*****************************
	 @카울리
	 *****************************/
	private LinearLayout adWrapper = null;
	private CaulyAdView xmlAdView;
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
