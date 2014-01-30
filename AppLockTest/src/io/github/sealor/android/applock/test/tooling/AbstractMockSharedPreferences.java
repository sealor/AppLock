package io.github.sealor.android.applock.test.tooling;

import java.util.Map;
import java.util.Set;

import android.content.SharedPreferences;

public abstract class AbstractMockSharedPreferences implements SharedPreferences {

	@Override
	public boolean contains(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Editor edit() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, ?> getAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getBoolean(String key, boolean defValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getFloat(String key, float defValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getInt(String key, int defValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getLong(String key, long defValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getString(String key, String defValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> getStringSet(String arg0, Set<String> arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
		throw new UnsupportedOperationException();
	}
}
