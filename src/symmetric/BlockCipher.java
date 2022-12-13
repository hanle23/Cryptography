package symmetric;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class BlockCipher {
	private String pt;
	private String key;
	public BlockCipher(String pt, String key) {
		this.pt = pt;
		this.key = key;
	}
	
	public BlockCipher(byte[] pt, byte[] key) {
		this.pt = CryptoTools.bytesToHex(pt);
		this.key = CryptoTools.bytesToHex(key);
	}
	
	public BlockCipher(byte[] pt, String key) {
		this.pt = CryptoTools.bytesToHex(pt);
		this.key = key;
	}
	
	public BlockCipher(String pt, byte[] key) {
		this.pt = pt;
		this.key = CryptoTools.bytesToHex(key);
	}
	
	
	public byte[] encrypt(String constructor, String iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] iv_new = null;
		if (iv != null) {
			iv_new = CryptoTools.hexToBytes(iv);
		}
		byte[] ky = CryptoTools.hexToBytes(this.key);
		byte[] pt_new = CryptoTools.hexToBytes(this.pt);
		Key secret = new SecretKeySpec(ky, "DES");
		Cipher cipher = Cipher.getInstance(constructor);
		if (iv_new != null) {
			AlgorithmParameterSpec aps = new IvParameterSpec(iv_new);
			cipher.init(Cipher.ENCRYPT_MODE, secret, aps);
		} else {
			cipher.init(Cipher.ENCRYPT_MODE, secret);
		}
		byte[] ct = cipher.doFinal(pt_new);
		
		return ct;
	}
	
	public byte[] decrypt(String constructor, String iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] iv_new = null;
		if (iv != null) {
			iv_new = CryptoTools.hexToBytes(iv);
		}
		byte[] ky = CryptoTools.hexToBytes(this.key);
		byte[] pt_new = CryptoTools.hexToBytes(this.pt);
		String encription_type = constructor.substring(0, 3);
		Key secret = new SecretKeySpec(ky, encription_type);
		Cipher cipher = Cipher.getInstance(constructor);
		if (iv_new != null) {
			AlgorithmParameterSpec aps = new IvParameterSpec(iv_new);
			cipher.init(Cipher.DECRYPT_MODE, secret, aps);
		} else {
			cipher.init(Cipher.DECRYPT_MODE, secret);
		}
		byte[] ct = cipher.doFinal(pt_new);
		
		return ct;
	}
	
	public static void decrypt_even_odd() throws Exception {
		String ct = "80148EACC62D91D1BA297A477A8B6FBCD1EA13A43859D464";
		byte[] iv = "InVector".getBytes();
		byte[] ky1 = "HelpThem".getBytes();
		byte[] ky2 = "OurRight".getBytes();
		byte[] ctext = CryptoTools.hexToBytes(ct);
		Cipher cipher1 = Cipher.getInstance("DES/CBC/NoPadding");
		Key secret1 = new SecretKeySpec(ky1, "DES");
		AlgorithmParameterSpec aps1 = new IvParameterSpec(iv);
		cipher1.init(Cipher.DECRYPT_MODE, secret1, aps1);
		byte[] pt1 = cipher1.doFinal(ctext, 0, 8);
		Cipher cipher2 = Cipher.getInstance("DES/CBC/NoPadding");
		Key secret2 = new SecretKeySpec(ky2, "DES");
		AlgorithmParameterSpec aps2 = new IvParameterSpec(ctext, 0, 8);
		cipher2.init(Cipher.DECRYPT_MODE, secret2, aps2);
		byte[] pt2 = cipher2.doFinal(ctext, 8, 8);
		Cipher cipher3 = Cipher.getInstance("DES/CBC/PKCS5Padding");
		Key secret3 = new SecretKeySpec(ky1, "DES");
		AlgorithmParameterSpec aps3 = new IvParameterSpec(ctext, 8, 8);
		cipher3.init(Cipher.DECRYPT_MODE, secret1, aps3);
		byte[] pt3 = cipher3.doFinal(ctext, 16, 8);
		System.out.println(CryptoTools.byteToString(pt1) + CryptoTools.byteToString(pt2) + CryptoTools.byteToString(pt3));
	}
	
	public static void main(String[] args) throws Exception {
		decrypt_even_odd();
		
	}
}


