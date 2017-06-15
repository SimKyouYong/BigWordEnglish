package co.kr.bigwordenglish;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class EgsMyPreferences{
	  public static void setAppPreferences(Activity context, String key, String value, String ProdKey)
	  {
		String mProdKey = ProdKey.replaceAll("-", "");
	    SharedPreferences pref = null;
	    pref = context.getSharedPreferences(mProdKey, 0);
	    SharedPreferences.Editor prefEditor = pref.edit();
	    prefEditor.putString(key, value);  
	    prefEditor.commit();
	  }
	  public static String getAppPreferences(Context context, String key, String ProdKey)
	  {
		String mProdKey = ProdKey.replaceAll("-", "");
	    String returnValue = null;
	    SharedPreferences pref = null;
	    pref = context.getSharedPreferences(mProdKey, 0);
	    returnValue = pref.getString(key, "");
	    return returnValue;
	  }
	  
	  public static void deleteAppPreferences(Activity context, String ProdKey)
	  {
		String mProdKey = ProdKey.replaceAll("-", "");
	    SharedPreferences pref = null;
	    pref = context.getSharedPreferences(mProdKey, 0);
	    SharedPreferences.Editor prefEditor = pref.edit();
	    prefEditor.clear();
	    prefEditor.commit();
	  }
	}
