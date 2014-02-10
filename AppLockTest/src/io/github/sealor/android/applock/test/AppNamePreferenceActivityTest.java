package io.github.sealor.android.applock.test;

import io.github.sealor.android.applock.ControllerFactory;
import io.github.sealor.android.applock.activity.AppNamePreferenceActivity;
import io.github.sealor.android.applock.appinfo.AppInfo;
import io.github.sealor.android.applock.appinfo.AppInfoResolver;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.preference.Preference;
import android.test.ActivityUnitTestCase;

public class AppNamePreferenceActivityTest extends ActivityUnitTestCase<AppNamePreferenceActivity> {

	private final static String TEST_PACKAGE_NAME = "io.github.sealor.android.applock.test.PACKAGE";
	private final static CharSequence TEST_LABEL = "TEST APP LABEL";

	public AppNamePreferenceActivityTest() {
		super(AppNamePreferenceActivity.class);
	}

	@SuppressWarnings("deprecation")
	public void testIfTestAppIsAvailable() {
		ControllerFactory.INSTANCE = new MockControllerFactory();
		AppNamePreferenceActivity activity = startActivity();

		Preference preference = activity.findPreference(TEST_PACKAGE_NAME);

		assertNotNull(preference);
		assertEquals(preference.getTitle(), TEST_LABEL);

		activity.finish();
	}

	private AppNamePreferenceActivity startActivity() {
		startActivity(new Intent(), null, null);
		return getActivity();
	}

	private class MockControllerFactory extends ControllerFactory {
		@Override
		public AppInfoResolver createAppInfoResolver(PackageManager packageManager) {
			return new MockAppInfoResolver();
		}
	}

	private class MockAppInfoResolver implements AppInfoResolver {

		@Override
		public List<AppInfo> resolveAllInstalledAppInfos() {
			AppInfo appInfo = new AppInfo();
			appInfo.setPackageName(TEST_PACKAGE_NAME);
			appInfo.setLabel(TEST_LABEL);
			return Arrays.asList(appInfo);
		}
	}
}
