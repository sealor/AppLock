package io.github.sealor.android.applock;

import io.github.sealor.android.applock.taskinfo.ActivityManagerRunningTaskInfoResolver;
import io.github.sealor.android.applock.taskinfo.RunningTaskInfoResolver;

import java.util.HashSet;
import java.util.Set;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;

public class AppLockService extends Service implements Runnable {

	public static final long MILLIS_CHECK_FREQUENCY = 100;

	private HandlerThread handlerThread;
	private Handler handler;

	private RunningTaskInfoResolver runningTaskInfoResolver;
	private Set<String> restrictedPackageNames;

	@Override
	public void onCreate() {
		super.onCreate();

		this.runningTaskInfoResolver = new ActivityManagerRunningTaskInfoResolver(this);
		this.restrictedPackageNames = new HashSet<String>();

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

	public void setRestrictedPackageNames(Set<String> restrictedPackageNames) {
		this.restrictedPackageNames = restrictedPackageNames;
	}

	@Override
	public void run() {
		String runningAppPackageName = this.runningTaskInfoResolver.resolveRunningAppPackageName();

		if (this.restrictedPackageNames.contains(runningAppPackageName)) {
			sendBroadcast(new Intent(AppLockBroadcastReceiver.RESTRICTED_APP_STARTED_BROADCAST));
		}

		this.handler.postDelayed(this, MILLIS_CHECK_FREQUENCY);
	}
}
