package co.kr.bigwordenglish;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.kr.bigwordenglish.common.ActivityEx;
import co.kr.bigwordenglish.common.CommonUtil;
import co.kr.bigwordenglish.obj.Noticeobj;
import co.kr.sky.AccumThread;

/*
* 공지사항 api
* url : NoticeSel.php
* param : 없음
* return :
* $key_index = 인덱스
  $title = 제목
  $body = 안에 내용(이미지 url 올거임 이거 그냥 이미지 뷰 해주셈)
  $date = 날짜
  $ea = 조회수
* */
public class SettingActivity extends ActivityEx {
    CommonUtil dataSet = CommonUtil.getInstance();

    private String [][] Object_Array;
    private ArrayList<Noticeobj> arr;

    private AccumThread mThread;
    private Map<String, String> map = new HashMap<String, String>();

    private LinearLayout mCategoryLay;
    private TextView mTitleTv;
    public LayoutInflater mLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        GetNotice();
    }
    //공지사항 가져오기
    private void GetNotice(){
        customProgressPop();
        map.put("url", dataSet.SERVER + "NoticeSel.php");
        // 스레드 생성
        mThread = new AccumThread(this, mAfterAccum, map, 1, 0, null);
        mThread.start(); // 스레드 시작!!
    }
    Handler mAfterAccum = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 0) {
                String res = (String) msg.obj;
                customProgressClose();
                arr.clear();
                //				Map<String, ArrayList<String>> Object_Array = new HashMap<String, ArrayList<String>>();
                Object_Array = (String [][]) msg.obj;
                if (Object_Array.length == 0) {
                    return;
                }
                //				Log.e("CHECK" ,"**********************  --->" + Object_Array[0].length);
                for (int i = 0; i < Object_Array.length; i++) {
                    for (int j = 0; j < Object_Array[0].length; j++) {
                        Log.e("CHECK" ,"value----> ---> Object_Array [" +i+"]["+j+"]"+  Object_Array[i][j]);
                    }
                }
                for (int i = 0; i < (Object_Array[0].length); i++){
                    if (Object_Array[0][i] != null) {
                        arr.add(new Noticeobj(Object_Array[0][i],Object_Array[1][i], Object_Array[2][i], Object_Array[3][i],Object_Array[4][i]));
                    }
                }
            }
        }
    };
    //버튼 리스너 구현 부분
    View.OnClickListener setting_btn = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.setting_btn:
                    Log.e("SKY" , "-- setting_btn --");

                    break;
            }
        }
    };
}
