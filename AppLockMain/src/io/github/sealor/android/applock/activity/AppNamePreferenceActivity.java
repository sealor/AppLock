package io.github.sealor.android.applock.activity;

import static io.github.sealor.android.applock.tooling.ContextUtils.resolvePackageManager;
import io.github.sealor.android.applock.ControllerFactory;
import io.github.sealor.android.applock.appinfo.ApplicationInfoResolver;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class AppNamePreferenceActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ControllerFactory factory = ControllerFactory.INSTANCE;
		PackageManager packageManager = resolvePackageManager(this);
		ApplicationInfoResolver applicationInfoResolver = factory.createApplicationInfoResolver(packageManager);

		addCheckBoxPreferencesForAllApplicationInfos(applicationInfoResolver);
	}

	@SuppressWarnings("deprecation")
	private void addCheckBoxPreferencesForAllApplicationInfos(ApplicationInfoResolver applicationInfoResolver) {
		PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(this);

		for (ApplicationInfo applicationInfo : applicationInfoResolver.resolveAllInstalledAppInfos()) {
			CheckBoxPreference checkBoxPreference = createCheckBoxPreference(applicationInfo);
			screen.addPreference(checkBoxPreference);
		}

		setPreferenceScreen(screen);
	}

	private CheckBoxPreference createCheckBoxPreference(ApplicationInfo applicationInfo) {
		CheckBoxPreference checkBoxPreference = new CheckBoxPreference(this);
		checkBoxPreference.setKey(applicationInfo.packageName);
		checkBoxPreference.setTitle(applicationInfo.packageName);
		return checkBoxPreference;
	}
}
