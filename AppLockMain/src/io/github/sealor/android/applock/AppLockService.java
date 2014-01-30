package io.github.sealor.android.applock;

import io.github.sealor.android.applock.appchecker.RestrictedAppChecker;
import io.github.sealor.android.applock.appchecker.SharedPreferencesRestrictedAppChecker;
import io.github.sealor.android.applock.taskinfo.ActivityManagerRunningTaskInfoResolver;
import io.github.sealor.android.applock.taskinfo.RunningTaskInfoResolver;

import java.util.Timer;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;

public class AppLockService extends Service {

	public static final long MILLIS_CHECK_FREQUENCY = 100;

	private Timer timer;

	@Override
	public void onCreate() {
		super.onCreate();

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		RunningTaskInfoResolver runningTaskInfoResolver = new ActivityManagerRunningTaskInfoResolver(this);
		RestrictedAppChecker restrictedAppChecker = new SharedPreferencesRestrictedAppChecker(sharedPreferences);
		RunningAppCheckTask task = new RunningAppCheckTask(runningTaskInfoResolver, restrictedAppChecker, this);

		this.timer = new Timer(true);
		this.timer.schedule(task, 0, MILLIS_CHECK_FREQUENCY);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		if (this.timer != null) {
			this.timer.cancel();
		}

		super.onDestroy();
	}
}
