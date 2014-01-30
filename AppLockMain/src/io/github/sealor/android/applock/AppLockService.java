package io.github.sealor.android.applock;

import io.github.sealor.android.applock.appchecker.RestrictedAppChecker;
import io.github.sealor.android.applock.appchecker.SharedPreferencesRestrictedAppChecker;
import io.github.sealor.android.applock.taskinfo.ActivityManagerTaskInfoResolver;
import io.github.sealor.android.applock.taskinfo.TaskInfoResolver;

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

		TaskInfoResolver taskInfoResolver = new ActivityManagerTaskInfoResolver(this);
		RestrictedAppChecker restrictedAppChecker = new SharedPreferencesRestrictedAppChecker(sharedPreferences);
		RunningAppCheckTask task = new RunningAppCheckTask(taskInfoResolver, restrictedAppChecker, this);

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
