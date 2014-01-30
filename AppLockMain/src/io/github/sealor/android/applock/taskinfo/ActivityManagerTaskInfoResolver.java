package io.github.sealor.android.applock.taskinfo;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;

public class ActivityManagerTaskInfoResolver implements TaskInfoResolver {

	private final ActivityManager activityManager;

	public ActivityManagerTaskInfoResolver(Context context) {
		this.activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	}

	@Override
	public String resolveRunningAppPackageName() {
		final List<RunningTaskInfo> services = activityManager.getRunningTasks(1);
		RunningTaskInfo runningApp = services.get(0);
		return runningApp.topActivity.getPackageName();
	}
}
