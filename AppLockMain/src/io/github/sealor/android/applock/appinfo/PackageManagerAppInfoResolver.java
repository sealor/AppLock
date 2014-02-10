package io.github.sealor.android.applock.appinfo;

import java.util.ArrayList;
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
	public List<AppInfo> resolveAllInstalledAppInfos() {
		List<AppInfo> appInfos = new ArrayList<AppInfo>();

		List<ApplicationInfo> applicationInfos = resolveApplicationInfos();
		for (ApplicationInfo applicationInfo : applicationInfos) {
			AppInfo appInfo = mapToAppInfo(applicationInfo);
			appInfos.add(appInfo);
		}

		return appInfos;
	}

	private List<ApplicationInfo> resolveApplicationInfos() {
		return this.packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
	}

	private AppInfo mapToAppInfo(ApplicationInfo applicationInfo) {
		AppInfo appInfo = new AppInfo();
		appInfo.setPackageName(applicationInfo.packageName);
		appInfo.setLabel(applicationInfo.loadLabel(this.packageManager));
		return appInfo;
	}
}
