package io.github.sealor.android.applock.test.mock;

import io.github.sealor.android.applock.taskinfo.TaskInfoResolver;

public class MockTaskInfoResolver implements TaskInfoResolver {

	private final String runningAppPackageName;

	public MockTaskInfoResolver(String runningAppPackageName) {
		this.runningAppPackageName = runningAppPackageName;
	}

	@Override
	public String resolveRunningAppPackageName() {
		return this.runningAppPackageName;
	}
}