package co.kr.bigwordenglish;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class LockScreenActivity extends Activity {
	@Override
	public void onResume() {
		Log.e("SKY" , "-- onResume --");
		super.onResume();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lockscreen);

	}
}
