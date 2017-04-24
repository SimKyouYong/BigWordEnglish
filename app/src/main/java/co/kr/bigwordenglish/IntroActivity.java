package co.kr.bigwordenglish;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import co.kr.bigwordenglish.common.Check_Preferences;
import co.kr.bigwordenglish.common.CommonUtil;
import co.kr.bigwordenglish.common.MySQLiteOpenHelper;
import co.kr.sky.AccumThread;


/**
 * Created by suwun on 2017-04-18.
 * 인트로 클래스
 * @since 0, 1
 */

public class IntroActivity extends AppCompatActivity {
    CommonUtil dataSet = CommonUtil.getInstance();

	ImageView introimg;
    private AccumThread mThread;
    private  MySQLiteOpenHelper vc;
    private Map<String, String> map = new HashMap<String, String>();
    private float local_Ver, Server_Ver;

	private Handler mIntroHandler = new Handler();

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro_activity);

		init();

//
//		introimg.postDelayed(new Runnable() {
//			@Override
//			public void run() {
////				if (!PermissionUtils.canAccessPhone(IntroActivity.this) ) {  // 폰 정보 권한이 없다면
////					ActivityCompat.requestPermissions(IntroActivity.this, PermissionUtils.PHONE_PERMS, CommonData.PERMISSION_REQUEST_PHONE_STATE);
////				} else {
//					mIntroHandler.postDelayed(mIntroRunnable, 1000);
////					requestAppInfo();
////				}
//			}
//		}, 1000);

	}
//	/**
//	 * 초기화
//	 */
	public void init(){

		introimg	=	(ImageView)	findViewById(R.id.intro_image);


        vc = new MySQLiteOpenHelper(this);

        map.put("url", dataSet.SERVER + "Version.php");
        // 스레드 생성
        mThread = new AccumThread(this, mAfterAccum, map, 0, 0, null);
        mThread.start(); // 스레드 시작!!
	}
    Handler mAfterAccum = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 0) {
                String res = (String) msg.obj;
                //Log.e("CHECK", "RESULT  -> " + res);

                String ver = Check_Preferences.getAppPreferences(getApplicationContext(), "version").equals("") ? "0" : Check_Preferences.getAppPreferences(getApplicationContext(), "version");
                //Log.e("SKY", "ver  -> " + ver);
                if (!ver.equals("")) {
                    local_Ver = Float.parseFloat(ver);
                    Server_Ver = Float.parseFloat(res);
                    //Log.e("SKY", "local_Ver :: " + local_Ver);
                    //Log.e("SKY", "Server_Ver :: " + Server_Ver);
                    if (local_Ver < Server_Ver) {
                        // 다운로드
                        new DownloadFileFullAsync(IntroActivity.this).execute(dataSet.SERVER + "EgDb.db");
                    } else {
                        MainMove();
                    }
                } else {
                    // 최초버전.. 무조건 다운로드
                    new DownloadFileFullAsync(IntroActivity.this).execute(dataSet.SERVER + "EgDb.db");
                }
            }
        }
    };
    private void MainMove(){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
    public class DownloadFileFullAsync extends
            AsyncTask<String, String, String> {

        private ProgressDialog mDlg;
        private Context mContext;

        public DownloadFileFullAsync(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            mDlg = new ProgressDialog(getApplicationContext(), AlertDialog.THEME_HOLO_LIGHT);
            mDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDlg.setMessage("로딩중");
            //mDlg.show();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            int count = 0;
            try {
                String str = dataSet.SERVER + "EgDb.db";
                String DEFAULT_FILE_PATH = dataSet.Local_Path + "/EgDb.db";
                Log.e("SKY", "STR :: " + str);

                URL url = new URL(str);
                URLConnection conexion = url.openConnection();
                conexion.setRequestProperty("Accept-Encoding", "identity"); // <--- Add this line

                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.e("SKY", "Lenght of file: " + lenghtOfFile);

                File file = new File(dataSet.Local_Path);
                if (!file.exists()) { // 원하는 경로에 폴더가 있는지 확인
                    Log.e("SKY", "폴더 생성");
                    file.mkdirs();
                }


                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(DEFAULT_FILE_PATH);

                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    Log.e("SKY", "total :: " + total);
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            // Log.e("SKY" , "progress size :: " + progress.length);
            // Log.e("SKY" , "progress 0 :: " + progress[0]);
            //mDlg.setProgress(Integer.parseInt(progress[0]));
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onPostExecute(String unused) {
            //mDlg.dismiss();
            Check_Preferences.setAppPreferences(IntroActivity.this, "version","" + Server_Ver);
            MainMove();
        }
    }


}
