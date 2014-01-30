package io.github.sealor.android.applock.appnamestorage;

import android.content.SharedPreferences;

public class SharedPreferencesRestrictedAppNameStorage implements RestrictedAppNameStorage {

	private final SharedPreferences sharedPreferences;

	public SharedPreferencesRestrictedAppNameStorage(SharedPreferences sharedPreferences) {
		this.sharedPreferences = sharedPreferences;
	}

	@Override
	public boolean isPackageNameRestricted(String appPackageName) {
		return this.sharedPreferences.getBoolean(appPackageName, false);
	}
}
