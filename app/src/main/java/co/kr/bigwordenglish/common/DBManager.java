package co.kr.bigwordenglish.common;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import co.kr.bigwordenglish.EgsMyPreferences;
import co.kr.bigwordenglish.LockScreenActivity;

//DB를 총괄관리
public class DBManager {

  // DB관련 상수 선언
  private static final String dbName = "egDb.db";
  private static final String tableName = "Category";
  public static final int dbVersion = 1;

  // DB관련 객체 선언
  private OpenHelper opener; // DB opener
  private SQLiteDatabase db; // DB controller

  // 부가적인 객체들
  private Context context;

    private String orderby = "";
  // 생성자
  public DBManager(Context context) {
      this.context = context;
      this.opener = new OpenHelper(context, dbName, null, dbVersion);
      db = opener.getWritableDatabase();

      String q3 = EgsMyPreferences.getAppPreferences(context,"select03","Egs");
      if(q3 != null && q3.equals("") == false){
          orderby = q3.split(",")[1];
      }
  }

  // Opener of DB and Table
  private class OpenHelper extends SQLiteOpenHelper {

      public OpenHelper(Context context, String name, CursorFactory factory,
              int version) {
          super(context, name, null, version);
          // TODO Auto-generated constructor stub
      }

      // 생성된 DB가 없을 경우에 한번만 호출됨
      @Override
      public void onCreate(SQLiteDatabase arg0) {
      }

