package co.kr.bigwordenglish.obj;

import android.os.Parcel;
import android.os.Parcelable;


public class Wordobj implements Parcelable {
//* CREATE TABLE "Word" ("" TEXT,"" TEXT,"" TEXT,"" TEXT,"" TEXT,"" TEXT,"" TEXT,"" TEXT,"" TEXT,"" TEXT,"" TEXT)

    private String Key_index;
    private String Word;
    private String Symbol;
    private String Ea;
    private String Word_mean;
    private String Difficulty;
    private String Example;
    private String Translate;
    private String Year;
    private String P_key_index;
    private String Category_sub;

    public Wordobj(String key_index, String word, String symbol, String ea, String word_mean, String difficulty, String example, String translate, String year, String p_key_index, String category_sub) {
        Key_index = key_index;
        Word = word;
        Symbol = symbol;
        Ea = ea;
        Word_mean = word_mean;
        Difficulty = difficulty;
        Example = example;
        Translate = translate;
        Year = year;
        P_key_index = p_key_index;
        Category_sub = category_sub;
    }

    public String getKey_index() {
        return Key_index;
    }

    public void setKey_index(String key_index) {
        Key_index = key_index;
    }

    public String getWord() {
        return Word;
    }

    public void setWord(String word) {
        Word = word;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }

    public String getEa() {
        return Ea;
    }

    public void setEa(String ea) {
        Ea = ea;
    }

    public String getWord_mean() {
        return Word_mean;
    }

    public void setWord_mean(String word_mean) {
        Word_mean = word_mean;
    }

    public String getDifficulty() {
        return Difficulty;
    }

    public void setDifficulty(String difficulty) {
        Difficulty = difficulty;
    }

    public String getExample() {
        return Example;
    }

    public void setExample(String example) {
        Example = example;
    }

    public String getTranslate() {
        return Translate;
    }

    public void setTranslate(String translate) {
        Translate = translate;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getP_key_index() {
        return P_key_index;
    }

    public void setP_key_index(String p_key_index) {
        P_key_index = p_key_index;
    }

    public String getCategory_sub() {
        return Category_sub;
    }

    public void setCategory_sub(String category_sub) {
        Category_sub = category_sub;
    }

    public static Creator<Wordobj> getCreator() {
        return CREATOR;
    }


    public Wordobj(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(Key_index);
        dest.writeString(Word);
        dest.writeString(Symbol);
        dest.writeString(Ea);
        dest.writeString(Word_mean);
        dest.writeString(Difficulty);
        dest.writeString(Example);
        dest.writeString(Translate);
        dest.writeString(Year);
        dest.writeString(P_key_index);
        dest.writeString(Category_sub);
    }

    private void readFromParcel(Parcel in) {

        Key_index = in.readString();
        Word = in.readString();
        Symbol = in.readString();
        Ea = in.readString();
        Word_mean = in.readString();
        Difficulty = in.readString();
        Example = in.readString();
        Translate = in.readString();
        Year = in.readString();
        P_key_index = in.readString();
        Category_sub = in.readString();
    }

    @SuppressWarnings("rawtypes")
    public static final Creator<Wordobj> CREATOR = new Creator() {
        public Object createFromParcel(Parcel in) {
            return new Wordobj(in);
        }

        public Object[] newArray(int size) {
            return new Wordobj[size];
        }
    };

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }
}