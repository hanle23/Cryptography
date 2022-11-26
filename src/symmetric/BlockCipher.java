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
	
	public static void main(String[] args) throws Exception {
		String ct_text = "80148EACC62D91D1BA297A477A8B6FBCD1EA13A43859D464";
		int counter = 0;
		byte[] ct = CryptoTools.hexToBytes(ct_text);
		for (int i = 1; i < 3; i++) {
			if (i % 2 != 0) {
				String key_text = "HelpThem";
				String key = CryptoTools.bytesToHex(key_text.getBytes());
				byte[] current = Arrays.copyOfRange(ct, counter, counter + 8);
				System.out.println(current.length);
				BlockCipher temp = new BlockCipher(current, key);
				String iv_text = "InVector";
				String IV = CryptoTools.bytesToHex(iv_text.getBytes());
				byte[] result = temp.decrypt("DES/CBC/NoPadding", IV);
				System.out.println(CryptoTools.byteToString(result));
			} else {
				String key_text = "OurRight";
				String key = CryptoTools.bytesToHex(key_text.getBytes());
				byte[] current = Arrays.copyOfRange(ct, counter, counter + 7);
				BlockCipher temp = new BlockCipher(current, key);
				String iv_text = "InVector";
				String IV = CryptoTools.bytesToHex(iv_text.getBytes());
				byte[] result = temp.decrypt("DES/CBC/PKCS5Padding", IV);
				System.out.println(CryptoTools.byteToString(result));
			}
			counter += 8;
		}
		
	}
}


