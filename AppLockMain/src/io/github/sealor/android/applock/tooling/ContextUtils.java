package io.github.sealor.android.applock.tooling;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ContextUtils {

	public static ActivityManager resolveActivityManager(Context context) {
		return (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	}

	public static SharedPreferences resolveSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
}
