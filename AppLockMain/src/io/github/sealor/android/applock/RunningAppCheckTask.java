package io.github.sealor.android.applock;

import io.github.sealor.android.applock.appchecker.RestrictedAppChecker;
import io.github.sealor.android.applock.taskinfo.RunningTaskInfoResolver;

import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;

public class RunningAppCheckTask extends TimerTask {

	private RunningTaskInfoResolver runningTaskInfoResolver;
	private RestrictedAppChecker restrictedAppChecker;
	private Context context;

	public RunningAppCheckTask(RunningTaskInfoResolver runningTaskInfoResolver,
			RestrictedAppChecker restrictedAppChecker, Context context) {
		super();

		this.runningTaskInfoResolver = runningTaskInfoResolver;
		this.restrictedAppChecker = restrictedAppChecker;
		this.context = context;
	}

	@Override
	public void run() {
		String runningAppPackageName = this.runningTaskInfoResolver.resolveRunningAppPackageName();

		if (this.restrictedAppChecker.isPackageNameRestricted(runningAppPackageName)) {
			this.context.sendBroadcast(new Intent(AppLockBroadcastReceiver.RESTRICTED_APP_STARTED_BROADCAST));
		}
	}
}
