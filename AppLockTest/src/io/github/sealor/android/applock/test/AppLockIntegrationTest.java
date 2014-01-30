package io.github.sealor.android.applock.test;

import static io.github.sealor.android.applock.AppLockBroadcastReceiver.RESTRICTED_APP_STARTED_BROADCAST;
import static io.github.sealor.android.applock.test.tooling.TestUtils.resolveOwnPackageName;
import static io.github.sealor.android.applock.test.tooling.TestUtils.startTestActivity;
import io.github.sealor.android.applock.AppLockService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ServiceTestCase;

public class AppLockIntegrationTest extends ServiceTestCase<AppLockService> {

	public AppLockIntegrationTest() {
		super(AppLockService.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		startService(new Intent());
	}

	public void testIdentifyTestActivityAsRestrictedApp() throws InterruptedException {
		TestBroadcastReceiver receiver = new TestBroadcastReceiver();

		setOwnPackageRestriction(true);
		startTestActivityAndWaitForBroadcast(receiver);

		assertEquals(true, receiver.isRestrictedAppStarted);
	}

	public void testIdentifyTestActivityAsNotRestrictedApp() throws InterruptedException {
		TestBroadcastReceiver receiver = new TestBroadcastReceiver();

		setOwnPackageRestriction(false);
		startTestActivityAndWaitForBroadcast(receiver);

		assertEquals(false, receiver.isRestrictedAppStarted);
	}

	public void testIdentifyTestActivityAsUnknownNotRestrictedApp() throws InterruptedException {
		TestBroadcastReceiver receiver = new TestBroadcastReceiver();

		removeOwnPackageRestriction();
		startTestActivityAndWaitForBroadcast(receiver);

		assertEquals(false, receiver.isRestrictedAppStarted);
	}

	private void setOwnPackageRestriction(boolean isRestricted) {
		String ownPackageName = resolveOwnPackageName(getContext());
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
		sharedPreferences.edit().putBoolean(ownPackageName, isRestricted).commit();
	}

	private void removeOwnPackageRestriction() {
		String ownPackageName = resolveOwnPackageName(getContext());
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
		sharedPreferences.edit().remove(ownPackageName).commit();
	}

	private void startTestActivityAndWaitForBroadcast(TestBroadcastReceiver receiver) throws InterruptedException {
		register(receiver);
		synchronized (receiver) {
			startTestActivity(getContext());
			receiver.wait(AppLockService.MILLIS_CHECK_FREQUENCY * 3);
		}
		unregister(receiver);
	}

	private void register(BroadcastReceiver receiver) {
		getContext().registerReceiver(receiver, new IntentFilter(RESTRICTED_APP_STARTED_BROADCAST));
	}

	private void unregister(BroadcastReceiver receiver) {
		getContext().unregisterReceiver(receiver);
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
