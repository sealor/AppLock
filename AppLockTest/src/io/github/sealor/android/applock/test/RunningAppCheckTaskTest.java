package io.github.sealor.android.applock.test;

import io.github.sealor.android.applock.AppLockBroadcastReceiver;
import io.github.sealor.android.applock.RunningAppCheckTask;
import io.github.sealor.android.applock.appchecker.RestrictedAppChecker;
import io.github.sealor.android.applock.taskinfo.TaskInfoResolver;
import junit.framework.TestCase;
import android.content.Intent;
import android.test.mock.MockContext;

public class RunningAppCheckTaskTest extends TestCase {

	private final static String APP1_PACKAGE_NAME = "io.github.sealor.android.applock.app1";
	private final static String APP2_PACKAGE_NAME = "io.github.sealor.android.applock.app2";

	public void testRestrictedAppIsRunning() {
		TaskInfoResolver taskInfoResolver = new MockTaskInfoResolver(APP1_PACKAGE_NAME);
		RestrictedAppChecker restrictedAppChecker = new MockRestrictedAppChecker(APP1_PACKAGE_NAME);
		MyMockContext context = new MyMockContext();

		RunningAppCheckTask task = new RunningAppCheckTask(taskInfoResolver, restrictedAppChecker, context);
		task.run();

		assertEquals(true, context.isBroadcastSent);
	}

	public void testRestrictedAppIsNotRunning() {
		TaskInfoResolver taskInfoResolver = new MockTaskInfoResolver(APP2_PACKAGE_NAME);
		RestrictedAppChecker restrictedAppChecker = new MockRestrictedAppChecker(APP1_PACKAGE_NAME);
		MyMockContext context = new MyMockContext();

		RunningAppCheckTask task = new RunningAppCheckTask(taskInfoResolver, restrictedAppChecker, context);
		task.run();

		assertEquals(false, context.isBroadcastSent);
	}

	private class MockTaskInfoResolver implements TaskInfoResolver {

		private final String runningAppPackageName;

		public MockTaskInfoResolver(String runningAppPackageName) {
			this.runningAppPackageName = runningAppPackageName;
		}

		@Override
		public String resolveRunningAppPackageName() {
			return this.runningAppPackageName;
		}
	}

	private class MockRestrictedAppChecker implements RestrictedAppChecker {

		private final String restrictedAppPackageName;

		public MockRestrictedAppChecker(String restrictedAppPackageName) {
			this.restrictedAppPackageName = restrictedAppPackageName;
		}

		@Override
		public boolean isPackageNameRestricted(String appPackageName) {
			return this.restrictedAppPackageName.equals(appPackageName);
		}
	}

	private class MyMockContext extends MockContext {
		private boolean isBroadcastSent = false;

		@Override
		public void sendBroadcast(Intent intent) {
			this.isBroadcastSent = intent.getAction().equals(AppLockBroadcastReceiver.RESTRICTED_APP_STARTED_BROADCAST);
		}

		@Override
		public void sendBroadcast(Intent intent, String receiverPermission) {
			this.isBroadcastSent = intent.getAction().equals(AppLockBroadcastReceiver.RESTRICTED_APP_STARTED_BROADCAST);
		}
	}
}
