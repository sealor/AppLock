package io.github.sealor.android.applock.test.tooling;

import io.github.sealor.android.applock.test.TestActivity;
import android.content.Context;
import android.content.Intent;

public class TestUtils {

	public static String resolveOwnPackageName(Context context) {
		return context.getApplicationInfo().packageName;
	}

	public static void startTestActivity(Context context) {
		Intent intent = new Intent(context, TestActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
