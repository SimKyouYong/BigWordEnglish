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
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.kr.bigwordenglish.common.DBManager;
import co.kr.bigwordenglish.common.VO_Item_Level_02;
import co.kr.bigwordenglish.obj.Mianobj;

public class MainSubActivity extends AppCompatActivity {
    private ListView subListView;
    private MyPostAdapter mAdapter;


    private String getSubKey;
    private ArrayList<VO_Item_Level_02> listitem = new ArrayList<VO_Item_Level_02>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsub);

        getSubKey = getIntent().getStringExtra("SubKey");
        subListView = (ListView) findViewById(R.id.listview_mainsub);

        onClickLevel_02(Integer.parseInt(getSubKey));

    }


    private void onClickLevel_02(int subkey){
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
}
