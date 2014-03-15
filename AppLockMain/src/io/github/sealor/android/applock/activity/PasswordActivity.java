package io.github.sealor.android.applock.activity;

import io.github.sealor.android.applock.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class PasswordActivity extends Activity implements OnClickListener {

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
		if ("534".equals(this.passwordInput.getText().toString())) {
			finish();
		} else {
			this.passwordInput.getText().clear();
		}
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
