package io.github.sealor.android.applock;

import io.github.sealor.android.applock.appnamestorage.RestrictedAppNameStorage;
import io.github.sealor.android.applock.appnamestorage.SharedPreferencesRestrictedAppNameStorage;
import io.github.sealor.android.applock.taskinfo.ActivityManagerTaskInfoResolver;
import io.github.sealor.android.applock.taskinfo.TaskInfoResolver;

import java.util.Timer;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
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

		TaskInfoResolver taskInfoResolver = new ActivityManagerTaskInfoResolver(resolveActivityManager());
		RestrictedAppNameStorage appStorage = new SharedPreferencesRestrictedAppNameStorage(resolveSharedPreferences());
		RunningAppCheckTask task = new RunningAppCheckTask(taskInfoResolver, appStorage, this);

		this.timer = new Timer(true);
		this.timer.schedule(task, 0, MILLIS_CHECK_FREQUENCY);
	}

	private ActivityManager resolveActivityManager() {
		return (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	}

	private SharedPreferences resolveSharedPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(this);
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
