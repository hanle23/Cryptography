package foundation;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

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
		Key secret = new SecretKeySpec(ky, "DES");
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
	
	public static void main(String[] args) throws Exception {
		String text = "8A9FF0E2CD27DA4DC7F0C810E73D0E3B3B27CA03762BAE85597995997E625BDF0FEC655994EDD4B0851D7955B3F66717A52F83D01D73ABD9C593DA8C8CCBB073BB19E78442D9AA6D13B307EC0E8EA191E6A21897A82F1A643DC3BE0E12854D01C6006AA1D0EB1B94CAC573908018F284";
		String key = "FACEBOOK";
		byte[] text_byte = CryptoTools.hexToBytes(text);
		byte[] key_byte = key.getBytes();
		byte[] key_inversed = CryptoTools.bit_complement(key_byte);
 		Key secret = new SecretKeySpec(key_inversed, "DES");
 		Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
 		cipher.init(Cipher.DECRYPT_MODE, secret);
 		byte[] ct = cipher.doFinal(text_byte);
 		Key secret1 = new SecretKeySpec(key_byte, "DES");
 		cipher.init(Cipher.DECRYPT_MODE, secret1);
 		byte[] ct1 = cipher.doFinal(ct);
 		System.out.println(new String(ct1, "UTF-8"));
	}
}


