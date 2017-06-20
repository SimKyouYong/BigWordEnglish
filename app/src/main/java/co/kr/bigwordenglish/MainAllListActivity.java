package co.kr.bigwordenglish;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import java.util.Locale;

import co.kr.bigwordenglish.common.CommonUtil;
import co.kr.bigwordenglish.common.DBManager;
import co.kr.bigwordenglish.common.VO_Item_Level_02_List;

public class MainAllListActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, CaulyAdViewListener {
    private ListView subListView;
    private MyPostAdapter mAdapter;
    private int MORE_CODE = -1;//더 보기

    private boolean is_En_Visible = true;
    private boolean is_Ko_Visible = true;
    private boolean is_Exam_Visible = false;
    private boolean isSetting = false;
    private ArrayList<VO_Item_Level_02_List> listitem = new ArrayList<VO_Item_Level_02_List>();

    @Override
    protected void onResume() {
        super.onResume();
        if(CommonUtil.isHome){
            finish();
        }

        if(isSetting){
            lastTotalcount = 0;
            isSetting = false;
            getList_Word_Page("0",Param_Level, Param_Count);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_item);
        String type = getIntent().getStringExtra("type");

        myTTS = new TextToSpeech(MainAllListActivity.this, MainAllListActivity.this);

        subListView = (ListView) findViewById(R.id.listview_eg_view);



        if(type.equals("1")){
            getList_Word();
        }else{
            Type2Event();
        }

        onClickEvent();
        initCauly();
        onClickEvent_BottomMenu();

        onScrollAddView();
    }

