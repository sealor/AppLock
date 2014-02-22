package io.github.sealor.android.applock.test.model;

import io.github.sealor.android.applock.appinfo.AppInfo;
import junit.framework.TestCase;

public class AppInfoTest extends TestCase {

	public void testEquals() {
		AppInfo appInfo = createAppInfo("label", "packageName");

		assertTrue(appInfo.equals(appInfo));
		assertFalse(appInfo.equals(null));
		assertFalse(appInfo.equals(new Object()));

		assertTrue(appInfo.equals(createAppInfo("label", "packageName")));
		assertFalse(appInfo.equals(createAppInfo(null, "packageName")));
		assertFalse(appInfo.equals(createAppInfo("label", null)));
		assertFalse(createAppInfo(null, "packageName").equals(appInfo));
		assertFalse(createAppInfo("label", null).equals(appInfo));
	}

	public void testHashcode() {
		AppInfo appInfo = createAppInfo("label", "packageName");
		assertTrue(appInfo.hashCode() == appInfo.hashCode());

		assertTrue(createAppInfo("label", "packageName").hashCode() == createAppInfo("label", "packageName").hashCode());
		assertTrue(createAppInfo(null, "packageName").hashCode() == createAppInfo(null, "packageName").hashCode());
		assertTrue(createAppInfo("label", null).hashCode() == createAppInfo("label", null).hashCode());
		assertTrue(createAppInfo(null, null).hashCode() == createAppInfo(null, null).hashCode());
	}

	private AppInfo createAppInfo(String label, String packageName) {
		AppInfo appInfo = new AppInfo();
		appInfo.setLabel(label);
		appInfo.setPackageName(packageName);
		return appInfo;
	}
}
