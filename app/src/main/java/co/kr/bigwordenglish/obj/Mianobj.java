package co.kr.bigwordenglish.obj;

import android.os.Parcel;
import android.os.Parcelable;


public class Mianobj implements Parcelable{

	public static Creator<Mianobj> getCreator() {
		return CREATOR;
	}

	String key_index , category;

	public Mianobj(String key_index, String category) {
		this.key_index = key_index;
		this.category = category;
	}
	public String getKey_index() {
		return key_index;
	}
	public void setKey_index(String key_index) {
		this.key_index = key_index;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Mianobj(Parcel in) {
	       readFromParcel(in);
	    }
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(key_index);
		dest.writeString(category);
	}
	private void readFromParcel(Parcel in){

		key_index = in.readString();
		category = in.readString();
	}

	@SuppressWarnings("rawtypes")
	public static final Creator<Mianobj> CREATOR = new Creator() {
		public Object createFromParcel(Parcel in) {
			return new Mianobj(in);
		}

		public Object[] newArray(int size) {
			return new Mianobj[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
}