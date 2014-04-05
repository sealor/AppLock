package io.github.sealor.android.applock.activity;

import io.github.sealor.android.applock.AppLockService;
import io.github.sealor.android.applock.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MainPreferenceActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AppLockService.start(this);

		addPreferencesFromResource(R.xml.main_preference_activity);
	}
}
