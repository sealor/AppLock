package io.github.sealor.android.applock.test;

import io.github.sealor.android.applock.AppLockBroadcastReceiver;
import io.github.sealor.android.applock.StartingAppCheckTask;
import io.github.sealor.android.applock.activity.PasswordActivity;
import io.github.sealor.android.applock.appnamestorage.RestrictedAppNameStorage;
import io.github.sealor.android.applock.taskinfo.TaskInfoResolver;
import io.github.sealor.android.applock.test.mock.MockRestrictedAppNameStorage;
import io.github.sealor.android.applock.test.mock.MockTaskInfoResolver;
import junit.framework.TestCase;
import android.content.ComponentName;
import android.content.Intent;
import android.test.mock.MockContext;

public class StartingAppCheckTaskTest extends TestCase {

	private final static String APP_LOCK_PACKAGE_NAME = "io.github.sealor.android.applock";
	private final static String APP1_PACKAGE_NAME = "io.github.sealor.android.applock.app1";
	private final static String APP2_PACKAGE_NAME = "io.github.sealor.android.applock.app2";

	public void testRestrictedAppIsStarted() {
		TaskInfoResolver taskInfoResolver = new MockTaskInfoResolver(createComponentName(APP1_PACKAGE_NAME));
		RestrictedAppNameStorage restrictedAppNameStorage = new MockRestrictedAppNameStorage(APP1_PACKAGE_NAME);
		MyMockContext context = new MyMockContext();

		StartingAppCheckTask task = new StartingAppCheckTask(taskInfoResolver, restrictedAppNameStorage, context);
		task.run();

		assertEquals(true, context.isBroadcastSent);
	}

	public void testRestrictedAppIsNotStarted() {
		TaskInfoResolver taskInfoResolver = new MockTaskInfoResolver(createComponentName(APP2_PACKAGE_NAME));
		RestrictedAppNameStorage restrictedAppNameStorage = new MockRestrictedAppNameStorage(APP1_PACKAGE_NAME);
		MyMockContext context = new MyMockContext();

		StartingAppCheckTask task = new StartingAppCheckTask(taskInfoResolver, restrictedAppNameStorage, context);
		task.run();

		assertEquals(false, context.isBroadcastSent);
	}

	public void testRestrictedAppIsStartedEventOccursOnlyOnce() {
		TaskInfoResolver taskInfoResolver = new MockTaskInfoResolver(createComponentName(APP1_PACKAGE_NAME));
		RestrictedAppNameStorage restrictedAppNameStorage = new MockRestrictedAppNameStorage(APP1_PACKAGE_NAME);
		MyMockContext context = new MyMockContext();

		StartingAppCheckTask task = new StartingAppCheckTask(taskInfoResolver, restrictedAppNameStorage, context);
		task.run();
		assertEquals(true, context.isBroadcastSent);

		context.isBroadcastSent = false;
		task.run();
		assertEquals(false, context.isBroadcastSent);
	}

	public void testRestrictedAppIsStartedAfterOtherApp() {
		MockTaskInfoResolver taskInfoResolver = new MockTaskInfoResolver(createComponentName(APP2_PACKAGE_NAME));
		RestrictedAppNameStorage restrictedAppNameStorage = new MockRestrictedAppNameStorage(APP1_PACKAGE_NAME);
		MyMockContext context = new MyMockContext();

		StartingAppCheckTask task = new StartingAppCheckTask(taskInfoResolver, restrictedAppNameStorage, context);
		task.run();
		assertEquals(false, context.isBroadcastSent);

		taskInfoResolver.runningComponentName = createComponentName(APP1_PACKAGE_NAME);
		task.run();
		assertEquals(true, context.isBroadcastSent);
	}

	public void testIgnoreAppLockPasswordInput() {
		ComponentName passwordComponentName = new ComponentName(APP_LOCK_PACKAGE_NAME, PasswordActivity.class.getName());
		MockTaskInfoResolver taskInfoResolver = new MockTaskInfoResolver(passwordComponentName);
		RestrictedAppNameStorage restrictedAppNameStorage = new MockRestrictedAppNameStorage(APP1_PACKAGE_NAME);
		MyMockContext context = new MyMockContext();

		StartingAppCheckTask task = new StartingAppCheckTask(taskInfoResolver, restrictedAppNameStorage, context);
		task.run();
		assertEquals(false, context.isBroadcastSent);
	}

	private ComponentName createComponentName(String packageName) {
		return new ComponentName(packageName, this.getClass().getName());
	}

	private class MyMockContext extends MockContext {
		private boolean isBroadcastSent = false;

		@Override
		public String getPackageName() {
			return APP_LOCK_PACKAGE_NAME;
		}

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
