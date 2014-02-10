package io.github.sealor.android.applock;

import io.github.sealor.android.applock.appinfo.AppInfoResolver;
import io.github.sealor.android.applock.appinfo.PackageManagerAppInfoResolver;
import io.github.sealor.android.applock.appnamestorage.RestrictedAppNameStorage;
import io.github.sealor.android.applock.appnamestorage.SharedPreferencesRestrictedAppNameStorage;
import io.github.sealor.android.applock.taskinfo.ActivityManagerTaskInfoResolver;
import io.github.sealor.android.applock.taskinfo.TaskInfoResolver;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

public class ControllerFactory {

	public static ControllerFactory INSTANCE = new ControllerFactory();

	protected ControllerFactory() {
		super();
	}

	public TaskInfoResolver createTaskInfoResolver(ActivityManager activityManager) {
		return new ActivityManagerTaskInfoResolver(activityManager);
	}

	public RestrictedAppNameStorage createRestrictedAppNameStorage(SharedPreferences sharedPreferences) {
		return new SharedPreferencesRestrictedAppNameStorage(sharedPreferences);
	}

	public RunningAppCheckTask createRunningAppCheckTask(TaskInfoResolver taskInfoResolver,
			RestrictedAppNameStorage restrictedAppNameStorage, Context context) {
		return new RunningAppCheckTask(taskInfoResolver, restrictedAppNameStorage, context);
	}

	public AppInfoResolver createAppInfoResolver(PackageManager packageManager) {
		return new PackageManagerAppInfoResolver(packageManager);
	}
}
