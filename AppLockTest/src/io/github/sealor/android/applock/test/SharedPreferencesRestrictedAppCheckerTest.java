package io.github.sealor.android.applock.test;

import io.github.sealor.android.applock.appchecker.RestrictedAppChecker;
import io.github.sealor.android.applock.appchecker.SharedPreferencesRestrictedAppChecker;
import io.github.sealor.android.applock.test.tooling.AbstractMockSharedPreferences;
import android.content.SharedPreferences;
import android.test.AndroidTestCase;

public class SharedPreferencesRestrictedAppCheckerTest extends AndroidTestCase {

	private final static String TEST1_APP_PACKAGE_NAME = "io.github.sealor.android.applock.test1";
	private final static String TEST2_APP_PACKAGE_NAME = "io.github.sealor.android.applock.test2";

	public void testCheckIfTest1AppIsRestricted() {
		SharedPreferences sharedPreferences = new MockSharedPreferences();
		RestrictedAppChecker checker = new SharedPreferencesRestrictedAppChecker(sharedPreferences);

		boolean isTestAppRestricted = checker.isPackageNameRestricted(TEST1_APP_PACKAGE_NAME);

		assertEquals(true, isTestAppRestricted);
	}

	public void testCheckIfTest2AppIsNotRestricted() {
		SharedPreferences sharedPreferences = new MockSharedPreferences();
		RestrictedAppChecker checker = new SharedPreferencesRestrictedAppChecker(sharedPreferences);

		boolean isTestAppRestricted = checker.isPackageNameRestricted(TEST2_APP_PACKAGE_NAME);

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
