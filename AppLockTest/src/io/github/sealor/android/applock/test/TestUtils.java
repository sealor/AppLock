package io.github.sealor.android.applock.test;

import android.content.Context;
import android.content.Intent;

public class TestUtils {

	static String resolveOwnPackageName(Context context) {
		return context.getApplicationInfo().packageName;
	}

	static void startTestActivity(Context context) {
		Intent intent = new Intent(context, TestActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
