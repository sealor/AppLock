package io.github.sealor.android.applock.test;

import io.github.sealor.android.applock.R;
import io.github.sealor.android.applock.activity.PasswordActivity;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

public class PasswordActivityTest extends ActivityInstrumentationTestCase2<PasswordActivity> {

	public PasswordActivityTest() {
		super(PasswordActivity.class);
	}

	public void testCorrectPasswordInput() throws Throwable {
		final Activity activity = getActivity();

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
}
