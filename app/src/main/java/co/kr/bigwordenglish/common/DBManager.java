package co.kr.bigwordenglish.common;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

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

  // 생성자
  public DBManager(Context context) {
      this.context = context;
      this.opener = new OpenHelper(context, dbName, null, dbVersion);
      db = opener.getWritableDatabase();
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

//  // 데이터 추가
//  public void insertData(APinfo info) {
//      String sql = "insert into " + tableName + " values(NULL, '"
//              + info.getSSID() + "', " + info.getCapabilities() + ", '"
//              + info.getPasswd() + "');";
//      db.execSQL(sql);
//  }
//
//  // 데이터 갱신
//  public void updateData(APinfo info, int index) {
//      String sql = "update " + tableName + " set SSID = '" + info.getSSID()
//              + "', capabilities = " + info.getCapabilities()
//              + ", passwd = '" + info.getPasswd() + "' where id = " + index
//              + ";";
//      db.execSQL(sql);
//  }
//
//  // 데이터 삭제
//  public void removeData(int index) {
//      String sql = "delete from " + tableName + " where id = " + index + ";";
//      db.execSQL(sql);
//  }

  // Level2 검색
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
        String sql = "select * from 'Word' where col_10 = " + subkey + " limit 50;";

        Cursor result = db.rawQuery(sql, null);

        ArrayList<VO_Item_Level_02_List> array = new ArrayList<VO_Item_Level_02_List>();
        while (result.moveToNext()) {
            VO_Item_Level_02_List item = new VO_Item_Level_02_List(result.getString(3), result.getString(1), result.getString(4), result.getString(5), false, result.getString(0), "2", "3", "4");
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
        String sql = "select * from 'Word' where col_10 = " + subkey + " and col_1 > "+ page +" limit 50;";
        Cursor result = db.rawQuery(sql, null);

        ArrayList<VO_Item_Level_02_List> array = new ArrayList<VO_Item_Level_02_List>();
        while (result.moveToNext()) {
            VO_Item_Level_02_List item = new VO_Item_Level_02_List(result.getString(3), result.getString(1), result.getString(4), result.getString(5), false, result.getString(0), "2", "3", "4");
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

            sql += " limit 50;";

        }else{
            sql = "select * from 'Word' where col_10 = " + subkey + " and col_1 >" + page;
//            sql = "select * from 'Word' where col_10 = " + subkey + " and col_1 >" + page + " and col_4 > "+ wordCount + " and col_6 = '"+ wordLevel +"' limit 50;";
            if(wordLevel.equals("") == false){
                sql +=  " and col_6 = '"+ wordLevel +"'";
            }

            if(wordCount.equals("") == false){
                sql += " and col_4 >= "+ wordCount;
            }

            sql += " limit 50;";

        }
        Cursor result = db.rawQuery(sql, null);

        ArrayList<VO_Item_Level_02_List> array = new ArrayList<VO_Item_Level_02_List>();
        while (result.moveToNext()) {
            VO_Item_Level_02_List item = new VO_Item_Level_02_List(result.getString(3), result.getString(1), result.getString(4), result.getString(5), false, result.getString(0), "2", "3", "4");
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
}

