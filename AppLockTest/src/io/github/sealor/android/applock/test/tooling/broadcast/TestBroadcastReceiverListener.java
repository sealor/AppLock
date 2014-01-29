package io.github.sealor.android.applock.test.tooling.broadcast;

import android.content.Context;
import android.content.Intent;

public interface TestBroadcastReceiverListener {

	public void onReceive(Context context, Intent intent);
}
