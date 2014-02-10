package io.github.sealor.android.applock.appinfo;

import java.util.List;

import android.content.pm.ApplicationInfo;

public interface AppInfoResolver {

	List<ApplicationInfo> resolveAllInstalledAppInfos();
}
