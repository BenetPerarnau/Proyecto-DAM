package Model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class Seguretat {
	
	
	
	public static String generateMd5(String pass){
		
		String original = pass;
		MessageDigest md=null;
		String md5="";
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(original.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}

			md5=sb.toString();
			System.out.println("original:" + original);
			System.out.println("digested(hex):" + sb.toString());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5;
		
	}
	
	public static String generateSALT(){
		String SALT="";
			
		SecureRandom sr;
		try {
			sr = SecureRandom.getInstance("SHA1PRNG");
	        byte[] salt = new byte[16];
	        sr.nextBytes(salt);
	        for(int i = 0; i<16; i++) {
	            SALT=SALT+salt[i];
	        }
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		System.out.println(SALT);
		return SALT;
		
	}
	
	public static String generatePEPER(String text){
		String PEPER="";
		
		for(int i=text.length()-1; i>-1; i--){
			
			PEPER=PEPER+text.charAt(i);
				
		}
		
		
		
		return PEPER;
	}
	
	

}
