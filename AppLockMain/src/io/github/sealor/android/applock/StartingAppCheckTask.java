package io.github.sealor.android.applock;

import io.github.sealor.android.applock.appnamestorage.RestrictedAppNameStorage;
import io.github.sealor.android.applock.taskinfo.TaskInfoResolver;

import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;

public class StartingAppCheckTask extends TimerTask {

	private TaskInfoResolver taskInfoResolver;
	private RestrictedAppNameStorage restrictedAppNameStorage;
	private Context context;

	private String lastRunningAppPackageName = null;

	public StartingAppCheckTask(TaskInfoResolver taskInfoResolver, RestrictedAppNameStorage restrictedAppNameStorage,
			Context context) {
		super();

		this.taskInfoResolver = taskInfoResolver;
		this.restrictedAppNameStorage = restrictedAppNameStorage;
		this.context = context;
	}

	@Override
	public void run() {
		String runningAppPackageName = this.taskInfoResolver.resolveRunningAppPackageName();

		if (this.lastRunningAppPackageName == null || !this.lastRunningAppPackageName.equals(runningAppPackageName)) {
			this.lastRunningAppPackageName = runningAppPackageName;

			if (this.restrictedAppNameStorage.isPackageNameRestricted(runningAppPackageName)) {
				this.context.sendBroadcast(new Intent(AppLockBroadcastReceiver.RESTRICTED_APP_STARTED_BROADCAST));
			}
		}
	}
}
