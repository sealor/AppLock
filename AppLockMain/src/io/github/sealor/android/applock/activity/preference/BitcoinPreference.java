package io.github.sealor.android.applock.activity.preference;

import io.github.sealor.android.applock.R;
import android.app.Service;
import android.content.Context;
import android.preference.DialogPreference;
import android.text.ClipboardManager;
import android.util.AttributeSet;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class BitcoinPreference extends DialogPreference {

	private final static String BITCOIN_ADDRESS = "16HR7heNHSgVVMXBKKP99WNRWvYUtxnDAA";

	public BitcoinPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onClick() {
		ClipboardManager clipboardManager = resolveClipboardManager();
		clipboardManager.setText(BITCOIN_ADDRESS);

		Toast.makeText(getContext(), R.string.donation_bitcoin_toast, Toast.LENGTH_SHORT).show();
	}

	private ClipboardManager resolveClipboardManager() {
		return (ClipboardManager) getContext().getSystemService(Service.CLIPBOARD_SERVICE);
	}
}
