package io.github.sealor.android.applock;

import io.github.sealor.android.applock.appnamestorage.RestrictedAppNameStorage;
import io.github.sealor.android.applock.taskinfo.TaskInfoResolver;

import java.util.TimerTask;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class StartingAppCheckTask extends TimerTask {

	private TaskInfoResolver taskInfoResolver;
	private RestrictedAppNameStorage restrictedAppNameStorage;
	private Context context;

	private ComponentName lastRunningComponentName = null;

	public StartingAppCheckTask(TaskInfoResolver taskInfoResolver, RestrictedAppNameStorage restrictedAppNameStorage,
			Context context) {
		super();

		this.taskInfoResolver = taskInfoResolver;
		this.restrictedAppNameStorage = restrictedAppNameStorage;
		this.context = context;
	}

	@Override
	public void run() {
		ComponentName runningComponentName = this.taskInfoResolver.resolveRunningComponentName();

		if (this.lastRunningComponentName == null || !this.lastRunningComponentName.equals(runningComponentName)) {
			this.lastRunningComponentName = runningComponentName;

			if (this.restrictedAppNameStorage.isPackageNameRestricted(runningComponentName.getPackageName())) {
				this.context.sendBroadcast(new Intent(AppLockBroadcastReceiver.RESTRICTED_APP_STARTED_BROADCAST));
			}
		}
	}
}
