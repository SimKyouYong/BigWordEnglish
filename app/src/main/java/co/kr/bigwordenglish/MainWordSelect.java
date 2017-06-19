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

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyAdView;
import com.fsn.cauly.CaulyAdViewListener;

import co.kr.bigwordenglish.common.CommonUtil;
import co.kr.bigwordenglish.obj.Mianobj;
public class MainWordSelect extends AppCompatActivity implements CaulyAdViewListener {


    private LinearLayout mCategoryLay;
    private TextView mTitleTv;
    public LayoutInflater mLayoutInflater;

	private String Word_Level = "";
	private String Word_Count = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_work_src);

        onClickEvent();
		initCauly();

        CheckWordEvent();

		if(CommonUtil.slevel.equals("") == false){
			int level = Integer.parseInt(CommonUtil.slevel);
				if(level == 1) {
					Select_01 = 1;
					((LinearLayout) findViewById(R.id.check_work01)).setBackgroundResource(R.mipmap.bg_search_set_on);
				}else if(level == 2) {
					Select_01 = 2;
					((LinearLayout) findViewById(R.id.check_work02)).setBackgroundResource(R.mipmap.bg_search_set_on);
				}else if(level == 3) {
					Select_01 = 3;
					((LinearLayout) findViewById(R.id.check_work03)).setBackgroundResource(R.mipmap.bg_search_set_on);
				}
		}

		if(CommonUtil.scount.equals("") == false){
			int level = Integer.parseInt(CommonUtil.scount);
			Select_02 = level;

			if(level == 1) {
				((LinearLayout) findViewById(R.id.check_work04)).setBackgroundResource(R.mipmap.bg_search_set_on);
			}else if(level == 2) {
				((LinearLayout) findViewById(R.id.check_work05)).setBackgroundResource(R.mipmap.bg_search_set_on);
			}else if(level == 3) {
				((LinearLayout) findViewById(R.id.check_work06)).setBackgroundResource(R.mipmap.bg_search_set_on);
			}else if(level == 4) {
				((LinearLayout) findViewById(R.id.check_work07)).setBackgroundResource(R.mipmap.bg_search_set_on);
			}else if(level == 5) {
				((LinearLayout) findViewById(R.id.check_work08)).setBackgroundResource(R.mipmap.bg_search_set_on);
			}else if(level == 7) {
				((LinearLayout) findViewById(R.id.check_work09)).setBackgroundResource(R.mipmap.bg_search_set_on);
			}else if(level == 10) {
				((LinearLayout) findViewById(R.id.check_work10)).setBackgroundResource(R.mipmap.bg_search_set_on);
			}
		}
    }
    
    private void onClickEvent(){
    	((Button) findViewById(R.id.btn_setting)).setVisibility(View.GONE);
		((Button) findViewById(R.id.btn_home)).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						CommonUtil.isHome = true;
						finish();
					}
				});

		((Button) findViewById(R.id.btn_check)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
//				Select_01 //1:상 2:중 3:하
//				Select_02 //1:1회출제 2:2회출제 3:3회출제 4:4회출제 5:5회출제 7:7회출제 10:10회출제
				Intent it = getIntent();
				it.putExtra("Select_01", Select_01+"");
				it.putExtra("Select_02", Select_02+"");
				setResult(Activity.RESULT_OK, it);
				finish();
			}
		});
		((Button) findViewById(R.id.btn_cancel)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
    }

    int Select_01 = 0;
    int Select_02 = 0;
    private void CheckWordEvent(){
    	
    	//난이도~~
    	((LinearLayout) findViewById(R.id.check_work01))
		.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Select_01 = 1;
				if(setCheck01() == true) {
					((LinearLayout) findViewById(R.id.check_work01)).setBackgroundResource(R.mipmap.bg_search_set_on);
					((LinearLayout) findViewById(R.id.check_work02)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work03)).setBackgroundResource(R.mipmap.bg_search_set_off);
				}
			}
		});
    	((LinearLayout) findViewById(R.id.check_work02))
    	.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Select_01 = 2;
				if(setCheck01() == true) {
					((LinearLayout) findViewById(R.id.check_work01)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work02)).setBackgroundResource(R.mipmap.bg_search_set_on);
					((LinearLayout) findViewById(R.id.check_work03)).setBackgroundResource(R.mipmap.bg_search_set_off);
				}
    		}
    	});
    	((LinearLayout) findViewById(R.id.check_work03))
    	.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Select_01 = 3;
				if(setCheck01() == true) {
					((LinearLayout) findViewById(R.id.check_work01)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work02)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work03)).setBackgroundResource(R.mipmap.bg_search_set_on);
				}
    		}
    	});
    	
    	//출제횟수~~
    	((LinearLayout) findViewById(R.id.check_work04))
    	.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Select_02 = 1;
				if(setCheck02() == true) {
					((LinearLayout) findViewById(R.id.check_work04)).setBackgroundResource(R.mipmap.bg_search_set_on);
					((LinearLayout) findViewById(R.id.check_work05)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work06)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work07)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work08)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work09)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work10)).setBackgroundResource(R.mipmap.bg_search_set_off);
				}
    		}
    	});
    	((LinearLayout) findViewById(R.id.check_work05))
    	.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Select_02 = 2;
				if(setCheck02() == true) {
					((LinearLayout) findViewById(R.id.check_work04)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work05)).setBackgroundResource(R.mipmap.bg_search_set_on);
					((LinearLayout) findViewById(R.id.check_work06)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work07)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work08)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work09)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work10)).setBackgroundResource(R.mipmap.bg_search_set_off);
				}
    		}
    	});
    	((LinearLayout) findViewById(R.id.check_work06))
    	.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Select_02 = 3;
				if(setCheck02() == true) {
					((LinearLayout) findViewById(R.id.check_work04)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work05)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work06)).setBackgroundResource(R.mipmap.bg_search_set_on);
					((LinearLayout) findViewById(R.id.check_work07)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work08)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work09)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work10)).setBackgroundResource(R.mipmap.bg_search_set_off);
				}
    		}
    	});
    	((LinearLayout) findViewById(R.id.check_work07))
    	.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Select_02 = 4;
				if(setCheck02() == true) {
					((LinearLayout) findViewById(R.id.check_work04)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work05)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work06)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work07)).setBackgroundResource(R.mipmap.bg_search_set_on);
					((LinearLayout) findViewById(R.id.check_work08)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work09)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work10)).setBackgroundResource(R.mipmap.bg_search_set_off);
				}
    		}
    	});
    	((LinearLayout) findViewById(R.id.check_work08))
    	.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Select_02 = 5;
				if(setCheck02() == true) {
					((LinearLayout) findViewById(R.id.check_work04)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work05)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work06)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work07)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work08)).setBackgroundResource(R.mipmap.bg_search_set_on);
					((LinearLayout) findViewById(R.id.check_work09)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work10)).setBackgroundResource(R.mipmap.bg_search_set_off);
				}
    		}
    	});
    	((LinearLayout) findViewById(R.id.check_work09))
    	.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Select_02 = 7;
				if(setCheck02() == true) {
					((LinearLayout) findViewById(R.id.check_work04)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work05)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work06)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work07)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work08)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work09)).setBackgroundResource(R.mipmap.bg_search_set_on);
					((LinearLayout) findViewById(R.id.check_work10)).setBackgroundResource(R.mipmap.bg_search_set_off);
				}
    		}
    	});
    	((LinearLayout) findViewById(R.id.check_work10))
    	.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Select_02 = 10;
				if(setCheck02() == true) {
					((LinearLayout) findViewById(R.id.check_work04)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work05)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work06)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work07)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work08)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work09)).setBackgroundResource(R.mipmap.bg_search_set_off);
					((LinearLayout) findViewById(R.id.check_work10)).setBackgroundResource(R.mipmap.bg_search_set_on);
				}
    		}
    	});




    }

    public boolean setCheck01(){
		if(Select_01 == prevSelect_01){
			Select_01 = 0;
			((LinearLayout) findViewById(R.id.check_work01)).setBackgroundResource(R.mipmap.bg_search_set_off);
			((LinearLayout) findViewById(R.id.check_work02)).setBackgroundResource(R.mipmap.bg_search_set_off);
			((LinearLayout) findViewById(R.id.check_work03)).setBackgroundResource(R.mipmap.bg_search_set_off);
			return false;
		}else{
			prevSelect_01 = Select_01;
			return true;
		}

	}

	public boolean setCheck02(){
		if(Select_02 == prevSelect_02){
			Select_02 = 0;
			((LinearLayout) findViewById(R.id.check_work04)).setBackgroundResource(R.mipmap.bg_search_set_off);
			((LinearLayout) findViewById(R.id.check_work05)).setBackgroundResource(R.mipmap.bg_search_set_off);
			((LinearLayout) findViewById(R.id.check_work06)).setBackgroundResource(R.mipmap.bg_search_set_off);
			((LinearLayout) findViewById(R.id.check_work07)).setBackgroundResource(R.mipmap.bg_search_set_off);
			((LinearLayout) findViewById(R.id.check_work08)).setBackgroundResource(R.mipmap.bg_search_set_off);
			((LinearLayout) findViewById(R.id.check_work09)).setBackgroundResource(R.mipmap.bg_search_set_off);
			((LinearLayout) findViewById(R.id.check_work10)).setBackgroundResource(R.mipmap.bg_search_set_off);
			return false;
		}else{
			prevSelect_02 = Select_02;
			return true;
		}
	}

    int prevSelect_01 = 0;
    int prevSelect_02 = 0;


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
