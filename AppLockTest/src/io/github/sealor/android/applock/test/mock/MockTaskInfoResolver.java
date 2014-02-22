package io.github.sealor.android.applock.test.mock;

import io.github.sealor.android.applock.taskinfo.TaskInfoResolver;
import android.content.ComponentName;

public class MockTaskInfoResolver implements TaskInfoResolver {

	public ComponentName runningComponentName;

	public MockTaskInfoResolver(ComponentName runningComponentName) {
		this.runningComponentName = runningComponentName;
	}

	@Override
	public ComponentName resolveRunningComponentName() {
		return this.runningComponentName;
	}
}