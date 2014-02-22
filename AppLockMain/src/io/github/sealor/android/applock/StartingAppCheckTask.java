package io.github.sealor.android.applock;

import io.github.sealor.android.applock.activity.PasswordActivity;
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

		if (!isAppLockPasswordActivity(runningComponentName)) {
			if (isStartingNewComponentPackage(runningComponentName)) {
				this.lastRunningComponentName = runningComponentName;

				if (this.restrictedAppNameStorage.isPackageNameRestricted(runningComponentName.getPackageName())) {
					this.context.sendBroadcast(new Intent(AppLockBroadcastReceiver.RESTRICTED_APP_STARTED_BROADCAST));
				}
			}
		}
	}

	private boolean isAppLockPasswordActivity(ComponentName componentName) {
		return this.context.getPackageName().equals(componentName.getPackageName())
				&& componentName.getClassName().equals(PasswordActivity.class.getName());
	}

	private boolean isStartingNewComponentPackage(ComponentName componentName) {
		return this.lastRunningComponentName == null
				|| !this.lastRunningComponentName.getPackageName().equals(componentName.getPackageName());
	}
}
