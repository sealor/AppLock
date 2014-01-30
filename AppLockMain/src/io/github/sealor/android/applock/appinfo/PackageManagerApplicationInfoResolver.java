package io.github.sealor.android.applock.appinfo;

import java.util.List;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class PackageManagerApplicationInfoResolver implements ApplicationInfoResolver {

	private final PackageManager packageManager;

	public PackageManagerApplicationInfoResolver(PackageManager packageManager) {
		super();

		this.packageManager = packageManager;
	}

	@Override
	public List<ApplicationInfo> resolveAllInstalledAppInfos() {
		return this.packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
	}
}
