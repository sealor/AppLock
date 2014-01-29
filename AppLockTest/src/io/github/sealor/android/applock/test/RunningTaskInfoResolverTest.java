package io.github.sealor.android.applock.test;

import io.github.sealor.android.applock.RunningTaskInfoResolver;
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
		startTestActivity();

		String runningAppPackageName = resolver.resolveRunningAppPackageName();

		assertEquals(resolveOwnPackageName(), runningAppPackageName);
	}

	private String resolveOwnPackageName() {
		return getService().getApplicationInfo().packageName;
	}

	private void startTestActivity() {
		Intent intent = new Intent(getContext(), TestActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getContext().startActivity(intent);
	}
}
