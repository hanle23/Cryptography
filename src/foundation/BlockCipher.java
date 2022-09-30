package foundation;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

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
	
	public byte[] decrypt() {
		byte[] result = null;
		return  result;
	}
	
	public static void main(String[] args) throws Exception {
		String pt = "";
		String key = "";
		BlockCipher ct = new BlockCipher(pt, key);
		
	}
}


