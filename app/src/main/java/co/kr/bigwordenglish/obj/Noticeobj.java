package co.kr.bigwordenglish.obj;

import android.os.Parcel;
import android.os.Parcelable;

public class Noticeobj implements Parcelable {
//* CREATE TABLE "Word" ("" TEXT,"" TEXT,"" TEXT,"" TEXT,"" TEXT,"" TEXT,"" TEXT,"" TEXT,"" TEXT,"" TEXT,"" TEXT)

    private String key_index;
    private String title;
    private String body;
    private String date;
    private String ea;

    public Noticeobj(String key_index, String title, String body, String date, String ea) {
        this.key_index = key_index;
        this.title = title;
        this.body = body;
        this.date = date;
        this.ea = ea;
    }

    public String getKey_index() {
        return key_index;
    }

    public void setKey_index(String key_index) {
        this.key_index = key_index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEa() {
        return ea;
    }

    public void setEa(String ea) {
        this.ea = ea;
    }

    public Noticeobj(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(key_index);
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(date);
        dest.writeString(ea);
    }

    private void readFromParcel(Parcel in) {

        key_index = in.readString();
        title = in.readString();
        body = in.readString();
        date = in.readString();
        ea = in.readString();
    }

    @SuppressWarnings("rawtypes")
    public static final Creator<Noticeobj> CREATOR = new Creator() {
        public Object createFromParcel(Parcel in) {
            return new Noticeobj(in);
        }

        public Object[] newArray(int size) {
            return new Noticeobj[size];
        }
    };

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }
}