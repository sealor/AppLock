package io.github.sealor.android.applock.test;

import io.github.sealor.android.applock.AppLockBroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

public class AppLockBroadcastReceiverTest extends AndroidTestCase {

	public void testOpenPasswordActivityOnRestrictedAppStartedBroadcast() throws InterruptedException {
		MyMockContext myMockContext = new MyMockContext();

		AppLockBroadcastReceiver receiver = new AppLockBroadcastReceiver();
		receiver.onReceive(myMockContext, new Intent(AppLockBroadcastReceiver.RESTRICTED_APP_STARTED_BROADCAST));

		assertTrue(myMockContext.isActivityStarted);
	}

	public void testStartAppLockServiceOnBootCompletedBroadcast() throws InterruptedException {
		MyMockContext myMockContext = new MyMockContext();

		AppLockBroadcastReceiver receiver = new AppLockBroadcastReceiver();
		receiver.onReceive(myMockContext, new Intent(Intent.ACTION_BOOT_COMPLETED));

		assertTrue(myMockContext.isServiceStarted);
	}

	public void testDoNothingOnAnyBroadcast() throws InterruptedException {
		MyMockContext myMockContext = new MyMockContext();

		AppLockBroadcastReceiver receiver = new AppLockBroadcastReceiver();
		receiver.onReceive(myMockContext, new Intent("testDoNothingOnAnyBroadcast"));

		assertFalse(myMockContext.isActivityStarted);
	}

	private class MyMockContext extends MockContext {

		private boolean isActivityStarted = false;
		private boolean isServiceStarted = false;

		@Override
		public String getPackageName() {
			return "packageName";
		}

		@Override
		public void startActivity(Intent intent) {
			this.isActivityStarted = true;
		}

		@Override
		public void startActivity(Intent intent, Bundle options) {
			this.isActivityStarted = true;
		}

		@Override
		public ComponentName startService(Intent intent) {
			this.isServiceStarted = true;
			return null;
		}
	}
}
