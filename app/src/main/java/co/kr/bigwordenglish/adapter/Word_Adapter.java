package co.kr.bigwordenglish.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import co.kr.bigwordenglish.R;
import co.kr.bigwordenglish.obj.Wordobj;

public class Word_Adapter extends BaseAdapter {

	private Activity activity;
	private static LayoutInflater inflater=null;
	ArrayList<Wordobj> items;
	private Typeface ttf;

	public Word_Adapter(Activity a, ArrayList<Wordobj> m_board  ) {
		activity = a;

		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		items = m_board;
		ttf = Typeface.createFromAsset(activity.getAssets(), "HANYGO230.TTF");

	}

	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		TextView t_title , t_address , t_number;
	}
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Wordobj board = items.get(position);
		ViewHolder vh = new ViewHolder();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.activity_word_item,null);
			vh.t_title = (TextView) convertView.findViewById(R.id.t_title); 
			vh.t_address = (TextView) convertView.findViewById(R.id.t_address); 
			vh.t_number = (TextView) convertView.findViewById(R.id.t_number); 

			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.t_title.setTypeface(ttf);
		vh.t_address.setTypeface(ttf);
		vh.t_number.setTypeface(ttf);
		
//		vh.t_title.setText(board.getChurch_name());
//		vh.t_address.setText(board.getChurch_address());
//		vh.t_number.setText(board.getChurch_number());
		return convertView;
	}

}