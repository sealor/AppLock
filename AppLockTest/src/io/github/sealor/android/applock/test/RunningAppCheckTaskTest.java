package io.github.sealor.android.applock.test;

import io.github.sealor.android.applock.AppLockBroadcastReceiver;
import io.github.sealor.android.applock.RunningAppCheckTask;
import io.github.sealor.android.applock.appnamestorage.RestrictedAppNameStorage;
import io.github.sealor.android.applock.taskinfo.TaskInfoResolver;
import io.github.sealor.android.applock.test.mock.MockRestrictedAppNameStorage;
import io.github.sealor.android.applock.test.mock.MockTaskInfoResolver;
import junit.framework.TestCase;
import android.content.Intent;
import android.test.mock.MockContext;

public class RunningAppCheckTaskTest extends TestCase {

	private final static String APP1_PACKAGE_NAME = "io.github.sealor.android.applock.app1";
	private final static String APP2_PACKAGE_NAME = "io.github.sealor.android.applock.app2";

	public void testRestrictedAppIsRunning() {
		TaskInfoResolver taskInfoResolver = new MockTaskInfoResolver(APP1_PACKAGE_NAME);
		RestrictedAppNameStorage restrictedAppNameStorage = new MockRestrictedAppNameStorage(APP1_PACKAGE_NAME);
		MyMockContext context = new MyMockContext();

		RunningAppCheckTask task = new RunningAppCheckTask(taskInfoResolver, restrictedAppNameStorage, context);
		task.run();

		assertEquals(true, context.isBroadcastSent);
	}

	public void testRestrictedAppIsNotRunning() {
		TaskInfoResolver taskInfoResolver = new MockTaskInfoResolver(APP2_PACKAGE_NAME);
		RestrictedAppNameStorage restrictedAppNameStorage = new MockRestrictedAppNameStorage(APP1_PACKAGE_NAME);
		MyMockContext context = new MyMockContext();

		RunningAppCheckTask task = new RunningAppCheckTask(taskInfoResolver, restrictedAppNameStorage, context);
		task.run();

		assertEquals(false, context.isBroadcastSent);
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
