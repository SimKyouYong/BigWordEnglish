package co.kr.bigwordenglish;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;

import co.kr.bigwordenglish.common.Check_Preferences;
import co.kr.bigwordenglish.common.CommonUtil;
import co.kr.sky.AccumThread;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    CommonUtil dataSet = CommonUtil.getInstance();
    AccumThread mThread;
    Map<String, String> map = new HashMap<String, String>();

    private static final String TAG = "MyFirebaseIIDService";

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("SKY", "Refreshed token: " + token);

        Check_Preferences.setAppPreferences(getApplicationContext() , "REG_ID" , token);
        //dataSet.REG_ID = token;
        Log.e("SKY", "token  -> " + token);
        map.clear();
        map.put("url", dataSet.SERVER + "android_push_register.php");
        map.put("reg_id", token);
        map.put("type", "android");
        // 스레드 생성
        mThread = new AccumThread(getApplicationContext(), mAfterAccum, map, 0, 0, null);
        mThread.start(); // 스레드 시작!!

    }
    Handler mAfterAccum = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 0) {
                String res = (String) msg.obj;
                Log.e("SKY", "RESULT  -> " + res);

            }
        }
    };

}
