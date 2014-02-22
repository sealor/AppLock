package io.github.sealor.android.applock.test;

import static io.github.sealor.android.applock.test.tooling.TestUtils.resolveOwnPackageName;
import static io.github.sealor.android.applock.test.tooling.TestUtils.startTestActivity;
import static io.github.sealor.android.applock.tooling.ContextUtils.resolveActivityManager;
import io.github.sealor.android.applock.taskinfo.ActivityManagerTaskInfoResolver;
import io.github.sealor.android.applock.taskinfo.TaskInfoResolver;
import android.content.ComponentName;
import android.test.AndroidTestCase;

public class ActivityManagerTaskInfoResolverTest extends AndroidTestCase {

	public void testResolveRunningComponentName() {
		TaskInfoResolver resolver = new ActivityManagerTaskInfoResolver(resolveActivityManager(getContext()));
		startTestActivity(getContext());

		ComponentName runningComponentName = resolver.resolveRunningComponentName();

		assertEquals(resolveOwnPackageName(getContext()), runningComponentName.getPackageName());
	}
}
