package de.markozeidler.elib.app;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodePassword {

	public static void main(String[] args) {
        System.out.println(encode("m"));
	}

	private static String encode(String password) {
		String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
	}
}
