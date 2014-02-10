package io.github.sealor.android.applock.test;

import static io.github.sealor.android.applock.test.tooling.TestUtils.resolveOwnApplicationInfo;
import io.github.sealor.android.applock.appinfo.AppInfoResolver;
import io.github.sealor.android.applock.appinfo.PackageManagerAppInfoResolver;

import java.util.List;

import android.content.pm.ApplicationInfo;
import android.test.AndroidTestCase;

public class PackageManagerAppInfoResolverTest extends AndroidTestCase {

	public void testResolveAllInstalledAppInfos() {
		AppInfoResolver resolver = new PackageManagerAppInfoResolver(getContext().getPackageManager());

		List<ApplicationInfo> appInfos = resolver.resolveAllInstalledAppInfos();

		assertNotNull(appInfos);
		assertEquals(true, contains(appInfos, resolveOwnApplicationInfo(getContext())));
	}

	private boolean contains(List<ApplicationInfo> appInfos, ApplicationInfo searchAppInfo) {
		for (ApplicationInfo appInfo : appInfos) {
			if (searchAppInfo.packageName.equals(appInfo.packageName)) {
				return true;
			}
		}
		return false;
	}
}
