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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.kr.bigwordenglish.obj.Mianobj;

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
public class MainActivity extends AppCompatActivity {


    private LinearLayout mCategoryLay;
    private TextView mTitleTv;
    public LayoutInflater mLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayoutInflater         	=	(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCategoryLay                = (LinearLayout) findViewById(R.id.category_lay);


        MainListLayout(SELECT_MainLabel("EgDb.db" , ""), mCategoryLay);


    }
    private ArrayList<Mianobj> SELECT_MainLabel(String DB , String Where)		//디비 값 조회해서 저장하기
    {
        ArrayList<Mianobj> arr = new ArrayList<Mianobj>();
        try{
            //  db파일 읽어오기
            SQLiteDatabase db = openOrCreateDatabase(DB, Context.MODE_PRIVATE, null);
            // 쿼리로 db의 커서 획득
            Cursor cur = db.rawQuery("SELECT * FROM `Main_Category`;", null);
            // 처음 레코드로 이동
            while(cur.moveToNext()){
                // 읽은값 출력
                Log.e("SKY",cur.getString(0)+"/"+cur.getString(1));
                arr.add(new Mianobj(cur.getString(0) , cur.getString(1)));
            }
            cur.close();
            db.close();

        }
        catch (SQLException se) {
            // TODO: handle exception
            Log.e("selectData()Error! : ",se.toString());
        }
        return arr;

    }
    private ArrayList<Mianobj> SELECT_SUBLabel(String DB , String Where)		//디비 값 조회해서 저장하기
    {
        ArrayList<Mianobj> arr = new ArrayList<Mianobj>();
        try{
            //  db파일 읽어오기
            SQLiteDatabase db = openOrCreateDatabase(DB, Context.MODE_PRIVATE, null);
            // 쿼리로 db의 커서 획득
            String sql = "SELECT * FROM `Category_Sub` " + Where;
            Log.e("SKY", "sql :: " + sql);
            Cursor cur = db.rawQuery(sql, null);
            // 처음 레코드로 이동
            while(cur.moveToNext()){
                // 읽은값 출력
                //[JSW] 여기서부터 처리해......
                Log.e("SKY",cur.getString(0)+"/"+cur.getString(1)+"/"+cur.getString(2));
            }
            cur.close();
            db.close();

        }
        catch (SQLException se) {
            // TODO: handle exception
            Log.e("selectData()Error! : ",se.toString());
        }
        return arr;

    }
    /**
     * 메인 리스트 레이아웃
     * @param item 메인 리스트 데이터
     * @param categorylay 메인 화면
     */
    public void MainListLayout(final ArrayList<Mianobj> item, final LinearLayout categorylay) {

        categorylay.removeAllViews();

        for (int i = 0 ; i < item.size(); i++) {
            final int pos = i;
            View view = mLayoutInflater.inflate(R.layout.little_categorylist, null);

            mTitleTv   =  (TextView) view.findViewById(R.id.title_tv);
            mTitleTv.setText(item.get(i).getCategory());
            mTitleTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
//                    Toast.makeText(MainActivity.this, "클릭한 포지션 --> " + pos, Toast.LENGTH_SHORT).show();
                    Log.e("SKY","item.get(pos).getCategory() :: " +item.get(pos).getCategory());
                    if (item.get(pos).getCategory().equals("영화") || item.get(pos).getCategory().equals("드라마")) {
                        // 영화나 드라마 일경우 디비셀렉 다시해서 재호출
                        SELECT_SUBLabel("EgDb.db" , "where Category_Sub_Key = '" + item.get(pos).getKey_index() + "'");
                        intent = new Intent(MainActivity.this, MainSubActivity.class);
                        intent.putExtra("OBJ" , item.get(pos).getKey_index());
                        startActivity(intent);
                    }else {
                        intent = new Intent(MainActivity.this, MainDetailActivity.class);
                        intent.putExtra("OBJ" , item.get(pos));
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "이거슨 인텐트--> " + item.get(pos), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            categorylay.addView(view);
        }

    }
}
