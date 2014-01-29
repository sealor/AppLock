package io.github.sealor.android.applock.test;

import static io.github.sealor.android.applock.test.TestUtils.resolveOwnPackageName;
import static io.github.sealor.android.applock.test.TestUtils.startTestActivity;
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

	public void testIdentifyTestActivityAsRestrictedApp() {
		Set<String> notAllowedPackageNames = new HashSet<String>();
		notAllowedPackageNames.add(resolveOwnPackageName(getContext()));
		getService().setRestrictedPackageNames(notAllowedPackageNames);

		startTestActivity(getContext());

		assertEquals(true, getService().isRunningAppRestricted());
	}

	public void testIdentifyTestActivityAsNotRestrictedApp() {
		Set<String> notAllowedPackageNames = new HashSet<String>();
		getService().setRestrictedPackageNames(notAllowedPackageNames);

		startTestActivity(getContext());

		assertEquals(false, getService().isRunningAppRestricted());
	}
}
