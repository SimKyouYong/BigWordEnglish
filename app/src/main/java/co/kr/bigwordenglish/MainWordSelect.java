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
import co.kr.bigwordenglish.obj.Mianobj;
public class MainWordSelect extends AppCompatActivity {


    private LinearLayout mCategoryLay;
    private TextView mTitleTv;
    public LayoutInflater mLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_work_src);

//        mLayoutInflater         	=	(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mCategoryLay                = (LinearLayout) findViewById(R.id.category_lay);
//
//
//        MainListLayout(SELECT_MainLabel("EgDb.db" , ""), mCategoryLay);
//
//
//        findViewById(R.id.setting_btn).setOnClickListener(btnListener);
        onClickEvent();
        CheckWordEvent();
    }
    
    private void onClickEvent(){
    	((Button) findViewById(R.id.btn_setting)).setVisibility(View.GONE);
		((Button) findViewById(R.id.btn_home))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
    }
    //버튼 리스너 구현 부분
    View.OnClickListener btnListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.setting_btn:
                    Log.e("SKY" , "-- setting_btn --");

                    break;
            }
        }
    };
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
                        intent = new Intent(MainWordSelect.this, MainSubActivity.class);
                        intent.putExtra("OBJ" , item.get(pos).getKey_index());
                        startActivity(intent);
                    }else {
                        intent = new Intent(MainWordSelect.this, MainDetailActivity.class);
                        intent.putExtra("OBJ" , item.get(pos));
                        startActivity(intent);
                        Toast.makeText(MainWordSelect.this, "이거슨 인텐트--> " + item.get(pos), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            categorylay.addView(view);
        }

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
				((LinearLayout) findViewById(R.id.check_work01)).setBackgroundResource(R.drawable.bg_search_set_on);
				((LinearLayout) findViewById(R.id.check_work02)).setBackgroundResource(R.drawable.bg_search_set_off);
				((LinearLayout) findViewById(R.id.check_work03)).setBackgroundResource(R.drawable.bg_search_set_off);
			}
		});
    	((LinearLayout) findViewById(R.id.check_work02))
    	.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Select_01 = 2;
    			((LinearLayout) findViewById(R.id.check_work01)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work02)).setBackgroundResource(R.drawable.bg_search_set_on);
				((LinearLayout) findViewById(R.id.check_work03)).setBackgroundResource(R.drawable.bg_search_set_off);
    		}
    	});
    	((LinearLayout) findViewById(R.id.check_work03))
    	.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Select_01 = 3;
    			((LinearLayout) findViewById(R.id.check_work01)).setBackgroundResource(R.drawable.bg_search_set_off);
				((LinearLayout) findViewById(R.id.check_work02)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work03)).setBackgroundResource(R.drawable.bg_search_set_on);
    		}
    	});
    	
    	//출제횟수~~
    	((LinearLayout) findViewById(R.id.check_work04))
    	.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Select_02 = 1;
    			((LinearLayout) findViewById(R.id.check_work04)).setBackgroundResource(R.drawable.bg_search_set_on);
				((LinearLayout) findViewById(R.id.check_work05)).setBackgroundResource(R.drawable.bg_search_set_off);
				((LinearLayout) findViewById(R.id.check_work06)).setBackgroundResource(R.drawable.bg_search_set_off);
				((LinearLayout) findViewById(R.id.check_work07)).setBackgroundResource(R.drawable.bg_search_set_off);
				((LinearLayout) findViewById(R.id.check_work08)).setBackgroundResource(R.drawable.bg_search_set_off);
				((LinearLayout) findViewById(R.id.check_work09)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work10)).setBackgroundResource(R.drawable.bg_search_set_off);
    		}
    	});
    	((LinearLayout) findViewById(R.id.check_work05))
    	.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Select_02 = 2;
    			((LinearLayout) findViewById(R.id.check_work04)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work05)).setBackgroundResource(R.drawable.bg_search_set_on);
    			((LinearLayout) findViewById(R.id.check_work06)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work07)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work08)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work09)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work10)).setBackgroundResource(R.drawable.bg_search_set_off);
    		}
    	});
    	((LinearLayout) findViewById(R.id.check_work06))
    	.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Select_02 = 3;
    			((LinearLayout) findViewById(R.id.check_work04)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work05)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work06)).setBackgroundResource(R.drawable.bg_search_set_on);
    			((LinearLayout) findViewById(R.id.check_work07)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work08)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work09)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work10)).setBackgroundResource(R.drawable.bg_search_set_off);
    		}
    	});
    	((LinearLayout) findViewById(R.id.check_work07))
    	.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Select_02 = 4;
    			((LinearLayout) findViewById(R.id.check_work04)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work05)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work06)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work07)).setBackgroundResource(R.drawable.bg_search_set_on);
    			((LinearLayout) findViewById(R.id.check_work08)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work09)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work10)).setBackgroundResource(R.drawable.bg_search_set_off);
    		}
    	});
    	((LinearLayout) findViewById(R.id.check_work08))
    	.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Select_02 = 5;
    			((LinearLayout) findViewById(R.id.check_work04)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work05)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work06)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work07)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work08)).setBackgroundResource(R.drawable.bg_search_set_on);
    			((LinearLayout) findViewById(R.id.check_work09)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work10)).setBackgroundResource(R.drawable.bg_search_set_off);
    		}
    	});
    	((LinearLayout) findViewById(R.id.check_work09))
    	.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Select_02 = 6;
    			((LinearLayout) findViewById(R.id.check_work04)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work05)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work06)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work07)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work08)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work09)).setBackgroundResource(R.drawable.bg_search_set_on);
    			((LinearLayout) findViewById(R.id.check_work10)).setBackgroundResource(R.drawable.bg_search_set_off);
    		}
    	});
    	((LinearLayout) findViewById(R.id.check_work10))
    	.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Select_02 = 7;
    			((LinearLayout) findViewById(R.id.check_work04)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work05)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work06)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work07)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work08)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work09)).setBackgroundResource(R.drawable.bg_search_set_off);
    			((LinearLayout) findViewById(R.id.check_work10)).setBackgroundResource(R.drawable.bg_search_set_on);
    		}
    	});
    }
}
