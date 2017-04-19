package co.kr.bigwordenglish.common;

import android.app.Activity;

import java.util.ArrayList;
import java.util.StringTokenizer;



/*
 * 怨듯넻�븿�닔 諛� �쟾泥댄겢�옒�뒪�뿉�꽌 蹂��닔 �젒洹쇳븯湲� �쐞�븳 怨듯넻 蹂��닔�뱾 �뀑�똿 �겢�옒�뒪
 * */
public class CommonUtil {
	private static CommonUtil _instance;
	public int EA;
	public int HomeBtn;
	
	public ArrayList<Activity> av = new ArrayList<Activity>();					//Activity 를 담는다.

	public String Local_Path;
	public String PHONE;
	public String SERVER;
	public String SERVER_IMG;
	public String SERVER_ADIMG;
	public String SERVER_E_IMG;
	public String SERVER_F_IMG;
	public String SERVER_FITIMG;
	public String SERVER_MP3;
	public String SERVER_DB;
	public String SERVER_MY_IMG;
	public String COSS_FOOD;
	
	
	public String LOGIN_MEMBER_GEUST;
	
	public ArrayList<Boolean> mp3_flag;
	public ArrayList<Boolean> mp3_detail_flag;
	
	public boolean Activity_flag = false;
	public boolean Agree_Check;
	public boolean EXIT;
	public String REG_ID;
	public String PROJECT_ID;
	public String CHOO;
	public String MY_EA;
	public String POINT;
	public String MY_IMG;

	
	public String KEY_INDEX;
	public String NAME;
	public String USER_ID;
	public String USER_PW;
	public String USER_EMAIL;
	public String USER_PHONE;
	public String USER_BIRTH;

	static {
		_instance = new CommonUtil();
		try {								 
			_instance.PROJECT_ID = 	   		"1072720857084";
			_instance.REG_ID = 	   		"";
			_instance.LOGIN_MEMBER_GEUST = 	   		"";
			_instance.MY_IMG = 	   		"";
			_instance.Local_Path = 	   		"/data/data/co.kr.app.helloweurope/databases"+ "/Hellow/";
			//_instance.Local_Path = 	   		Environment.getExternalStorageDirectory().getAbsolutePath()+ "/Hellow/";
			
			_instance.mp3_flag = 	   		new ArrayList<Boolean>();
			_instance.mp3_detail_flag = 	   		new ArrayList<Boolean>();
			_instance.KEY_INDEX = 	   		"";
			_instance.NAME = 	   			"";
			_instance.USER_ID = 	   		"";
			_instance.USER_PW = 	   		"";
			_instance.USER_EMAIL = 	   		"";
			_instance.USER_PHONE = 	   		"";
			_instance.USER_BIRTH = 	   		"";
			_instance.POINT = 	   		"";

			_instance.EXIT = 	   		false;
			_instance.PHONE = 	   		"";
			_instance.SERVER = 	   		"http://coaineu.cafe24.com/Hellow/php/";
			_instance.SERVER_IMG = 	   		"http://coaineu.cafe24.com/Hellow/hellowtalk_img/";
			_instance.SERVER_E_IMG = 	   		"http://coaineu.cafe24.com/Hellow/EuropePath_IMG/";
			_instance.SERVER_F_IMG = 	   		"http://coaineu.cafe24.com/Hellow/Food_IMG/";
			_instance.SERVER_FITIMG = 	   		"http://coaineu.cafe24.com/Hellow/fitimg/";
			_instance.SERVER_MP3 = 	   		"http://coaineu.cafe24.com/Hellow/mp3/";
			_instance.SERVER_DB = 	   		"http://coaineu.cafe24.com/Hellow/DB/hellow_db.db";
			_instance.SERVER_MY_IMG = 	   		"http://coaineu.cafe24.com/Hellow/MY_IMG/";
			_instance.COSS_FOOD = 	   		"http://coaineu.cafe24.com/Hellow/Coss_Food/";
			_instance.SERVER_ADIMG = 	   		"http://coaineu.cafe24.com/Hellow/AD_IMG/";

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static CommonUtil getInstance() {
		return _instance;
	}

	
	public ArrayList<String> Token_String(String url , String token){
		ArrayList<String> Obj = new ArrayList<String>();

		StringTokenizer st1 = new StringTokenizer( url , token);
		while(st1.hasMoreTokens()){
			Obj.add(st1.nextToken());
		}
		return Obj;
	}
}
