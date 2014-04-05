package io.github.sealor.android.applock.activity.preference;

import io.github.sealor.android.applock.activity.AppNamePreferenceActivity;
import android.content.Context;
import android.content.Intent;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class AppNamePreference extends DialogPreference {

	public AppNamePreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onClick() {
		Intent intent = new Intent(getContext(), AppNamePreferenceActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getContext().startActivity(intent);
	}
}
