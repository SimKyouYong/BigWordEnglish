package co.kr.bigwordenglish.common;

import android.content.Context;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class CommonUtil {
	private static CommonUtil _instance;
	//public static boolean isHome = false;
	//public static boolean isLock = false;

	public static boolean isMainActivity = false;

    public static String slevel1 = "";
    public static String slevel2 = "";
    public static String slevel3 = "";
	public static String scount = "";
	public static String getLevel_03_Q = "";
	public String Local_Path;
    public String SERVER;
    public String SERVERDB;

	public static Typeface font = null;
	public static void setFont(Context context) {

		if(font != null){
			return;
		}
		font = Typeface.createFromAsset(context.getAssets(), "coolvetica.ttf");
	}

	public static Typeface lsfont = null;
	public static void setLSFont(Context context) {

		if(lsfont != null){
			return;
		}
		lsfont = Typeface.createFromAsset(context.getAssets(), "coolvetica.ttf");
	}

	static {
		_instance = new CommonUtil();
		try {
            _instance.SERVER = 	   		"http://snap40.cafe24.com/BigWordEgs/";
            //_instance.SERVERDB = 	   		"http://shqrp5200.cafe24.com/egDb.db";
            _instance.SERVERDB = 	   		"http://ec2-13-113-158-34.ap-northeast-1.compute.amazonaws.com/BigWordEgs/admin/db/egDb.db";
			_instance.Local_Path = 	   	"/data/data/co.kr.bigwordenglish/databases";

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
