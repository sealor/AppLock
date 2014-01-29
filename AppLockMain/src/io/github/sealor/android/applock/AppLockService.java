package io.github.sealor.android.applock;

import java.util.Set;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AppLockService extends Service {

	private RunningTaskInfoResolver runningTaskInfoResolver;

	private Set<String> restrictedPackageNames;

	@Override
	public void onCreate() {
		super.onCreate();

		this.runningTaskInfoResolver = new RunningTaskInfoResolver(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public void setRestrictedPackageNames(Set<String> restrictedPackageNames) {
		this.restrictedPackageNames = restrictedPackageNames;
	}

	public boolean isRunningAppRestricted() {
		return this.restrictedPackageNames.contains(this.runningTaskInfoResolver.resolveRunningAppPackageName());
	}
}
