package io.github.sealor.android.applock.appchecker;

public interface RestrictedAppChecker {

	boolean isPackageNameRestricted(String appPackageName);
}
