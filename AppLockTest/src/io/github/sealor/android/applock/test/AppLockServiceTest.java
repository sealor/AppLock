package io.github.sealor.android.applock.test;

import io.github.sealor.android.applock.AppLockService;
import io.github.sealor.android.applock.ControllerFactory;
import io.github.sealor.android.applock.RunningAppCheckTask;
import io.github.sealor.android.applock.appnamestorage.RestrictedAppNameStorage;
import io.github.sealor.android.applock.taskinfo.TaskInfoResolver;
import android.content.Context;
import android.content.Intent;
import android.test.ServiceTestCase;

public class AppLockServiceTest extends ServiceTestCase<AppLockService> {

	public AppLockServiceTest() {
		super(AppLockService.class);
	}

	public void testRepeatingAppCheck() throws InterruptedException {
		MockRunningAppCheckTask task = new MockRunningAppCheckTask();
		ControllerFactory.INSTANCE = new MockControllerFactory(task);
		startService(new Intent());

		int runCount = 3;
		synchronized (task) {
			for (int i = 0; i < runCount; i++) {
				task.wait(AppLockService.MILLIS_CHECK_FREQUENCY * 7);
			}
		}

		assertEquals(runCount, task.runCount);
	}

	private class MockControllerFactory extends ControllerFactory {

		private RunningAppCheckTask task;

		public MockControllerFactory(RunningAppCheckTask task) {
			super();

			this.task = task;
		}

		@Override
		public RunningAppCheckTask createRunningAppCheckTask(TaskInfoResolver taskInfoResolver,
				RestrictedAppNameStorage restrictedAppNameStorage, Context context) {
			return this.task;
		}
	}

	private class MockRunningAppCheckTask extends RunningAppCheckTask {

		private int runCount = 0;

		public MockRunningAppCheckTask() {
			super(null, null, null);
		}

		@Override
		public void run() {
			synchronized (this) {
				this.runCount++;
				notifyAll();
			}
		}
	}
}
