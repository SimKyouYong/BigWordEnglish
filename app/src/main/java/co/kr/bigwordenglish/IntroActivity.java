package co.kr.bigwordenglish;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import co.kr.bigwordenglish.util.Log;


/**
 * Created by suwun on 2017-04-18.
 * 인트로 클래스
 * @since 0, 1
 */

public class IntroActivity extends AppCompatActivity {

	ImageView introimg;

	private Handler mIntroHandler = new Handler();

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro_activity);

		init();


		introimg.postDelayed(new Runnable() {
			@Override
			public void run() {
//				if (!PermissionUtils.canAccessPhone(IntroActivity.this) ) {  // 폰 정보 권한이 없다면
//					ActivityCompat.requestPermissions(IntroActivity.this, PermissionUtils.PHONE_PERMS, CommonData.PERMISSION_REQUEST_PHONE_STATE);
//				} else {
					mIntroHandler.postDelayed(mIntroRunnable, 1000);
//					requestAppInfo();
//				}
			}
		}, 1000);

	}

//	/**
//	 * 앱 최초 실행시 바로가기 아이콘 추가
//	 */
//	private void addShortcut() {
//		Log.i("addShortcut()");
//		//Adding shortcut for MainActivity
//		//on Home screen
//		Intent shortcutIntent = new Intent(getApplicationContext(), IntroActivity.class);
//		shortcutIntent.setAction(Intent.ACTION_MAIN);
//
//		Intent addIntent = new Intent();
//		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
//		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
//		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
//				Intent.ShortcutIconResource.fromContext(getApplicationContext(),
//						R.mipmap.launcher));
//		addIntent.putExtra("duplicate", false);
//		addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
//		getApplicationContext().sendBroadcast(addIntent);
//
//		CommonData.getInstance().setShortCut(true);
//	}

//	/**
//	 * 초기화
//	 */
	public void init(){

		introimg	=	(ImageView)	findViewById(R.id.intro_image);


	}

	/**
	 * 프롤로그 화면이동 런어블
	 */
	private Runnable mIntroRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.i("run");
			Intent intent = new Intent(IntroActivity.this, MainActivity.class);
			startActivity(intent);
			IntroActivity.this.finish();
		}
	};



	// 퍼미션 권한 획득 리퀘스트
//	@Override
//	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//
//		switch(requestCode) {
//			case CommonData.PERMISSION_REQUEST_PHONE_STATE:
//				if (PermissionUtils.canAccessPhone(this)) {	// 폰 권한을 획득했다면
//					mIntroHandler.postDelayed(mIntroRunnable, CommonData.INTRO_POST_DELAYED);
//				}
//				else {
//					Log.i("권한 획득 거부 or 취소");
//					mDialog = new CustomAlertDialog(this, CustomAlertDialog.TYPE_A);
//					mDialog.setTitle(getString(R.string.popup_dialog_a_type_title));
//					mDialog.setContent(getString(R.string.popup_dialog_permission_content));
//					mDialog.setPositiveButton(getString(R.string.popup_dialog_button_confirm), new CustomAlertDialogInterface.OnClickListener() {
//
//						@Override
//						public void onClick(CustomAlertDialog dialog, Button button) {
//							mDialog.dismiss();
//							finish();
//						}
//					});
//					mDialog.show();
//				}
//				break;
//			default:
//				Log.i("onRequestPermissionsResult default");
//				super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//				break;
//		}
//	}

}
