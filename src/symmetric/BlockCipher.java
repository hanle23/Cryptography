package symmetric;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class BlockCipher {
	private byte[] pt;
	private byte[] key;
	
	public BlockCipher(byte[] pt, byte[] key) {
		this.pt = pt;
		this.key = key;
	}
	
	
	public byte[] encrypt(String constructor, byte[] iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] iv_new = null;
		if (iv != null) {
			iv_new = iv;
		}
		byte[] ky = this.key;
		byte[] pt_new = this.pt;
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
	
	public byte[] decrypt(String constructor, byte[] iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] iv_new = null;
		if (iv != null) {
			iv_new = iv;
		}
		byte[] ky = this.key;
		byte[] pt_new = this.pt;
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
	
	public static void changes_bin() throws Exception {
		double sum = 0;
		for (int time = 0; time < 1000; time++) {
			byte[] key = "universe".getBytes();
			byte[] pt = "Facebook".getBytes();
			BlockCipher new_ops = new BlockCipher(pt, key);
			byte[] result = new_ops.decrypt("DES/ECB/NoPadding", null);
			String result_bin = CryptoTools.bytesToBin(result);
			System.out.println("Before flipping: " + result_bin);
			
			Random rng = new Random();
			int num = rng.nextInt(64);
			System.out.println("Random Number: " + num);
			String pt_2 = CryptoTools.bytesToBin(pt);
			if (pt_2.charAt(num) == '1') {
				pt_2 = pt_2.substring(0, num) + '0' + pt_2.substring(num + 1);
			} else {
				pt_2 = pt_2.substring(0, num) + '1' + pt_2.substring(num + 1);
			}
			byte[] pt_2_byte = CryptoTools.hexToBytes(CryptoTools.binToHex(pt_2));
			BlockCipher new_ops_2 = new BlockCipher(pt_2_byte, key);
			byte[] result_2 = new_ops_2.decrypt("DES/ECB/NoPadding", null);
			String result_2_bin = CryptoTools.bytesToBin(result_2);
			System.out.println("After flipping: " + result_2_bin);
			
			int changes = 0;
			for (int i = 0; i < result.length; i++) {
				if (result_bin.charAt(i) != result_2_bin.charAt(i)) {
					changes++;
				}
			}
			sum += changes;
			System.out.println("Changes bin: " + changes);
		}
		
		System.out.println("Average changes: " + (sum / 1000));

	}
	
	public static void main(String[] args) throws Exception {
		byte[] key = "FACEBOOK".getBytes();
		byte[] comp_key = CryptoTools.bit_complement(key);
		byte[] ct = CryptoTools.hexToBytes("8A9FF0E2CD27DA4DC7F0C810E73D0E3B3B27CA03762BAE85597995997E625BDF0FEC655994EDD4B0851D7955B3F66717A52F83D01D73ABD9C593DA8C8CCBB073BB19E78442D9AA6D13B307EC0E8EA191E6A21897A82F1A643DC3BE0E12854D01C6006AA1D0EB1B94CAC573908018F284");
		BlockCipher ops = new BlockCipher(ct, comp_key);
		byte[] ct_1 = ops.decrypt("DES/ECB/NoPadding", null);
		BlockCipher ops_2 = new BlockCipher(ct_1, key);
		byte[] pt = ops_2.decrypt("DES/ECB/NoPadding", null);
		System.out.println(CryptoTools.byteToString(pt));
 	}
}


