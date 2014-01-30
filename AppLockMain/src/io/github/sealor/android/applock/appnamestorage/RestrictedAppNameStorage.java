package io.github.sealor.android.applock.appnamestorage;

public interface RestrictedAppNameStorage {

	boolean isPackageNameRestricted(String appPackageName);
}
