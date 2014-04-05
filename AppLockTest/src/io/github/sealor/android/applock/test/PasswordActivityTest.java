package io.github.sealor.android.applock.test;

import static io.github.sealor.android.applock.activity.PasswordActivity.PASSWORD_HASH_PREFERENCE_KEY;
import static io.github.sealor.android.applock.tooling.ContextUtils.resolveActivityManager;
import io.github.sealor.android.applock.R;
import io.github.sealor.android.applock.activity.PasswordActivity;
import io.github.sealor.android.applock.tooling.DigestUtils;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

public class PasswordActivityTest extends ActivityInstrumentationTestCase2<PasswordActivity> {

	public PasswordActivityTest() {
		super(PasswordActivity.class);
	}

	public void testCorrectPasswordInput() throws Throwable {
		final Activity activity = getActivity();
		definePassword("534");

		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				EditText passwordInput = (EditText) activity.findViewById(R.id.passwordInput);
				Button passwordButton = (Button) activity.findViewById(R.id.passwordButton);

				passwordInput.setText("534");
				passwordButton.performClick();

				assertTrue(getActivity().isFinishing());
			}
		});
	}

	public void testIncorrectPasswordInput() throws Throwable {
		final Activity activity = getActivity();
		definePassword("534");

		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				EditText passwordInput = (EditText) activity.findViewById(R.id.passwordInput);
				Button passwordButton = (Button) activity.findViewById(R.id.passwordButton);

				passwordInput.setText("wrong");
				passwordButton.performClick();

				assertFalse(getActivity().isFinishing());
				assertTrue(passwordInput.getText().length() == 0);
			}
		});
	}

	public void testBackButtonLaunchesHomeScreen() throws Throwable {
		final Activity activity = getActivity();

		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				activity.onBackPressed();

				ActivityManager activityManager = resolveActivityManager(activity);
				List<RunningTaskInfo> infoList = activityManager.getRunningTasks(1);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				assertTrue(infoList.get(0).baseActivity.getPackageName().contains("launcher"));
			}
		});
	}

	private void definePassword(String password) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		prefs.edit().putString(PASSWORD_HASH_PREFERENCE_KEY, DigestUtils.createSha256(password)).commit();
	}
}
