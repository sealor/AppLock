package io.github.sealor.android.applock.test.mock;

import io.github.sealor.android.applock.appnamestorage.RestrictedAppNameStorage;

public class MockRestrictedAppNameStorage implements RestrictedAppNameStorage {

	private final String restrictedAppPackageName;

	public MockRestrictedAppNameStorage(String restrictedAppPackageName) {
		this.restrictedAppPackageName = restrictedAppPackageName;
	}

	@Override
	public boolean isPackageNameRestricted(String appPackageName) {
		return this.restrictedAppPackageName.equals(appPackageName);
	}
}