package io.github.sealor.android.applock.tooling;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Base64;

public class DigestUtils {

	public static String createSha256(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			md.update(text.getBytes());
			byte[] digest = md.digest();

			return Base64.encodeToString(digest, Base64.DEFAULT);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
