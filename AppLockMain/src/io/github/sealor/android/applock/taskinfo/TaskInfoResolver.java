package io.github.sealor.android.applock.taskinfo;

import android.content.ComponentName;

public interface TaskInfoResolver {

	public ComponentName resolveRunningComponentName();
}