package io.github.sealor.android.applock;

import static io.github.sealor.android.applock.tooling.ContextUtils.resolveActivityManager;
import static io.github.sealor.android.applock.tooling.ContextUtils.resolveSharedPreferences;
import io.github.sealor.android.applock.appnamestorage.RestrictedAppNameStorage;
import io.github.sealor.android.applock.taskinfo.TaskInfoResolver;

import java.util.Timer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class AppLockService extends Service {

	public static final long MILLIS_CHECK_FREQUENCY = 100;

	private Timer timer;

	public static void start(Context context) {
		context.startService(new Intent(context, AppLockService.class));
	}

	@Override
	public void onCreate() {
		super.onCreate();

		ControllerFactory factory = ControllerFactory.INSTANCE;

		TaskInfoResolver taskInfoResolver = factory.createTaskInfoResolver(resolveActivityManager(this));
		RestrictedAppNameStorage storage = factory.createRestrictedAppNameStorage(resolveSharedPreferences(this));
		RunningAppCheckTask task = factory.createRunningAppCheckTask(taskInfoResolver, storage, this);

		this.timer = new Timer(true);
		this.timer.schedule(task, 0, MILLIS_CHECK_FREQUENCY);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		if (this.timer != null) {
			this.timer.cancel();
		}

		super.onDestroy();
	}
}
