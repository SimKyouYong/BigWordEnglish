package co.kr.bigwordenglish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class MainSettingNoticeList extends AppCompatActivity implements CaulyAdViewListener {


    private LinearLayout mCategoryLay;
    private TextView mTitleTv;
    public LayoutInflater mLayoutInflater;

	private ListView list_notice;
	private MyPostAdapter mAdapter;
	public ArrayList<TestItem_01_VO> arry_item = new ArrayList<TestItem_01_VO>();

	@Override
	protected void onResume() {
		super.onResume();
		if(CommonUtil.isHome){
			finish();
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notice);

		//홈버튼
		((Button) findViewById(R.id.btn_home)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				CommonUtil.isHome = true;
				finish();
			}
		});

		initCauly();

		list_notice = (ListView)findViewById(R.id.list_notice);

		getList();

		list_notice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent = new Intent(MainSettingNoticeList.this, MainSettingNoticeDetail.class);
				Log.v("ifeelbluu", arry_item.get((int)l).getmTitle() +","+arry_item.get((int)l).getmPath());
				intent.putExtra("data",arry_item.get((int)l).getmTitle() +","+arry_item.get((int)l).getmPath());
				startActivity(intent);
			}
		});
    }


	public void getList(){
		TimerTask myTask = new TimerTask() {
			public void run() {
				try {
					String url = "http://snap40.cafe24.com/BigWordEgs/admin/view_notice_list_json.php";
					URL obj = new URL(url);
					HttpURLConnection con = (HttpURLConnection) obj.openConnection();
					con.setRequestMethod("POST");
					con.setDefaultUseCaches(false);
					con.setDoInput(true); // 서버에서 읽기 모드 지정
					con.setDoOutput(true); // 서버로 쓰기 모드 지정
					StringBuffer buffer = new StringBuffer();
					OutputStreamWriter outStream = new OutputStreamWriter(con.getOutputStream(), "utf-8");
					PrintWriter writer = new PrintWriter(outStream);
					writer.write(buffer.toString());
					writer.flush();

					int responseCode = con.getResponseCode();
					System.out.println("\nSending 'GET' request to URL : " + url);
					System.out.println("Response Code : " + responseCode);

					BufferedReader in = new BufferedReader(
							new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();

					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();
					con.disconnect();
					String result = response.toString();
					if (result != null) {
//						String result = URLDecoder.decode(
//								EntityUtils.toString(resEntity), "UTF-8");
						Message msg = mHandler.obtainMessage();
						msg.arg1 = 1;
						msg.obj = result;
						mHandler.sendMessage(msg);
					} else {
						Message msg = mHandler.obtainMessage();
						msg.arg1 = -99;
						msg.obj = "resEntity null";
						mHandler.sendMessage(msg);
						return;
					}
				} catch (Exception e) {
					Message msg = mHandler.obtainMessage();
					msg.arg1 = -99;
					msg.obj = e.getMessage().toString();
					mHandler.sendMessage(msg);
				}
			}
		};
		Timer timer = new Timer();
		timer.schedule(myTask, 0);
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
//			page_endprogress();
			int arg1 = msg.arg1;
			String rst = (String) msg.obj;
			if(arg1 == 1){
				if(JSONParser(rst)){
					mAdapter = new MyPostAdapter(MainSettingNoticeList.this, R.layout.item_notice_list, arry_item);
					list_notice.setAdapter(mAdapter);
				}
			}
		}
	};

	public boolean JSONParser(String str){
		try {
			Log.v("ifeelbluu","getJson = " + str);
//			JSONObject jobj = new JSONObject(str);
			JSONArray jary = new JSONArray(str);

			for(int i=0; i<jary.length(); i++){
				JSONObject o = (JSONObject)jary.get(i);
				TestItem_01_VO vo = new TestItem_01_VO();
				vo.setmIndex(o.get("mIndex").toString());
				vo.setmTitle(o.get("mTitle").toString());
				vo.setmBody(o.get("mBody").toString());
				vo.setmDate(o.get("mDate").toString());
				vo.setmPath(o.get("mPath").toString());
				arry_item.add(vo);
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("ifeelbluu",e.getMessage().toString());
			return false;
		}
	}

	public class TestItem_01_VO {
		String mIndex = "";
		String mTitle = "";
		String mBody = "";
		String mDate = "";
		String mPath = "";

		public TestItem_01_VO() {
			super();
		}

		public TestItem_01_VO(String mIndex, String mTitle, String mBody, String mDate, String mPath) {
			super();
			this.mIndex = mIndex;
			this.mTitle = mTitle;
			this.mBody = mBody;
			this.mDate = mDate;
			this.mPath = mPath;
		}

		public String getmIndex() {
			return mIndex;
		}

		public void setmIndex(String mIndex) {
			this.mIndex = mIndex;
		}

		public String getmTitle() {
			return mTitle;
		}

		public void setmTitle(String mTitle) {
			this.mTitle = mTitle;
		}

		public String getmBody() {
			return mBody;
		}

		public void setmBody(String mBody) {
			this.mBody = mBody;
		}

		public String getmDate() {
			return mDate;
		}

		public void setmDate(String mDate) {
			this.mDate = mDate;
		}

		public String getmPath() {
			return mPath;
		}

		public void setmPath(String mPath) {
			this.mPath = mPath;
		}
	}

	/*************
	 * ViewHolder
	 *************/
	class ViewHolder {
		LinearLayout lay;
		TextView txt_data1, txt_data2;
	}

	/****************
	 * Adapter Class
	 ****************/
	class MyPostAdapter extends BaseAdapter {
		Context context;
		LayoutInflater Inflater;
		ArrayList<TestItem_01_VO> items;
		int layout;

		public MyPostAdapter(Context context, int layout,
							 ArrayList<TestItem_01_VO> Array_AddList) {
			this.items = Array_AddList;
			this.context = context;
			Inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.layout = layout;
		}

		public Context getContext() {
			return context;
		}

		public int getLayout() {
			return layout;
		}

		public int getCount() {
			return items.size();
		}

		public TestItem_01_VO getItem(int position) {
			return items.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			TestItem_01_VO p = items.get(position);
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.item_notice_list, null);
				ViewHolder holder = new ViewHolder();
				holder.txt_data1 = (TextView) v.findViewById(R.id.notice_title);
				holder.txt_data2 = (TextView) v.findViewById(R.id.notice_date);
				v.setTag(holder);
			}
			final ViewHolder holder = (ViewHolder) v.getTag();
			holder.txt_data1.setText(p.getmTitle());
			holder.txt_data2.setText(p.getmDate());
			return v;
		}
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
