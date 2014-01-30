package io.github.sealor.android.applock.test;

import static io.github.sealor.android.applock.AppLockBroadcastReceiver.RESTRICTED_APP_STARTED_BROADCAST;
import static io.github.sealor.android.applock.test.tooling.TestUtils.resolveOwnPackageName;
import static io.github.sealor.android.applock.test.tooling.TestUtils.startTestActivity;
import io.github.sealor.android.applock.AppLockService;
import io.github.sealor.android.applock.test.tooling.broadcast.TestBroadcastReceiver;
import io.github.sealor.android.applock.test.tooling.broadcast.TestBroadcastReceiverListener;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.test.ServiceTestCase;

public class AppLockServiceTest extends ServiceTestCase<AppLockService> {

	public AppLockServiceTest() {
		super(AppLockService.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		startService(new Intent());
	}

	public void testIdentifyTestActivityAsRestrictedApp() {
		TestBroadcastReceiver receiver = new TestBroadcastReceiver();
		RestrictedAppBroadcastReceiverListener listener = new RestrictedAppBroadcastReceiverListener();
		receiver.setListener(listener);

		getService().setRestrictedPackageNames(ownPackageIsRestricted());

		getContext().registerReceiver(receiver, new IntentFilter(RESTRICTED_APP_STARTED_BROADCAST));
		startTestActivity(getContext());
		tryToSleep(AppLockService.MILLIS_CHECK_FREQUENCY * 2);
		getContext().unregisterReceiver(receiver);

		assertEquals(true, listener.isRestrictedAppStarted);
	}

	public void testIdentifyTestActivityAsNotRestrictedApp() {
		TestBroadcastReceiver receiver = new TestBroadcastReceiver();
		RestrictedAppBroadcastReceiverListener listener = new RestrictedAppBroadcastReceiverListener();
		receiver.setListener(listener);

		getContext().registerReceiver(receiver, new IntentFilter(RESTRICTED_APP_STARTED_BROADCAST));
		startTestActivity(getContext());
		tryToSleep(AppLockService.MILLIS_CHECK_FREQUENCY * 2);
		getContext().unregisterReceiver(receiver);

		assertEquals(false, listener.isRestrictedAppStarted);
	}

	private Set<String> ownPackageIsRestricted() {
		Set<String> restrictedPackageNames = new HashSet<String>();
		restrictedPackageNames.add(resolveOwnPackageName(getContext()));
		return restrictedPackageNames;
	}

	private void tryToSleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	private static class RestrictedAppBroadcastReceiverListener implements TestBroadcastReceiverListener {

		private boolean isRestrictedAppStarted = false;

		@Override
		public void onReceive(Context context, Intent intent) {
			this.isRestrictedAppStarted = true;
		}
	}
}
