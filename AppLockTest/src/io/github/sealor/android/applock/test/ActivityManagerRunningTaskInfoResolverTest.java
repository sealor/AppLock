package io.github.sealor.android.applock.test;

import static io.github.sealor.android.applock.test.tooling.TestUtils.resolveOwnPackageName;
import static io.github.sealor.android.applock.test.tooling.TestUtils.startTestActivity;
import io.github.sealor.android.applock.taskinfo.ActivityManagerRunningTaskInfoResolver;
import io.github.sealor.android.applock.taskinfo.RunningTaskInfoResolver;
import android.test.AndroidTestCase;

public class ActivityManagerRunningTaskInfoResolverTest extends AndroidTestCase {

	public void testResolveRunningAppPackageName() {
		RunningTaskInfoResolver resolver = new ActivityManagerRunningTaskInfoResolver(getContext());
		startTestActivity(getContext());

		String runningAppPackageName = resolver.resolveRunningAppPackageName();

		assertEquals(resolveOwnPackageName(getContext()), runningAppPackageName);
	}
}
