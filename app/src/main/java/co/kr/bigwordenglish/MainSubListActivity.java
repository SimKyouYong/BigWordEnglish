package co.kr.bigwordenglish;

import android.content.Context;
import android.database.SQLException;
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

import java.util.ArrayList;

import co.kr.bigwordenglish.common.DBManager;
import co.kr.bigwordenglish.common.VO_Item_Level_02;
import co.kr.bigwordenglish.common.VO_Item_Level_02_List;

public class MainSubListActivity extends AppCompatActivity {
    private ListView subListView;
    private MyPostAdapter mAdapter;


    private String getSubKey;
    private ArrayList<VO_Item_Level_02_List> listitem = new ArrayList<VO_Item_Level_02_List>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_item);


        getSubKey = getIntent().getStringExtra("Subkey_list");
        subListView = (ListView) findViewById(R.id.listview_eg_view);

        Log.v("ifeelbluu","activity_word_item === " +  getSubKey);
        onClickLevel_02(Integer.parseInt(getSubKey));

    }


    private void onClickLevel_02(int subkey){
        listitem = getItem_Level2(subkey);
//        for(int i=0; i<temp.size(); i++){
        mAdapter = new MyPostAdapter(this, R.layout.item_eg_list, listitem);
        subListView.setAdapter(mAdapter);
    }

    //레벨2가져오기
    private ArrayList<VO_Item_Level_02_List> getItem_Level2(int subkey){
        ArrayList<VO_Item_Level_02_List> arr = new ArrayList<VO_Item_Level_02_List>();
        try {
            DBManager dbm = new DBManager(this);
            arr = dbm.selectData_Level2_List(subkey);
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
        TextView txt_count;
        TextView txt_english;
        TextView txt_korean;
        TextView txt_type;

        boolean isFavorite = false;
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
                v.setTag(holder);
            }
            final ViewHolder holder = (ViewHolder) v.getTag();
            holder.txt_count.setText(p.getLevel2_List_Count());
            holder.txt_type.setText(p.getLevel2_List_Type());
            holder.txt_english.setText(p.getLevel2_List_English());
            holder.txt_korean.setText(p.getLevel2_List_Korean());
            return v;
        }
    }
}
