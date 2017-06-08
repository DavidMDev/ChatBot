package com.web.atrio.users.utilities;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.security.crypto.codec.Base64;

@Converter
public class PasswordEncrypter implements AttributeConverter<String, String> {

	private static String SECRETKEY = "1234567890123456";
	private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
	private static final byte[] KEY = SECRETKEY.getBytes();

	@Override
	public String convertToDatabaseColumn(String password) {
		// do some encryption
		Key key = new SecretKeySpec(KEY, "AES");
		try {
			Cipher c = Cipher.getInstance(ALGORITHM);
			c.init(Cipher.ENCRYPT_MODE, key);
			return new String(Base64.encode(c.doFinal(password.getBytes())));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		// do some decryption
		Key key = new SecretKeySpec(KEY, "AES");
		try {
			Cipher c = Cipher.getInstance(ALGORITHM);
			c.init(Cipher.DECRYPT_MODE, key);
			return new String(c.doFinal(Base64.decode(dbData.getBytes())));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
