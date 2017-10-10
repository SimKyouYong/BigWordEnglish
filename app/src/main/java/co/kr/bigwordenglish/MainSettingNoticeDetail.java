package co.kr.bigwordenglish;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyAdView;
import com.fsn.cauly.CaulyAdViewListener;
import com.gomfactory.adpie.sdk.AdPieError;
import com.gomfactory.adpie.sdk.AdView;

import co.kr.bigwordenglish.common.Check_Preferences;

public class MainSettingNoticeDetail extends AppCompatActivity implements CaulyAdViewListener {


    private LinearLayout mCategoryLay;
    private TextView mTitleTv;
    public LayoutInflater mLayoutInflater;
	private CaulyAdView xmlAdView;
	private AdView adPieView;
	private LinearLayout adWrapper = null;

	@Override
	public void onResume() {
		super.onResume();


//		if(CommonUtil.isLock){
//			CommonUtil.isLock = false;
////			moveTaskToBack(true);
//			Intent intent = new Intent();
//			intent.setAction(Intent.ACTION_MAIN);
//			intent.addCategory(Intent.CATEGORY_HOME);
//			startActivity(intent);
//		}
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notice_detail);

		//광고
		adPieView = (AdView) findViewById(R.id.ad_view);
		xmlAdView = (CaulyAdView) findViewById(R.id.xmladview);
		if (Check_Preferences.getAppPreferences(this , "adview").equals("cauly")){
			initCauly();
		}else{
			initAdpie();
		}

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
//				CommonUtil.isHome = true;
				finish();
			}
		});
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
