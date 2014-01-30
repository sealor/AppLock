package io.github.sealor.android.applock.test;

import io.github.sealor.android.applock.appnamestorage.RestrictedAppNameStorage;
import io.github.sealor.android.applock.appnamestorage.SharedPreferencesRestrictedAppNameStorage;
import io.github.sealor.android.applock.test.tooling.AbstractMockSharedPreferences;
import junit.framework.TestCase;
import android.content.SharedPreferences;

public class SharedPreferencesRestrictedAppNameStorageTest extends TestCase {

	private final static String TEST1_APP_PACKAGE_NAME = "io.github.sealor.android.applock.test1";
	private final static String TEST2_APP_PACKAGE_NAME = "io.github.sealor.android.applock.test2";

	public void testCheckIfTest1AppIsRestricted() {
		SharedPreferences sharedPreferences = new MockSharedPreferences();
		RestrictedAppNameStorage storage = new SharedPreferencesRestrictedAppNameStorage(sharedPreferences);

		boolean isTestAppRestricted = storage.isPackageNameRestricted(TEST1_APP_PACKAGE_NAME);

		assertEquals(true, isTestAppRestricted);
	}

	public void testCheckIfTest2AppIsNotRestricted() {
		SharedPreferences sharedPreferences = new MockSharedPreferences();
		RestrictedAppNameStorage storage = new SharedPreferencesRestrictedAppNameStorage(sharedPreferences);

		boolean isTestAppRestricted = storage.isPackageNameRestricted(TEST2_APP_PACKAGE_NAME);

		assertEquals(false, isTestAppRestricted);
	}

	private static class MockSharedPreferences extends AbstractMockSharedPreferences {

		@Override
		public boolean getBoolean(String key, boolean defValue) {
			if (TEST1_APP_PACKAGE_NAME.equals(key)) {
				return true;
			} else {
				return defValue;
			}
		}
	}
}