    int lastTotalcount = 0;
    private void onScrollAddView(){
        subListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if((firstVisibleItem+visibleItemCount)==totalItemCount && totalItemCount > 10){
                    Log.d("ifeelbluu", "리스트를 추가합니다"+totalItemCount+ "//");
                    if(lastTotalcount == totalItemCount){
                        return;
                    }
                    getList_Word_Page(listitem.size()+"",Param_Level,Param_Count);
                    mAdapter.notifyDataSetChanged();
                    lastTotalcount = totalItemCount;
                }

            }
        });
    }

    private void onClickEvent_BottomMenu() {
        //설정
        ((TextView) findViewById(R.id.btn_bottom_set)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainAllListActivity.this, MainWordSelect.class);
                startActivityForResult(i, 1);
            }
        });

        //단어가리기 버튼
        ((TextView) findViewById(R.id.btn_bottom_en_visible)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_En_Visible) {
                    is_En_Visible = false;
                    ((TextView) findViewById(R.id.btn_bottom_en_visible)).setBackgroundResource(R.drawable.set_btn_round_press);
                    ((TextView) findViewById(R.id.btn_bottom_en_visible)).setText("단어보기");
                }else {
                    is_En_Visible = true;
                    ((TextView) findViewById(R.id.btn_bottom_en_visible)).setBackgroundResource(R.drawable.set_btn_round);
                    ((TextView) findViewById(R.id.btn_bottom_en_visible)).setText("단어가리기");
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        //뜻가리기 버튼
        ((TextView) findViewById(R.id.btn_bottom_ko_visible)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_Ko_Visible) {
                    is_Ko_Visible = false;
                    ((TextView) findViewById(R.id.btn_bottom_ko_visible)).setBackgroundResource(R.drawable.set_btn_round_press);
                    ((TextView) findViewById(R.id.btn_bottom_ko_visible)).setText("뜻보기");
                }else {
                    is_Ko_Visible = true;
                    ((TextView) findViewById(R.id.btn_bottom_ko_visible)).setBackgroundResource(R.drawable.set_btn_round);
                    ((TextView) findViewById(R.id.btn_bottom_ko_visible)).setText("뜻가리기");
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        //예문보기 버튼
        ((TextView) findViewById(R.id.btn_bottom_exam_visible)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_Exam_Visible) {
                    is_Exam_Visible = false;
                    ((TextView) findViewById(R.id.btn_bottom_exam_visible)).setBackgroundResource(R.drawable.set_btn_round);
                    ((TextView) findViewById(R.id.btn_bottom_exam_visible)).setText("예문보기");
                }else {
                    is_Exam_Visible = true;
                    ((TextView) findViewById(R.id.btn_bottom_exam_visible)).setBackgroundResource(R.drawable.set_btn_round_press);
                    ((TextView) findViewById(R.id.btn_bottom_exam_visible)).setText("예문가리기");
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        //단어장 버튼
        ((TextView) findViewById(R.id.btn_bottom_word_paper)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFavoriteMod == false){
                    ((TextView) findViewById(R.id.btn_bottom_word_paper)).setBackgroundResource(R.drawable.set_btn_round_press);
                    ((TextView) findViewById(R.id.btn_bottom_word_paper)).setText("전체보기");
                    isFavoriteMod = true;
                    listitem.clear();
                    DBManager dbm = new DBManager(MainAllListActivity.this);
                    listitem = dbm.Favorite_Word_List_Set("0");
                    mAdapter = new MyPostAdapter(MainAllListActivity.this, R.layout.item_eg_list, listitem);
                    subListView.setAdapter(mAdapter);
                }else{
                    ((TextView) findViewById(R.id.btn_bottom_word_paper)).setBackgroundResource(R.drawable.set_btn_round);
                    ((TextView) findViewById(R.id.btn_bottom_word_paper)).setText("단어장");
                    isFavoriteMod = false;
                    listitem.clear();
                    DBManager dbm = new DBManager(MainAllListActivity.this);
                    listitem = dbm.All_Word_List_Set("0","","");
                    mAdapter = new MyPostAdapter(MainAllListActivity.this, R.layout.item_eg_list, listitem);
                    subListView.setAdapter(mAdapter);
                }
            }
        });
    }


    private boolean isFavoriteMod = false;
    //버튼 클릭이벤트
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
                isSetting = true;
                Intent i = new Intent(MainAllListActivity.this, MainSettingActivity.class);
                startActivity(i);
            }
        });
        //검색 버튼
        ((Button) findViewById(R.id.btn_search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainAllListActivity.this, MainSearchActivity.class);
                startActivity(i);
            }
        });
    }
    View kMore;
    //default 리스트
    private void getList_Word(){
        try {
            DBManager dbm = new DBManager(this);
            listitem = dbm.All_Word_List_Set("0","","");
        } catch (SQLException se) {
            Log.e("ifeelbluu", se.toString());
        }

        if(kMore == null) {
            kMore = getLayoutInflater().inflate(R.layout.item_more, null);
            subListView.addFooterView(kMore, MORE_CODE, true);
        }
        mAdapter = new MyPostAdapter(this, R.layout.item_eg_list, listitem);
        subListView.setAdapter(mAdapter);

        subListView.setOnItemClickListener(onItemClick_workitem);
    }

    private void Type2Event(){
        ((TextView) findViewById(R.id.btn_bottom_word_paper)).setBackgroundResource(R.drawable.set_btn_round_press);
        ((TextView) findViewById(R.id.btn_bottom_word_paper)).setText("전체보기");
        isFavoriteMod = true;


        try {
            DBManager dbm = new DBManager(this);
            listitem = dbm.Favorite_Word_List_Set("0");
        } catch (SQLException se) {
            Log.e("ifeelbluu", se.toString());
        }

        if(kMore == null) {
            kMore = getLayoutInflater().inflate(R.layout.item_more, null);
            subListView.addFooterView(kMore, MORE_CODE, true);
        }
        mAdapter = new MyPostAdapter(this, R.layout.item_eg_list, listitem);
        subListView.setAdapter(mAdapter);

        subListView.setOnItemClickListener(onItemClick_workitem);
    }

    //page 리스트
    private void getList_Word_Page(String page,String level,String count){
        ArrayList<VO_Item_Level_02_List> additem = new ArrayList<VO_Item_Level_02_List>();
        try {
            DBManager dbm = new DBManager(this);
            if(isFavoriteMod == true){
                additem = dbm.Favorite_Word_List_Set(page);
            }else{
                additem = dbm.All_Word_List_Set(page,level,count);
            }

        } catch (SQLException se) {
            Log.e("ifeelbluu", se.toString());
        }

        if(page.equals("0"))
            listitem.clear();

        for(int i=0; i<additem.size(); i++){
            listitem.add(additem.get(i));
        }

        mAdapter.notifyDataSetChanged();

        if(page.equals("0"))
            subListView.setSelection(0);
    }

    private String Param_Level = "";
    private String Param_Count = "";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(data != null){
//              Select_01 //1:상 2:중 3:하
//				Select_02 //1:1회출제 2:2회출제 3:3회출제 4:4회출제 5:5회출제 7:7회출제 10:10회출제
                String Select_01 = data.getStringExtra("Select_01");
                String Select_02 = data.getStringExtra("Select_02");
                String param1,param2 = "";
                CommonUtil.slevel = Select_01;
                CommonUtil.scount = Select_02;
                if(Select_01.equals("1")){
                    Param_Level = "상";
                }else if(Select_01.equals("2")){
                    Param_Level = "중";
                }else if(Select_01.equals("3")){
                    Param_Level = "하";
                }else{
                    Param_Level = "";
                }

                if(Select_02.equals("0")){
                    Param_Count = "";
                }else{
                    Param_Count = Select_02;
                }
                lastTotalcount = 0;
                getList_Word_Page("0",Param_Level, Param_Count);

            }
        }
    }


    //더보기버튼클릭이벤트
    OnItemClickListener onItemClick_workitem = new OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(l == MORE_CODE){
                getList_Word_Page(listitem.size()+"",Param_Level,Param_Count);
                mAdapter.notifyDataSetChanged();
            }else
                return;
        }
    };

    /*************
     * ViewHolder
     *************/
    class ViewHolder {
        LinearLayout lay;
        TextView txt_count;
        TextView txt_english;
        TextView txt_korean;
        TextView txt_type;

        TextView txt_exam_en;
        TextView txt_exam_ko;

        Button btn_speek;
        Button btn_favorite;
        boolean isFavorite = false;

        TextView txt_english_info;
    }

    /****************
     * Adapter Class
     ****************/
    class MyPostAdapter extends BaseAdapter {
        Context context;
        LayoutInflater Inflater;
        ArrayList<VO_Item_Level_02_List> items;
        int layout;

        public MyPostAdapter(Context context, int layout,
                             ArrayList<VO_Item_Level_02_List> Array_AddList) {
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

        public VO_Item_Level_02_List getItem(int position) {
            return items.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            VO_Item_Level_02_List p = items.get(position);
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.item_eg_list, null);
                ViewHolder holder = new ViewHolder();
                holder.txt_count = (TextView) v.findViewById(R.id.text_count);
                holder.txt_type = (TextView) v.findViewById(R.id.txt_type);
                holder.txt_english = (TextView) v.findViewById(R.id.txt_english);
                holder.txt_korean = (TextView) v.findViewById(R.id.txt_korean);
                holder.btn_speek = (Button) v.findViewById(R.id.btn_speek);
                holder.btn_favorite = (Button) v.findViewById(R.id.btn_favorite);
                holder.txt_exam_en = (TextView) v.findViewById(R.id.txt_en_exam);
                holder.txt_exam_ko = (TextView) v.findViewById(R.id.txt_ko_exam);
                holder.txt_english_info = (TextView) v.findViewById(R.id.txt_english_info);
                v.setTag(holder);
            }
            final ViewHolder holder = (ViewHolder) v.getTag();
            holder.txt_count.setText(p.getLevel2_List_Count());
            holder.txt_type.setText(p.getLevel2_List_Type());
            holder.txt_english.setText(p.getLevel2_List_English());
            holder.txt_korean.setText(p.getLevel2_List_Korean());
            holder.txt_english_info.setText(" ["+p.getLevel2_List_Info5() + "]");


            if(p.getLevel2_List_Info2() == null || p.getLevel2_List_Info2().equals("")){
                Log.v("ifeelbluu","getLevel2_List_Info2 === null");
                holder.isFavorite = false;
            }else{
                Log.v("ifeelbluu","getLevel2_List_Info2 === " + p.getLevel2_List_Info2());
                holder.isFavorite = true;
            }
            final boolean isfav = holder.isFavorite;

            final String id = p.getLevel2_List_Info1();
            final int l_position = position;
            holder.btn_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FavoriteChange(isfav, id, l_position);
                }
            });

            if(holder.isFavorite == false){
                holder.btn_favorite.setBackgroundResource(R.drawable.set_btn_favorite_false);
            }else{
                holder.btn_favorite.setBackgroundResource(R.drawable.set_btn_favorite_true);
            }



            if(is_Ko_Visible == false)
                holder.txt_korean.setVisibility(View.INVISIBLE);
            else
                holder.txt_korean.setVisibility(View.VISIBLE);


            if(is_En_Visible == false) {
                holder.txt_english.setVisibility(View.INVISIBLE);
                holder.txt_english_info.setVisibility(View.INVISIBLE);
            }
            else{
                holder.txt_english.setVisibility(View.VISIBLE);
                holder.txt_english_info.setVisibility(View.VISIBLE);
            }

            if(is_Exam_Visible == false) {
                holder.txt_exam_en.setVisibility(View.GONE);
                holder.txt_exam_ko.setVisibility(View.GONE);
            }else{
                holder.txt_exam_en.setVisibility(View.VISIBLE);
                holder.txt_exam_ko.setVisibility(View.VISIBLE);
                holder.txt_exam_en.setText(p.getLevel2_List_Info3());
                holder.txt_exam_ko.setText(p.getLevel2_List_Info4());
            }



            final String ket = p.getLevel2_List_English();
            holder.btn_speek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    speakEnglish(ket);
                }
            });

            return v;
        }
    }

    //Favorite
    public void FavoriteChange(boolean value, String id, int position){
        DBManager dbm = new DBManager(this);
        if(value){ //즐겨찾기 해제
            listitem.get(position).setLevel2_List_Info2(null);
            dbm.updateFavorite("",id);
        }else{ //즐겨찾기 추가
            listitem.get(position).setLevel2_List_Info2("f");
            dbm.updateFavorite("f",id);
        }

        mAdapter.notifyDataSetChanged();
    }

    //TTS
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(myTTS != null){
            myTTS.shutdown();
        }

        CommonUtil.slevel = "";
        CommonUtil.scount = "";
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
