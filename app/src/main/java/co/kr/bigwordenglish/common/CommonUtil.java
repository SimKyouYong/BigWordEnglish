package co.kr.bigwordenglish.common;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class CommonUtil {
	private static CommonUtil _instance;


	public String Local_Path;
	public String SERVER;


	static {
		_instance = new CommonUtil();
		try {								 
			_instance.SERVER = 	   		"http://snap40.cafe24.com/BigWordEgs/";

			
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
