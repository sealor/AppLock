package io.github.sealor.android.applock.activity;

import static io.github.sealor.android.applock.tooling.ContextUtils.resolvePackageManager;
import io.github.sealor.android.applock.ControllerFactory;
import io.github.sealor.android.applock.appinfo.AppInfo;
import io.github.sealor.android.applock.appinfo.AppInfoResolver;
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
		AppInfoResolver applicationInfoResolver = factory.createAppInfoResolver(packageManager);

		addCheckBoxPreferencesForAllApplicationInfos(applicationInfoResolver);
	}

	@SuppressWarnings("deprecation")
	private void addCheckBoxPreferencesForAllApplicationInfos(AppInfoResolver applicationInfoResolver) {
		PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(this);

		for (AppInfo appInfo : applicationInfoResolver.resolveAllInstalledAppInfos()) {
			CheckBoxPreference checkBoxPreference = createCheckBoxPreference(appInfo);
			screen.addPreference(checkBoxPreference);
		}

		setPreferenceScreen(screen);
	}

	private CheckBoxPreference createCheckBoxPreference(AppInfo appInfo) {
		CheckBoxPreference checkBoxPreference = new CheckBoxPreference(this);
		checkBoxPreference.setKey(appInfo.getPackageName());
		checkBoxPreference.setTitle(appInfo.getName());
		return checkBoxPreference;
	}
}
