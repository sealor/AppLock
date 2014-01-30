package io.github.sealor.android.applock;

import io.github.sealor.android.applock.appchecker.RestrictedAppChecker;
import io.github.sealor.android.applock.appchecker.SharedPreferencesRestrictedAppChecker;
import io.github.sealor.android.applock.taskinfo.ActivityManagerRunningTaskInfoResolver;
import io.github.sealor.android.applock.taskinfo.RunningTaskInfoResolver;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.preference.PreferenceManager;

public class AppLockService extends Service implements Runnable {

	public static final long MILLIS_CHECK_FREQUENCY = 100;

	private HandlerThread handlerThread;
	private Handler handler;

	private RunningTaskInfoResolver runningTaskInfoResolver;
	private RestrictedAppChecker restrictedAppChecker;

	@Override
	public void onCreate() {
		super.onCreate();

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		this.runningTaskInfoResolver = new ActivityManagerRunningTaskInfoResolver(this);
		this.restrictedAppChecker = new SharedPreferencesRestrictedAppChecker(sharedPreferences);

		this.handlerThread = new HandlerThread(this.getClass().getSimpleName());
		this.handlerThread.start();
		this.handler = new Handler(this.handlerThread.getLooper());

		this.handler.postDelayed(this, MILLIS_CHECK_FREQUENCY);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		if (this.handlerThread != null) {
			this.handlerThread.quit();
		}

		super.onDestroy();
	}

	@Override
	public void run() {
		String runningAppPackageName = this.runningTaskInfoResolver.resolveRunningAppPackageName();

		if (this.restrictedAppChecker.isPackageNameRestricted(runningAppPackageName)) {
			sendBroadcast(new Intent(AppLockBroadcastReceiver.RESTRICTED_APP_STARTED_BROADCAST));
		}

		this.handler.postDelayed(this, MILLIS_CHECK_FREQUENCY);
	}
}
