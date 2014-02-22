package io.github.sealor.android.applock.taskinfo;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;

public class ActivityManagerTaskInfoResolver implements TaskInfoResolver {

	private final ActivityManager activityManager;

	public ActivityManagerTaskInfoResolver(ActivityManager activityManager) {
		this.activityManager = activityManager;
	}

	@Override
	public ComponentName resolveRunningComponentName() {
		final List<RunningTaskInfo> services = activityManager.getRunningTasks(1);
		return services.get(0).topActivity;
	}
}
