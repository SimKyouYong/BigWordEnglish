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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.kr.bigwordenglish.common.Check_Preferences;
import co.kr.bigwordenglish.common.CommonUtil;
import co.kr.bigwordenglish.common.DBManager;
import co.kr.bigwordenglish.common.MySQLiteOpenHelper;
import co.kr.sky.AccumThread;


/**
 * Created by suwun on 2017-04-18.
 * 인트로 클래스 test12
 * @since 0, 1
 */

public class IntroActivity extends AppCompatActivity {
    CommonUtil dataSet = CommonUtil.getInstance();
    ImageView introimg;
    private AccumThread mThread;
    private Map<String, String> map = new HashMap<String, String>();
    private String local_Ver, Server_Ver;


    public ArrayList<String> checkFav = new ArrayList<String>();
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);
        init();
    }

    public void init(){
        introimg	=	(ImageView)	findViewById(R.id.intro_image);

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
                String ver = Check_Preferences.getAppPreferences(getApplicationContext(), "version");

                if (ver != null && "".equals(ver) == false && "null".equals(ver) == false) {
                    local_Ver = ver;
                    Server_Ver = res;
                    Log.i("ifeelbluu", "local_Ver :: " + ver);
                    Log.i("ifeelbluu", "Server_Ver :: " + res);
                    if (ver.equals(res) == false) {
                        Log.i("ifeelbluu", "다운로드");
                        // 다운로드
                        DBManager dbm = new DBManager(IntroActivity.this);
                        checkFav = dbm.getFavorite();
                        new DownloadFileFullAsync(IntroActivity.this).execute(dataSet.SERVER + "admin/db/egDb.db");
                        return;
                    } else {
                        Log.i("ifeelbluu", "패스");
                        MainMove();
                        return;
                    }
                } else {
                    Log.e("ifeelbluu", "local_Ver :: null");
                    // 최초버전.. 무조건 다운로드
                    new DownloadFileFullAsync(IntroActivity.this).execute(dataSet.SERVER + "admin/db/egDb.db");
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
        }, 500);
    }
    public class DownloadFileFullAsync extends  AsyncTask<String, String, String> {

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
                String str = dataSet.SERVER + "admin/db/egDb.db";
                String DEFAULT_FILE_PATH = IntroActivity.this.getDatabasePath("egDb.db")+"";
                Log.e("SKY", "STR :: " + str);

                URL url = new URL(str);
                URLConnection conexion = url.openConnection();
                conexion.setRequestProperty("Accept-Encoding", "identity"); // <--- Add this line

                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.e("SKY", "Lenght of file: " + lenghtOfFile);

                File file = new File(DEFAULT_FILE_PATH.substring(0,DEFAULT_FILE_PATH.lastIndexOf("/")));
                if (!file.exists()) { // 원하는 경로에 폴더가 있는지 확인
                    Log.e("SKY", "폴더 생성");
                    file.mkdirs();
                }


                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(DEFAULT_FILE_PATH);

                byte data[] = new byte[2048];
                long total = 0;
                while ((count = input.read(data)) != -1) {
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

            if(checkFav != null && checkFav.size() > 0){
                DBManager dbm = new DBManager(IntroActivity.this);
                for(int i=0; i<checkFav.size(); i++){
                    dbm.updateFavorite("f",checkFav.get(i).toString());
                }
            }
            MainMove();
        }
    }
}
