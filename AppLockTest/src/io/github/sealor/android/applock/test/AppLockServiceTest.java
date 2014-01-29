package io.github.sealor.android.applock.test;

import io.github.sealor.android.applock.AppLockService;
import android.content.Intent;
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

	public void testResolveRunningAppPackage() {
		Intent intent = new Intent(getContext(), TestActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getContext().startActivity(intent);

		String runningAppPackage = getService().resolveRunningAppPackage();

		assertEquals(getService().getApplicationInfo().packageName, runningAppPackage);
	}
}
