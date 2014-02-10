package io.github.sealor.android.applock.appinfo;

import java.util.List;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class PackageManagerAppInfoResolver implements AppInfoResolver {

	private final PackageManager packageManager;

	public PackageManagerAppInfoResolver(PackageManager packageManager) {
		super();

		this.packageManager = packageManager;
	}

	@Override
	public List<ApplicationInfo> resolveAllInstalledAppInfos() {
		return this.packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
	}
}
