package co.kr.bigwordenglish;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
}
