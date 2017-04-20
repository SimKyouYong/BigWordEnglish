package co.kr.bigwordenglish;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private LinearLayout mCategoryLay;
    private ArrayList<String> mMenuItem = new ArrayList<String>();
    private TextView mTitleTv;
    public LayoutInflater mLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayoutInflater	=	(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mCategoryLay = (LinearLayout) findViewById(R.id.category_lay);

        mMenuItem.add("카테고리1-1");
        mMenuItem.add("카테고리1-2");
        mMenuItem.add("카테고리1 All 단어");
        mMenuItem.add("카테고리2-1");
        mMenuItem.add("카테고리2-2");
        mMenuItem.add("카테고리 All 단어");

        MainListLayout(mMenuItem, mCategoryLay);    // 대표메뉴 UI 추가

    }


    /**
     * 메인 리스트 레이아웃
     * @param item 메인 리스트 데이터
     * @param categorylay 메인 화면
     */
    public void MainListLayout(final ArrayList<String> item, final LinearLayout categorylay) {

        categorylay.removeAllViews();

        for (int i = 0 ; i < item.size(); i++) {
            final int pos = i;
            View view = mLayoutInflater.inflate(R.layout.little_categorylist, null);

            mTitleTv   =  (TextView) view.findViewById(R.id.title_tv); // 타이틀 제목
            mTitleTv.setText(mMenuItem.get(i));
            mTitleTv.setOnClickListener(new View.OnClickListener() {    // 답글 삭제버튼
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "클릭한 포지션 --> " + pos, Toast.LENGTH_SHORT).show();

                }
            });

            categorylay.addView(view);
        }

    }
}
