package io.github.sealor.android.applock.test;

import io.github.sealor.android.applock.RunningTaskInfoResolver;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TestFactoryService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public RunningTaskInfoResolver createRunningTaskInfoResolver() {
		return new RunningTaskInfoResolver(this);
	}
}
