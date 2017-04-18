package co.kr.bigwordenglish.util;//package kr.gaubiz.checkking.util;
//
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.media.MediaPlayer;
//import android.media.RingtoneManager;
//import android.support.v4.app.NotificationCompat;
//import android.util.Log;
//
//import kr.mangoapps.dietpic.R;
//import kr.mangoapps.dietpic.intro.IntroActivity;
//
//public class AlarmReceiver extends BroadcastReceiver {
//
//    private MediaPlayer player;
//    private String TAG = "BroadcastReceiver";
//    private String mValue;
//
//    private String mBf = "android.intent.action.BF";
//    private String mLc = "android.intent.action.LC";
//    private String mDn = "android.intent.action.DN";
//
//    private String mMealTime;
//    private String mContents;
//
//    @Override
//    public void onReceive(Context context , Intent intent) {
//
//        mValue = intent.getAction();
//        Log.i(TAG , "intent.getAction()---->" + mValue);
//
//        if (mValue.equals(mBf)){
//            mMealTime = context.getString(R.string.bf);
//            mContents = "아침 드신 걸 올려주세요.";
//        }else if (mValue.equals(mLc)){
//            mMealTime = context.getString(R.string.lc);
//            mContents = "점심 드신 걸 올려주세요.";
//        }else {
//            mMealTime = context.getString(R.string.dn);
//            mContents = "저녁 드신 걸 올려주세요.";
//        }
//
//        Log.i(TAG , "mMealTime ---> " + mMealTime);
//        showNotification(context, mMealTime, mContents);
////        setAlarm(context);
//    }
//
////    private void setAlarm(Context context) {
////        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
////        player = new MediaPlayer();
////        try {
////            player.setDataSource(context , alert);
////        } catch (IllegalArgumentException e1) {
////            e1.printStackTrace();
////        } catch (SecurityException e1) {
////            e1.printStackTrace();
////        } catch (IllegalStateException e1) {
////            e1.printStackTrace();
////        } catch (IOException e1) {
////            e1.printStackTrace();
////        }
////        final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
////        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
////            player.setAudioStreamType(AudioManager.STREAM_ALARM);
////            player.setLooping(true);
////            try {
////                player.prepare();
////            } catch (IllegalStateException e) {
////                e.printStackTrace();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////            player.start();
////        }
////    }
//
//    private void showNotification(Context context , String mealtime , String contents){
////        Intent i = new Intent(context, MainActivity.class);
////        PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//        Log.i(TAG , "mealtime ---> " + mealtime);
//        Log.i(TAG , "contents ---> " + contents);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, IntroActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
//        .setSmallIcon(R.drawable.launcher)       // 작은 아이콘 이미지.
//        .setTicker("DietPic 알람입니다.")         // 알림이 출력될 때 상단에 나오는 문구.
//        .setWhen(System.currentTimeMillis()) // 알림 출력 시간.
//        .setContentTitle(mealtime) // 알림 제목.
//        .setContentText(contents)         // 알림 내용.
//        .setContentIntent(pendingIntent) //         알림 터치시 반응.
//
//        .setAutoCancel(true)    // 알림 터치시 반응 후 알림 삭제 여부.
//        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
//
//        // 고유ID로 알림을 생성.
//        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        nm.notify(123456, builder.build());
//
////      builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)         // 알림시 사운드, 진동, 불빛을 설정 가능.
////      builder.setProgress(100, 50, false); // 프로그래스 바.
////      builder.setPriority(context.NotificationCompat.PRIORITY_MAX);         // 우선순위.
////      builder.addAction(R.mipmap.ic_launcher, "Show", pendingIntent);           // 행동 최대3개 등록 가능.
////      builder.addAction(R.mipmap.ic_launcher, "Hide", pendingIntent);
////      builder.addAction(R.mipmap.ic_launcher, "Remove", pendingIntent);
//
////      if (player.isPlaying()) {
////          player.stop();
////          player.release();
////      }
//
//
////
////        CharSequence from = "알람";
////        CharSequence message = "끝";
////
////        Notification notif = new Notification(statusBarIconID , null , System.currentTimeMillis());
////        notif.sound = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "6");
////        notif.flags = Notification.FLAG_INSISTENT;
//////        notif.setLatestEventInfo(context , from , message , i);
////        notif.ledARGB = Color.GREEN;
////        NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
////        nm.notify(1234,notif);
//    }
//}