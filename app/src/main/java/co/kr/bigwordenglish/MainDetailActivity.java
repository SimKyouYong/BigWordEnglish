package co.kr.bigwordenglish;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.kr.bigwordenglish.obj.Mianobj;

/*
* TABLE CREATE[JSW] : object 만들때 String 이름 아래껄로 만들기.
* CREATE TABLE "Word" ("Key_index" TEXT,"Word" TEXT,"Symbol" TEXT,"Ea" TEXT,"Word_mean" TEXT,"Difficulty" TEXT,"Example" TEXT,"Translate" TEXT,"Year" TEXT,"P_key_index" TEXT,"Category_sub" TEXT)
* */
public class MainDetailActivity extends AppCompatActivity {


    private LinearLayout mCategoryLay;
    private TextView mNoTv, mWordTv, mLevelTv;
    private ImageView mPronunciationTv , mMemoTv;
    public LayoutInflater mLayoutInflater;


    private Mianobj obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maindetail);

        Bundle bundle = getIntent().getExtras();
        obj = bundle.getParcelable("OBJ");


        mLayoutInflater         	=	(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCategoryLay                = (LinearLayout) findViewById(R.id.category_lay);

        SELECT_Word("EgDb.db" , "WHERE P_key_index = '" + obj.getKey_index() + "'");


    }
    private void SELECT_Word(String DB , String Where)		//디비 값 조회해서 저장하기
    {
        ArrayList<String> arr = new ArrayList<String>();
        try{
            //  db파일 읽어오기
            SQLiteDatabase db = openOrCreateDatabase(DB, Context.MODE_PRIVATE, null);
            // 쿼리로 db의 커서 획득
            String sql = "SELECT * FROM `Word` "  + Where;
            Log.e("SKY","sql :: " + sql);
            Cursor cur = db.rawQuery(sql, null);
            // 처음 레코드로 이동
            while(cur.moveToNext()){
                // 읽은값 출력
                //[JSW] 단어 쭉 뿌리삼..  cur.getString(0) ~ cur.getString(11) 까지 있음! OBJECT 로 담고, 리스트로 뿌려..
                Log.e("SKY",cur.getString(0)+"/"+cur.getString(1));
            }
            cur.close();
            db.close();
        }
        catch (SQLException se) {
            // TODO: handle exception
            Log.e("selectData()Error! : ",se.toString());
        }

    }

    /**
     * 메인 리스트 레이아웃
     * @param item 메인 리스트 데이터
     * @param detaillistlay 메인 화면
     */
    public void MainListLayout(final ArrayList<String> item, final LinearLayout detaillistlay) {

        detaillistlay.removeAllViews();

        for (int i = 0 ; i < item.size(); i++) {
            final int pos = i;
            View view = mLayoutInflater.inflate(R.layout.little_detaillist, null);

            mNoTv   =  (TextView) view.findViewById(R.id.no_tv); // 출제횟수
            mWordTv   =  (TextView) view.findViewById(R.id.word_tv); // 단어
            mLevelTv   =  (TextView) view.findViewById(R.id.level_tv); // 난이도
            mPronunciationTv   =  (ImageView) view.findViewById(R.id.pronunciation_tv); // 발음
            mMemoTv   =  (ImageView) view.findViewById(R.id.memo_tv); // 단어장

            mNoTv.setText(item.get(i));
            mNoTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainDetailActivity.this, "클릭한 포지션 --> " + pos, Toast.LENGTH_SHORT).show();

                }
            });

            detaillistlay.addView(view);
        }

    }
}
