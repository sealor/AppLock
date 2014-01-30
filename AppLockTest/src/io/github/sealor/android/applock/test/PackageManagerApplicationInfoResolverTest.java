package io.github.sealor.android.applock.test;

import static io.github.sealor.android.applock.test.tooling.TestUtils.resolveOwnApplicationInfo;
import io.github.sealor.android.applock.appinfo.ApplicationInfoResolver;
import io.github.sealor.android.applock.appinfo.PackageManagerApplicationInfoResolver;

import java.util.List;

import android.content.pm.ApplicationInfo;
import android.test.AndroidTestCase;

public class PackageManagerApplicationInfoResolverTest extends AndroidTestCase {

	public void testResolveAllInstalledAppInfos() {
		ApplicationInfoResolver resolver = new PackageManagerApplicationInfoResolver(getContext().getPackageManager());

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