      @Override
      public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
          // TODO Auto-generated method stub
      }
  }

  public ArrayList<VO_Item_Level_02> selectData_Level2(int subkey) {
      String sql = "select * from " + tableName + " where Category_Sub_Key = " + subkey + ";";
      Cursor result = db.rawQuery(sql, null);

      ArrayList<VO_Item_Level_02> array = new ArrayList<VO_Item_Level_02>();
      while (result.moveToNext()) {
    	  VO_Item_Level_02 item = new VO_Item_Level_02(result.getString(0), result.getString(1), result.getString(2), "", false);
    	  array.add(item);
		}
      result.close();
      return array;
  }

  // Default WordList
    public ArrayList<VO_Item_Level_02_List> selectData_Word_List(String subkey) {
        String sql = "select * from 'Word' where col_10 = " + subkey;
        if(CommonUtil.getLevel_03_Q != null && CommonUtil.getLevel_03_Q.equals("") == false)
            sql +=CommonUtil.getLevel_03_Q;


        sql += orderby + " limit 50;";

        Cursor result = db.rawQuery(sql, null);
        Log.v("CHECK_B", "sql = " + sql );
        ArrayList<VO_Item_Level_02_List> array = new ArrayList<VO_Item_Level_02_List>();
        while (result.moveToNext()) {
            VO_Item_Level_02_List item = new VO_Item_Level_02_List(result.getString(3), result.getString(1), result.getString(4), result.getString(5), false, result.getString(0), result.getString(12), result.getString(6), result.getString(7),result.getString(2));
            Log.v("ifeelbluu","-----------------------------");
            Log.v("ifeelbluu",result.getString(3));
            Log.v("ifeelbluu",result.getString(1));
            Log.v("ifeelbluu",result.getString(4));
            Log.v("ifeelbluu",result.getString(5));
            Log.v("ifeelbluu",result.getString(0));
            array.add(item);
        }
        result.close();
        return array;
    }

    //Page WordList
    public ArrayList<VO_Item_Level_02_List> selectData_Word_List_Page(String subkey,String page) {
        String sql = "select * from 'Word' where col_10 = " + subkey;
        if(CommonUtil.getLevel_03_Q != null && CommonUtil.getLevel_03_Q.equals("") == false)
            sql +=CommonUtil.getLevel_03_Q;
        sql += orderby + " limit "+ page + ", 50;";
        Cursor result = db.rawQuery(sql, null);
        Log.v("CHECK_B", "sql = " + sql );
        ArrayList<VO_Item_Level_02_List> array = new ArrayList<VO_Item_Level_02_List>();
        while (result.moveToNext()) {
            VO_Item_Level_02_List item = new VO_Item_Level_02_List(result.getString(3), result.getString(1), result.getString(4), result.getString(5), false, result.getString(0), result.getString(12), result.getString(6), result.getString(7),result.getString(2));
            array.add(item);
        }
        result.close();
        return array;
    }

    //Setting WordList
    public ArrayList<VO_Item_Level_02_List> selectData_Word_List_Set(String subkey, String page, String wordLevel, String wordCount) {
        String sql = "";
        if(page.equals("0")){
            sql = "select * from 'Word' where col_10 = " + subkey;

            if(wordLevel.equals("") == false){
                sql +=  " and col_6 = '"+ wordLevel +"'";
            }

            if(wordCount.equals("") == false){
                sql += " and col_4 >= "+ wordCount;
            }

            if(CommonUtil.getLevel_03_Q != null && CommonUtil.getLevel_03_Q.equals("") == false)
                sql +=CommonUtil.getLevel_03_Q;
            sql += orderby + " limit 50;";

        }else{
            sql = "select * from 'Word' where col_10 = " + subkey;
            if(wordLevel.equals("") == false){
                sql +=  " and col_6 = '"+ wordLevel +"'";
            }

            if(wordCount.equals("") == false){
                sql += " and col_4 >= "+ wordCount;
            }

            if(CommonUtil.getLevel_03_Q != null && CommonUtil.getLevel_03_Q.equals("") == false)
                sql +=CommonUtil.getLevel_03_Q;
            sql += orderby + " limit "+ page + ", 50;";
        }

        Log.v("CHECK_B", "sql = " + sql );
        Cursor result = db.rawQuery(sql, null);

        ArrayList<VO_Item_Level_02_List> array = new ArrayList<VO_Item_Level_02_List>();
        while (result.moveToNext()) {
            VO_Item_Level_02_List item = new VO_Item_Level_02_List(result.getString(3), result.getString(1), result.getString(4), result.getString(5), false, result.getString(0), result.getString(12), result.getString(6), result.getString(7),result.getString(2));
            Log.v("ifeelbluu","-----------------------------");
            Log.v("ifeelbluu",result.getString(3));
            Log.v("ifeelbluu",result.getString(1));
            Log.v("ifeelbluu",result.getString(4));
            Log.v("ifeelbluu",result.getString(5));
            Log.v("ifeelbluu",result.getString(0));
            array.add(item);
        }
        result.close();
        return array;
    }

    //All WordList
    public ArrayList<VO_Item_Level_02_List> All_Word_List_Set(String page) {
        String sql = "";
        if(page.equals("0")){
            sql = "select * from 'Word'";

            sql += orderby + " limit 50;";

        }else{
            sql = "select * from 'Word'";

            sql += orderby + " limit "+ page + ", 50;";

        }
        Cursor result = db.rawQuery(sql, null);
        Log.v("CHECK_B", "sql = " + sql );
        ArrayList<VO_Item_Level_02_List> array = new ArrayList<VO_Item_Level_02_List>();
        while (result.moveToNext()) {
            VO_Item_Level_02_List item = new VO_Item_Level_02_List(result.getString(3), result.getString(1), result.getString(4), result.getString(5), false, result.getString(0), result.getString(12), result.getString(6), result.getString(7),result.getString(2));
            Log.v("ifeelbluu","-----------------------------");
            Log.v("ifeelbluu",result.getString(3));
            Log.v("ifeelbluu",result.getString(1));
            Log.v("ifeelbluu",result.getString(4));
            Log.v("ifeelbluu",result.getString(5));
            Log.v("ifeelbluu",result.getString(0));
            array.add(item);
        }
        result.close();
        return array;
    }

    //Favorite WordList
    public ArrayList<VO_Item_Level_02_List> Favorite_Word_List_Set(String page) {
        String sql = "";
        if(page.equals("0")){
            sql = "select * from 'Word' where col_13 = 'f'";

            sql += orderby + " limit 50;";

        }else{
            sql = "select * from 'Word' where col_13 = 'f'";

            sql += orderby + " limit "+ page + ", 50;";

        }
        Cursor result = db.rawQuery(sql, null);

        ArrayList<VO_Item_Level_02_List> array = new ArrayList<VO_Item_Level_02_List>();
        while (result.moveToNext()) {
            VO_Item_Level_02_List item = new VO_Item_Level_02_List(result.getString(3), result.getString(1), result.getString(4), result.getString(5), false, result.getString(0), result.getString(12), result.getString(6), result.getString(7),result.getString(2));
            Log.v("ifeelbluu","-----------------------------");
            Log.v("ifeelbluu",result.getString(3));
            Log.v("ifeelbluu",result.getString(1));
            Log.v("ifeelbluu",result.getString(4));
            Log.v("ifeelbluu",result.getString(5));
            Log.v("ifeelbluu",result.getString(0));
            array.add(item);
        }
        result.close();
        return array;
    }

    //즐겨찾기 추가
    public void updateFavorite(String value, String id) {
        String sql = "";
        if(value.equals(""))
            sql = "update Word set col_13 = null where col_1 = " + id;
        else
            sql = "update Word set col_13 = '" + value + "' where col_1 = '" + id+"'";

        db.execSQL(sql);
    }

    public String randomWord(String id){
        String sql = "select * from 'Word' where col_1 = "+ id;
        Cursor result = db.rawQuery(sql, null);
        String temp = "";
        while (result.moveToNext()) {
            temp +=result.getString(1)+"-=-=";//en
            temp +=result.getString(4)+"-=-=";//ko
            temp +=result.getString(6)+"-=-=";//e_en
            temp +=result.getString(7)+"-=-=";//e_ko
            temp +=result.getString(3)+"-=-=";//count
            temp +=result.getString(5)+"-=-=";//level
            temp +=result.getString(2);
        }
        return temp;
    }

    public String getWordCount(String q){
        Log.v("ifeelbluu", "q ======= " + q);
        int rancount = 11440;
        String temp = "";

        Cursor result;

        if(q.equals("") == false){
            String sql = "select * from 'Word'";
            sql += " " + q+";";
            result = db.rawQuery(sql, null);
            rancount = result.getCount();

            Log.v("ifeelbluu", "sql === " + sql);
            Log.v("ifeelbluu", "rancount == " + rancount);

            Random ran = new Random();
            int r = ran.nextInt(rancount);
            LockScreenActivity.getRows = r;

            Log.v("ifeelbluu","index == " + r);
            result.moveToPosition(r);
            temp +=result.getString(1)+"-=-=";//en
            temp +=result.getString(4)+"-=-=";//ko
            temp +=result.getString(6)+"-=-=";//e_en
            temp +=result.getString(7)+"-=-=";//e_ko
            temp +=result.getString(3)+"-=-=";//count
            temp +=result.getString(5)+"-=-=";//level
            temp +=result.getString(2);
        }else{
            Random ran = new Random();
            int r = ran.nextInt(rancount) + 1;

            LockScreenActivity.getRows = r;
            temp = randomWord(r+"");
        }
        return temp;
    }

    public String getWordNext(String q, int page){
        Log.v("ifeelbluu", "q ======= " + q);
        String temp = "";

        Cursor result;

        if(q.equals("") == false){
            String sql = "select * from 'Word'";
            sql += " " + q+";";

            result = db.rawQuery(sql, null);
            Log.v("ifeelbluu", "sql === " + sql);
//            if(page > result.getCount()){
//                return null;
//            }
            result.moveToPosition(page);
            temp +=result.getString(1)+"-=-=";//en
            temp +=result.getString(4)+"-=-=";//ko
            temp +=result.getString(6)+"-=-=";//e_en
            temp +=result.getString(7)+"-=-=";//e_ko
            temp +=result.getString(3)+"-=-=";//count
            temp +=result.getString(5)+"-=-=";//level
            temp +=result.getString(2);
        }else{
            temp = randomWord(page+"");
        }
        return temp;
    }
}

