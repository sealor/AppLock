package io.github.sealor.android.applock;

import io.github.sealor.android.applock.activity.PasswordActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppLockBroadcastReceiver extends BroadcastReceiver {

	public static final String RESTRICTED_APP_STARTED_BROADCAST = "io.github.sealor.android.applock.RESTRICTED_APP_STARTED";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
			startAppLockService(context);
		}

		if (RESTRICTED_APP_STARTED_BROADCAST.equals(intent.getAction())) {
			openPasswordActivity(context);
		}
	}

	private void startAppLockService(Context context) {
		AppLockService.start(context);
	}

	private void openPasswordActivity(Context context) {
		Intent startIntent = new Intent(context, PasswordActivity.class);
		startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(startIntent);
	}
}
