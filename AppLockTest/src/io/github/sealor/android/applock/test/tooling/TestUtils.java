package io.github.sealor.android.applock.test.tooling;

import io.github.sealor.android.applock.test.TestActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;

public class TestUtils {

	public static ApplicationInfo resolveOwnApplicationInfo(Context context) {
		return context.getApplicationInfo();
	}

	public static String resolveOwnPackageName(Context context) {
		return resolveOwnApplicationInfo(context).packageName;
	}

	public static void startTestActivity(Context context) {
		Intent intent = new Intent(context, TestActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
