package io.github.sealor.android.applock.test;

import io.github.sealor.android.applock.AppLockService;

import java.util.HashSet;
import java.util.Set;

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

	public void testIdentifyTestActivityAsRestrictedApp() {
		Set<String> notAllowedPackageNames = new HashSet<String>();
		notAllowedPackageNames.add(getService().getApplicationInfo().packageName);
		getService().setRestrictedPackageNames(notAllowedPackageNames);

		Intent intent = new Intent(getContext(), TestActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getContext().startActivity(intent);

		assertEquals(true, getService().isRunningAppRestricted());
	}

	public void testIdentifyTestActivityAsNotRestrictedApp() {
		Set<String> notAllowedPackageNames = new HashSet<String>();
		getService().setRestrictedPackageNames(notAllowedPackageNames);

		Intent intent = new Intent(getContext(), TestActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getContext().startActivity(intent);

		assertEquals(false, getService().isRunningAppRestricted());
	}
}
