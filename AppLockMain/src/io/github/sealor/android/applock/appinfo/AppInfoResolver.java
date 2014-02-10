package io.github.sealor.android.applock.appinfo;

import java.util.List;

public interface AppInfoResolver {

	List<AppInfo> resolveAllInstalledAppInfos();
}
