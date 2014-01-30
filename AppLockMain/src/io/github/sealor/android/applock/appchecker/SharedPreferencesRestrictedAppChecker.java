package io.github.sealor.android.applock.appchecker;

import android.content.SharedPreferences;

public class SharedPreferencesRestrictedAppChecker implements RestrictedAppChecker {

	private final SharedPreferences sharedPreferences;

	public SharedPreferencesRestrictedAppChecker(SharedPreferences sharedPreferences) {
		this.sharedPreferences = sharedPreferences;
	}

	@Override
	public boolean isPackageNameRestricted(String appPackageName) {
		return this.sharedPreferences.getBoolean(appPackageName, false);
	}
}
