package io.github.sealor.android.applock.test;

import static io.github.sealor.android.applock.test.tooling.TestUtils.resolveOwnPackageName;
import static io.github.sealor.android.applock.test.tooling.TestUtils.startTestActivity;
import io.github.sealor.android.applock.taskinfo.RunningTaskInfoResolver;
import android.content.Intent;
import android.test.ServiceTestCase;

public class RunningTaskInfoResolverTest extends ServiceTestCase<TestFactoryService> {

	public RunningTaskInfoResolverTest() {
		super(TestFactoryService.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		startService(new Intent());
	}

	public void testResolveRunningAppPackageName() {
		RunningTaskInfoResolver resolver = getService().createRunningTaskInfoResolver();
		startTestActivity(getContext());

		String runningAppPackageName = resolver.resolveRunningAppPackageName();

		assertEquals(resolveOwnPackageName(getContext()), runningAppPackageName);
	}
}
