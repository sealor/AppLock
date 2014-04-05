package io.github.sealor.android.applock.activity.preference;

import android.content.Context;
import android.util.AttributeSet;

public class PayPalPreference extends AbstractActionPreference {

	public PayPalPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected String getActionUri() {
		return "https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=RCM9U4W33SUPE";
	}
}
