package io.github.sealor.android.applock.activity.preference;

import io.github.sealor.android.applock.tooling.DigestUtils;
import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

public class EditPasswordPreference extends EditTextPreference {

	public EditPasswordPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected boolean persistString(String value) {
		return super.persistString(DigestUtils.tryToCreateSha256(value));
	}

	@Override
	protected String getPersistedString(String defaultReturnValue) {
		return "password";
	}
}
