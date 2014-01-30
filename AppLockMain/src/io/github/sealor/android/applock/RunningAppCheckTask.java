package io.github.sealor.android.applock;

import io.github.sealor.android.applock.appnamestorage.RestrictedAppNameStorage;
import io.github.sealor.android.applock.taskinfo.TaskInfoResolver;

import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;

public class RunningAppCheckTask extends TimerTask {

	private TaskInfoResolver taskInfoResolver;
	private RestrictedAppNameStorage restrictedAppNameStorage;
	private Context context;

	public RunningAppCheckTask(TaskInfoResolver taskInfoResolver,
			RestrictedAppNameStorage restrictedAppNameStorage, Context context) {
		super();

		this.taskInfoResolver = taskInfoResolver;
		this.restrictedAppNameStorage = restrictedAppNameStorage;
		this.context = context;
	}

	@Override
	public void run() {
		String runningAppPackageName = this.taskInfoResolver.resolveRunningAppPackageName();

		if (this.restrictedAppNameStorage.isPackageNameRestricted(runningAppPackageName)) {
			this.context.sendBroadcast(new Intent(AppLockBroadcastReceiver.RESTRICTED_APP_STARTED_BROADCAST));
		}
	}
}
