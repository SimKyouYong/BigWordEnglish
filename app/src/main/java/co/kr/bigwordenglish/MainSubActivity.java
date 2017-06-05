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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyAdView;
import com.fsn.cauly.CaulyAdViewListener;

import java.util.ArrayList;

import co.kr.bigwordenglish.common.CommonUtil;
import co.kr.bigwordenglish.common.DBManager;
import co.kr.bigwordenglish.common.VO_Item_Level_02;
import co.kr.bigwordenglish.obj.Mianobj;

public class MainSubActivity extends AppCompatActivity implements CaulyAdViewListener {
    //리스트뷰
    private ListView subListView;
    private MyPostAdapter mAdapter;
    private ArrayList<VO_Item_Level_02> listitem = new ArrayList<VO_Item_Level_02>();

    //서브키
    private String getSubKey;


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
        setContentView(R.layout.activity_mainsub);

        getSubKey = getIntent().getStringExtra("SubKey");
        subListView = (ListView) findViewById(R.id.listview_mainsub);

        subListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("ifeelbluu","start i ==== " + l);
                Intent intent = new Intent(MainSubActivity.this, MainSubListActivity.class);
                intent.putExtra("Subkey_list",listitem.get((int)l).getLevel2_Index());
                startActivity(intent);
            }
        });

        initCauly();
        onClickEvent();

        getListLevel_02(Integer.parseInt(getSubKey));
        setMenuButtonPress(Integer.parseInt(getSubKey));
    }

    private int setMenuKey = -1;
    private void setMenuButtonPress(int set){
        ((Button) findViewById(R.id.btn_menu1)).setBackgroundResource(R.mipmap.menu_01);
        ((Button) findViewById(R.id.btn_menu2)).setBackgroundResource(R.mipmap.menu_02);
        ((Button) findViewById(R.id.btn_menu3)).setBackgroundResource(R.mipmap.menu_03);
        ((Button) findViewById(R.id.btn_menu4)).setBackgroundResource(R.mipmap.menu_04);
        ((Button) findViewById(R.id.btn_menu5)).setBackgroundResource(R.mipmap.menu_05);
        ((Button) findViewById(R.id.btn_menu6)).setBackgroundResource(R.mipmap.menu_06);

        switch (set) {
            case 1:
                ((Button) findViewById(R.id.btn_menu1)).setBackgroundResource(R.mipmap.menu_01_press);
                break;
            case 2:
                ((Button) findViewById(R.id.btn_menu2)).setBackgroundResource(R.mipmap.menu_02_press);
                break;
            case 3:
                ((Button) findViewById(R.id.btn_menu3)).setBackgroundResource(R.mipmap.menu_03_press);
                break;
            case 4:
                ((Button) findViewById(R.id.btn_menu4)).setBackgroundResource(R.mipmap.menu_04_press);
                break;
            case 5:
                ((Button) findViewById(R.id.btn_menu5)).setBackgroundResource(R.mipmap.menu_05_press);
                break;
            case 6:
                ((Button) findViewById(R.id.btn_menu6)).setBackgroundResource(R.mipmap.menu_06_press);
                break;
        }
    }

    private void onClickEvent() {
        //홈버튼
        ((Button) findViewById(R.id.btn_home)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtil.isHome = true;
                finish();
            }
        });

        //설정 버튼
        ((Button) findViewById(R.id.btn_setting)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainSubActivity.this, MainSettingActivity.class);
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
            switch (v.getId()) {
                case R.id.btn_menu1:
                    setMenuKey = 1;
                    break;
                case R.id.btn_menu2:
                    setMenuKey = 2;
                    break;
                case R.id.btn_menu3:
                    setMenuKey = 3;
                    break;
                case R.id.btn_menu4:
                    setMenuKey = 4;
                    break;
                case R.id.btn_menu5:
                    setMenuKey = 5;
                    break;
                case R.id.btn_menu6:
                    setMenuKey = 6;
                    break;
            }
            setMenuButtonPress(setMenuKey);
            getListLevel_02(setMenuKey);
        }
    };


    private void getListLevel_02(int subkey){
        listitem.clear();
        listitem = getItem_Level2(subkey);
//        for(int i=0; i<temp.size(); i++){
//            Log.v("ifeelbluu",temp.get(i).getLevel2_Index() + " / " + temp.get(i).getLevel2_C_Name());
//        }
        mAdapter = new MyPostAdapter(this, R.layout.item_mainsub, listitem);
        subListView.setAdapter(mAdapter);
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

    /*************
     * ViewHolder
     *************/
    class ViewHolder {
        LinearLayout lay;
        TextView txt_data1;
    }

    /****************
     * Adapter Class
     ****************/
    class MyPostAdapter extends BaseAdapter {
        Context context;
        LayoutInflater Inflater;
        ArrayList<VO_Item_Level_02> items;
        int layout;

        public MyPostAdapter(Context context, int layout,
                             ArrayList<VO_Item_Level_02> Array_AddList) {
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

        public VO_Item_Level_02 getItem(int position) {
            return items.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            VO_Item_Level_02 p = items.get(position);
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.item_mainsub, null);
                ViewHolder holder = new ViewHolder();
                holder.txt_data1 = (TextView) v.findViewById(R.id.txtitem_01_1);
                v.setTag(holder);
            }
            final ViewHolder holder = (ViewHolder) v.getTag();
            holder.txt_data1.setText(p.getLevel2_C_Name());
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
