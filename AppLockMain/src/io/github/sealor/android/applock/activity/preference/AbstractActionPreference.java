package io.github.sealor.android.applock.activity.preference;

import io.github.sealor.android.applock.R;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.DialogPreference;
import android.text.ClipboardManager;
import android.util.AttributeSet;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public abstract class AbstractActionPreference extends DialogPreference {

	public AbstractActionPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onClick() {
		try {
			ClipboardManager clipboardManager = resolveClipboardManager();
			clipboardManager.setText(getActionUri());

			Toast.makeText(getContext(), R.string.link_copied_to_clipboard, Toast.LENGTH_SHORT).show();
		} finally {
			startAction(getActionUri());
		}
	}

	protected abstract String getActionUri();

	private ClipboardManager resolveClipboardManager() {
		return (ClipboardManager) getContext().getSystemService(Service.CLIPBOARD_SERVICE);
	}

	private void startAction(String uri) {
		getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
	}
}
