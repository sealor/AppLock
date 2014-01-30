package io.github.sealor.android.applock.taskinfo;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;

public class ActivityManagerRunningTaskInfoResolver implements RunningTaskInfoResolver {

	private final ActivityManager activityManager;

	public ActivityManagerRunningTaskInfoResolver(Context context) {
		this.activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	}

	@Override
	public String resolveRunningAppPackageName() {
		final List<RunningTaskInfo> services = activityManager.getRunningTasks(1);
		RunningTaskInfo runningApp = services.get(0);
		return runningApp.topActivity.getPackageName();
	}
}
