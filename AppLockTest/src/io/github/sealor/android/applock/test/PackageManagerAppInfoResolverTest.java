package io.github.sealor.android.applock.test;

import static io.github.sealor.android.applock.test.tooling.TestUtils.resolveOwnApplicationInfo;
import io.github.sealor.android.applock.appinfo.AppInfo;
import io.github.sealor.android.applock.appinfo.AppInfoResolver;
import io.github.sealor.android.applock.appinfo.PackageManagerAppInfoResolver;

import java.util.List;

import android.content.pm.ApplicationInfo;
import android.test.AndroidTestCase;

public class PackageManagerAppInfoResolverTest extends AndroidTestCase {

	public void testResolveAllInstalledAppInfos() {
		AppInfoResolver resolver = new PackageManagerAppInfoResolver(getContext().getPackageManager());

		List<AppInfo> appInfos = resolver.resolveAllInstalledAppInfos();

		assertNotNull(appInfos);
		assertEquals(true, contains(appInfos, resolveOwnApplicationInfo(getContext())));
	}

	private boolean contains(List<AppInfo> appInfos, ApplicationInfo searchAppInfo) {
		for (AppInfo appInfo : appInfos) {
			if (searchAppInfo.packageName.equals(appInfo.getPackageName())) {
				return true;
			}
		}
		return false;
	}
}
