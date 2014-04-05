package io.github.sealor.android.applock.activity.preference;

import io.github.sealor.android.applock.R;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class AboutPreference extends DialogPreference {

	public AboutPreference(Context context, AttributeSet attrs) {
		super(context, attrs);

		String appName = context.getText(R.string.app_name).toString();
		String version = "";

		try {
			version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		setDialogTitle(String.format("%s %s", appName, version));
		setDialogLayoutResource(R.layout.about);
	}
}
