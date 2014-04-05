package io.github.sealor.android.applock.activity.preference;

import android.content.Context;
import android.util.AttributeSet;

public class AmazonPreference extends AbstractActionPreference {

	public AmazonPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected String getActionUri() {
		return "http://www.amazon.de/registry/wishlist/141TJJFQ4EQT3";
	}
}
