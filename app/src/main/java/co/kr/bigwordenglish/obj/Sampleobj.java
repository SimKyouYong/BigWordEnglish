package co.kr.bigwordenglish.obj;

import android.os.Parcel;
import android.os.Parcelable;



public class Sampleobj implements Parcelable{

	private String index , type , title_kr , title_image , title_en , swith , city_info , mp3_file,city_img;
	private String down_flag;
	public static Creator<Sampleobj> getCreator() {
		return CREATOR;
	}
	
	public String getCity_img() {
		return city_img;
	}

	public void setCity_img(String city_img) {
		this.city_img = city_img;
	}

	public Sampleobj(String index, String type, String title_kr,
			String title_image, String title_en, String swith,
			String city_info, String mp3_file, String city_img, String down_flag) {
		super();
		this.index = index;
		this.type = type;
		this.title_kr = title_kr;
		this.title_image = title_image;
		this.title_en = title_en;
		this.swith = swith;
		this.city_info = city_info;
		this.mp3_file = mp3_file;
		this.city_img = city_img;
		this.down_flag = down_flag;
	}

	public String getDown_flag() {
		return down_flag;
	}

	public void setDown_flag(String down_flag) {
		this.down_flag = down_flag;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle_kr() {
		return title_kr;
	}

	public void setTitle_kr(String title_kr) {
		this.title_kr = title_kr;
	}

	public String getTitle_image() {
		return title_image;
	}

	public void setTitle_image(String title_image) {
		this.title_image = title_image;
	}

	public String getTitle_en() {
		return title_en;
	}

	public void setTitle_en(String title_en) {
		this.title_en = title_en;
	}

	public String getSwith() {
		return swith;
	}

	public void setSwith(String swith) {
		this.swith = swith;
	}

	public String getCity_info() {
		return city_info;
	}

	public void setCity_info(String city_info) {
		this.city_info = city_info;
	}

	public String getMp3_file() {
		return mp3_file;
	}

	public void setMp3_file(String mp3_file) {
		this.mp3_file = mp3_file;
	}

	public Sampleobj(Parcel in) {
	       readFromParcel(in);
	    }
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(index);
		dest.writeString(type);
		dest.writeString(title_kr);
		dest.writeString(title_image);
		dest.writeString(title_en);
		dest.writeString(swith);
		dest.writeString(city_info);
		dest.writeString(mp3_file);
		dest.writeString(city_img);
		dest.writeString(down_flag);
	}
	private void readFromParcel(Parcel in){
		
		index = in.readString();
		type = in.readString();
		title_kr = in.readString();
		title_image = in.readString();
		title_en = in.readString();
		swith = in.readString();
		city_info = in.readString();
		mp3_file = in.readString();
		city_img = in.readString();
		down_flag = in.readString();
	}

	@SuppressWarnings("rawtypes")
	public static final Creator<Sampleobj> CREATOR = new Creator() {
		public Object createFromParcel(Parcel in) {
			return new Sampleobj(in);
		}

		public Object[] newArray(int size) {
			return new Sampleobj[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
}