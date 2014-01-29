package io.github.sealor.android.applock;

import java.util.List;
import java.util.Set;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class AppLockService extends Service {

	private Set<String> restrictedPackageNames;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public String resolveRunningAppPackage() {
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final List<RunningTaskInfo> services = activityManager.getRunningTasks(1);
		RunningTaskInfo runningApp = services.get(0);
		return runningApp.topActivity.getPackageName();
	}

	public void setRestrictedPackageNames(Set<String> restrictedPackageNames) {
		this.restrictedPackageNames = restrictedPackageNames;
	}

	public boolean isRunningAppRestricted() {
		return this.restrictedPackageNames.contains(resolveRunningAppPackage());
	}
}
