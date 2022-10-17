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
		String text = "LET US MEET HERE";
		String key = "DO NOT TELL EVE!";
		byte[] text_byte = text.getBytes();
		byte[] key_byte = key.getBytes();
 		Key secret = new SecretKeySpec(key_byte, "AES");
 		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
 		cipher.init(Cipher.DECRYPT_MODE, secret);
 		byte[] ct = cipher.doFinal(text_byte);
		text_byte[0] += 1;
		byte[] ct1 = cipher.doFinal(text_byte);
		String ct_result = CryptoTools.bytesToBin(ct);
		String ct1_result = CryptoTools.bytesToBin(ct1);
		int result = 0;
		for (int  i = 0; i < ct_result.length(); i++) {
			if (ct_result.charAt(i) != ct1_result.charAt(i)) {
				result++;
			}
		}
		System.out.println(result);
//		byte[] key_inversed = CryptoTools.bit_complement(key_byte);

 		
 		
// 		Key secret1 = new SecretKeySpec(key_byte, "DES");
// 		cipher.init(Cipher.DECRYPT_MODE, secret1);
// 		byte[] ct1 = cipher.doFinal(ct);
// 		System.out.println(new String(ct, "UTF-8"));
	}
}


