package io.github.sealor.android.applock.test;

import static io.github.sealor.android.applock.AppLockBroadcastReceiver.RESTRICTED_APP_STARTED_BROADCAST;
import static io.github.sealor.android.applock.test.tooling.TestUtils.resolveOwnPackageName;
import static io.github.sealor.android.applock.test.tooling.TestUtils.startTestActivity;
import io.github.sealor.android.applock.AppLockService;

import java.util.HashSet;
import java.util.Set;

import android.content.BroadcastReceiver;
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

	public void testIdentifyTestActivityAsRestrictedApp() throws InterruptedException {
		TestBroadcastReceiver receiver = new TestBroadcastReceiver();

		getService().setRestrictedPackageNames(ownPackageIsRestricted());

		getContext().registerReceiver(receiver, new IntentFilter(RESTRICTED_APP_STARTED_BROADCAST));
		synchronized (receiver) {
			startTestActivity(getContext());
			receiver.wait(AppLockService.MILLIS_CHECK_FREQUENCY * 3);
		}
		getContext().unregisterReceiver(receiver);

		assertEquals(true, receiver.isRestrictedAppStarted);
	}

	public void testIdentifyTestActivityAsNotRestrictedApp() throws InterruptedException {
		TestBroadcastReceiver receiver = new TestBroadcastReceiver();

		getContext().registerReceiver(receiver, new IntentFilter(RESTRICTED_APP_STARTED_BROADCAST));
		synchronized (receiver) {
			startTestActivity(getContext());
			receiver.wait(AppLockService.MILLIS_CHECK_FREQUENCY * 3);
		}
		getContext().unregisterReceiver(receiver);

		assertEquals(false, receiver.isRestrictedAppStarted);
	}

	private Set<String> ownPackageIsRestricted() {
		Set<String> restrictedPackageNames = new HashSet<String>();
		restrictedPackageNames.add(resolveOwnPackageName(getContext()));
		return restrictedPackageNames;
	}

	public class TestBroadcastReceiver extends BroadcastReceiver {

		private boolean isRestrictedAppStarted = false;

		@Override
		public void onReceive(Context context, Intent intent) {
			synchronized (this) {
				this.isRestrictedAppStarted = true;
				notifyAll();
			}
		}
	}
}
