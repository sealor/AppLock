package io.github.sealor.android.applock.test;

import io.github.sealor.android.applock.AppLockBroadcastReceiver;
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

	public void testDoNothingOnAnyBroadcast() throws InterruptedException {
		MyMockContext myMockContext = new MyMockContext();

		AppLockBroadcastReceiver receiver = new AppLockBroadcastReceiver();
		receiver.onReceive(myMockContext, new Intent("testDoNothingOnAnyBroadcast"));

		assertFalse(myMockContext.isActivityStarted);
	}

	private class MyMockContext extends MockContext {

		private boolean isActivityStarted = false;

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
	}
}
