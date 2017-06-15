package co.kr.bigwordenglish;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Dialog_list extends Dialog {
	
	ListView coopon_list;
	public MyPostAdapter mAdapter;
	ArrayList<String> arry_item = new ArrayList<String>();
	public Dialog_list(Context context, ArrayList<String> item, String title) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_list_1);
		arry_item = item;
		coopon_list = (ListView)findViewById(R.id.coopon_list_topbg);
		
		View header = getLayoutInflater().inflate(R.layout.dialog_list_1_header, null, false);
		((TextView)header.findViewById(R.id.title_text)).setText(title);
        coopon_list.addHeaderView(header);
//		dialog_list_1_item
		mAdapter = new MyPostAdapter(context, R.layout.item_mainsub, arry_item);
		coopon_list.setAdapter(mAdapter);
	}
	
	public void setCooponItemClickListener(AdapterView.OnItemClickListener cooponclicklistener){
		coopon_list.setOnItemClickListener(cooponclicklistener);
	}
	
	/*************
	 * ViewHolder
	 *************/
	class ViewHolder{
		TextView TXT_VIEW;
	}
	
	/****************
	 * Adapter Class
	 ****************/
	class MyPostAdapter extends BaseAdapter{
		Context context;
		LayoutInflater Inflater;
		ArrayList<String> items;
		int layout;
		public MyPostAdapter(Context context, int layout, ArrayList<String> Array_AddList){
			this.items = Array_AddList;
			this.context = context;
			Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		public String getItem(int position) {
			return items.get(position);
		}
		public long getItemId(int position) {
			return position;
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			String p = items.get(position);
			if(v == null){
				LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.item_mainsub, null);
				ViewHolder holder = new ViewHolder();
				holder.TXT_VIEW = (TextView)v.findViewById(R.id.txtitem_01_1);
				v.setTag(holder);
			}
			final ViewHolder holder = (ViewHolder)v.getTag();
			holder.TXT_VIEW.setText(p);
			return v;
		}
	}
}
