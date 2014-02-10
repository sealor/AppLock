package io.github.sealor.android.applock.test;

import static io.github.sealor.android.applock.test.tooling.TestUtils.resolveOwnPackageName;
import io.github.sealor.android.applock.activity.AppNamePreferenceActivity;
import android.content.Intent;
import android.preference.Preference;
import android.test.ActivityUnitTestCase;

public class AppNamePreferenceActivityTest extends ActivityUnitTestCase<AppNamePreferenceActivity> {

	public AppNamePreferenceActivityTest() {
		super(AppNamePreferenceActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		startActivity(new Intent(), null, null);
	}

	@SuppressWarnings("deprecation")
	public void testOwnPackageNameIsAvailable() {
		AppNamePreferenceActivity activity = getActivity();
		String preferenceKey = resolveOwnPackageName(activity);
		Preference preference = activity.findPreference(preferenceKey);

		assertNotNull(preference);
	}
}
