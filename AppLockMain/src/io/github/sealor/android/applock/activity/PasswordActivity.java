package io.github.sealor.android.applock.activity;

import io.github.sealor.android.applock.R;
import io.github.sealor.android.applock.tooling.DigestUtils;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class PasswordActivity extends Activity implements OnClickListener {

	public static final String PASSWORD_HASH_PREFERENCE_KEY = "PASSWORD_HASH";

	private EditText passwordInput;
	private Button passwordButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.password_activity);

		this.passwordInput = (EditText) findViewById(R.id.passwordInput);
		this.passwordButton = (Button) findViewById(R.id.passwordButton);

		this.passwordButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String inputPassword = String.valueOf(this.passwordInput.getText().toString());
		String inputPasswordHash = DigestUtils.tryToCreateSha256(inputPassword);
		String appPasswordHash = resolvePasswordHash();

		if (appPasswordHash == null || appPasswordHash.equals(inputPasswordHash)) {
			finish();
		} else {
			this.passwordInput.getText().clear();
		}
	}

	private String resolvePasswordHash() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		return prefs.getString(PASSWORD_HASH_PREFERENCE_KEY, null);
	}

	@Override
	public void onBackPressed() {
		launchHomeScreen();
	}

	private void launchHomeScreen() {
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);
	}
}
