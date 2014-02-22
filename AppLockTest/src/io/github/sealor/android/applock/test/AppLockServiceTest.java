package io.github.sealor.android.applock.test;

import io.github.sealor.android.applock.AppLockService;
import io.github.sealor.android.applock.ControllerFactory;
import io.github.sealor.android.applock.StartingAppCheckTask;
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
		MockStartingAppCheckTask task = new MockStartingAppCheckTask();
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

	public void testForbiddenBinding() {
		startService(new Intent());
		assertNull(getService().onBind(null));
	}

	private class MockControllerFactory extends ControllerFactory {

		private StartingAppCheckTask task;

		public MockControllerFactory(StartingAppCheckTask task) {
			super();

			this.task = task;
		}

		@Override
		public StartingAppCheckTask createStartingAppCheckTask(TaskInfoResolver taskInfoResolver,
				RestrictedAppNameStorage restrictedAppNameStorage, Context context) {
			return this.task;
		}
	}

	private class MockStartingAppCheckTask extends StartingAppCheckTask {

		private int runCount = 0;

		public MockStartingAppCheckTask() {
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
