package io.github.sealor.android.applock.test;

import static io.github.sealor.android.applock.test.tooling.TestUtils.resolveOwnPackageName;
import io.github.sealor.android.applock.appinfo.AppInfo;
import io.github.sealor.android.applock.appinfo.AppInfoResolver;
import io.github.sealor.android.applock.appinfo.PackageManagerAppInfoResolver;

import java.util.List;

import android.test.AndroidTestCase;

public class PackageManagerAppInfoResolverTest extends AndroidTestCase {

	public void testResolveAllInstalledAppInfos() {
		AppInfoResolver resolver = new PackageManagerAppInfoResolver(getContext().getPackageManager());

		List<AppInfo> appInfos = resolver.resolveAllInstalledAppInfos();

		assertNotNull(appInfos);
		assertEquals(true, containsOwnPackageName(appInfos, resolveOwnPackageName(getContext())));
	}

	private boolean containsOwnPackageName(List<AppInfo> appInfos, String ownPackageName) {
		for (AppInfo appInfo : appInfos) {
			if (ownPackageName.equals(appInfo.getPackageName())) {
				return true;
			}
		}
		return false;
	}
}
