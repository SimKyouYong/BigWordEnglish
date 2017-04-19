package co.kr.bigwordenglish.common;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	static String NAME = "hellow.sqlite";		//sqlite 占싱몌옙 占쏙옙占쏙옙 
	static CursorFactory FACTORY = null;		//커占쏙옙 占십깍옙화 
	static String PACKEGE = "co.kr.app.helloweurope";	//占쏙옙키占쏙옙 占싱몌옙 占쏙옙占쏙옙 
	static String DB = "hellow_db.db";			//칼占싸몌옙 占쏙옙占� 占싱몌옙 占쏙옙占쏙옙 
	static int VERSION  = 1;					//占쏙옙占� 占쏙옙占쏙옙 
	public MySQLiteOpenHelper(Context context) {
		super(context, NAME, FACTORY, VERSION);
		// TODO Auto-generated constructor stub
		try {
			boolean bResult = isCheckDB(context);  // DB占쏙옙 占쌍댐옙占쏙옙?
			Log.i("MiniApp", "DB Check="+bResult);
			if(!bResult){   // DB占쏙옙 占쌌댐옙占쏙옙 확占쏙옙 
				copyDB(context);		//占쏙옙占� 占쏙옙占쏙옙占쏙옙 占쏙옙 占쏙옙占쏙옙 
			}else{

			}
		} catch (Exception e) {

		}
	}
	// DB占쏙옙 占쌍놂옙 체크占싹깍옙
	public boolean isCheckDB(Context mContext){
		String filePath = "/data/data/" + PACKEGE + "/databases/" + DB;
		File file = new File(filePath);

		if (file.exists()) {
			return true;
		}

		return false;

	}
	// DB占쏙옙 占쏙옙占쏙옙占싹깍옙
	// assets占쏙옙 /db/xxxx.db 占쏙옙占쏙옙占쏙옙 占쏙옙치占쏙옙 占쏙옙占싸그뤄옙占쏙옙 占쏙옙占쏙옙 DB占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占싹깍옙
	public void copyDB(Context mContext){
		Log.d("MiniApp", "copyDB");
		AssetManager manager = mContext.getAssets();
		String folderPath = "/data/data/" + PACKEGE + "/databases";
		String filePath = "/data/data/" + PACKEGE + "/databases/" +DB;
		File folder = new File(folderPath);
		File file = new File(filePath);

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			InputStream is = manager.open("db/" + DB);
			BufferedInputStream bis = new BufferedInputStream(is);

			if (folder.exists()) {
			}else{
				folder.mkdirs();
			}


			if (file.exists()) {
				file.delete();
				file.createNewFile();
			}

			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			int read = -1;
			byte[] buffer = new byte[1024];
			while ((read = bis.read(buffer, 0, 1024)) != -1) {
				bos.write(buffer, 0, read);
			}

			bos.flush();

			bos.close();
			fos.close();
			bis.close();
			is.close();

		} catch (IOException e) {
			Log.e("ErrorMessage : ", e.getMessage());
		} 
	}
	/** Called when the activity is first created. */
	@Override
	public void onCreate(SQLiteDatabase db) {
//		String QUERY = "CREATE TABLE word (_id INTEGER PRIMARY KEY autoincrement, word_e TEXT , word_k TEXT)";
//		db.execSQL(QUERY);
		Log.e("ehsk", "eee");

		//        String QUERY1 = "INSERT INTO word (word_e, word_k ) VALUES(apple , 占쏙옙占�)";
		//        db.execSQL(QUERY1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String QUERY = "DROP TABLE IF EXISTS word";
		db.execSQL(QUERY);
		onCreate(db);


	}
}