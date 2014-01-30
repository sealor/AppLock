package io.github.sealor.android.applock.test;

import static io.github.sealor.android.applock.test.tooling.TestUtils.resolveOwnPackageName;
import static io.github.sealor.android.applock.test.tooling.TestUtils.startTestActivity;
import io.github.sealor.android.applock.taskinfo.ActivityManagerTaskInfoResolver;
import io.github.sealor.android.applock.taskinfo.TaskInfoResolver;
import android.test.AndroidTestCase;

public class ActivityManagerTaskInfoResolverTest extends AndroidTestCase {

	public void testResolveRunningAppPackageName() {
		TaskInfoResolver resolver = new ActivityManagerTaskInfoResolver(getContext());
		startTestActivity(getContext());

		String runningAppPackageName = resolver.resolveRunningAppPackageName();

		assertEquals(resolveOwnPackageName(getContext()), runningAppPackageName);
	}
}
