package io.github.sealor.android.applock.test.tooling.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TestBroadcastReceiver extends BroadcastReceiver {

	private TestBroadcastReceiverListener listener;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.listener.onReceive(context, intent);
	}

	public void setListener(TestBroadcastReceiverListener listener) {
		this.listener = listener;
	}

	public TestBroadcastReceiverListener getListener() {
		return listener;
	}
}
