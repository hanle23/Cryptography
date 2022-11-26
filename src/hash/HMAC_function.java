package hash;


import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class HMAC_function
{
	final static int BLOCK = 64;
	final static byte OPAD = 0x5C;
	final static byte IPAD = 0x36;

	public static void main(String[] args) throws Exception
	{
		String m = "Mainly cloudy with 40 percent chance of showers";
		String k = "This is an ultra-secret key";
		byte[] answer = hmac(m.getBytes(), k.getBytes());
		System.out.println("Answer: " + CryptoTools.bytesToHex(answer));
		
		byte[] answerJ = JCEhmac(m.getBytes(), k.getBytes());
		System.out.println("JCEhmac answer: " + CryptoTools.bytesToHex(answerJ));
	}
	
	private static byte[] hmac(byte[] msg, byte[] key) throws Exception
	{
		if (key.length > BLOCK) key = hash(key);
		key = pad(BLOCK, key, (byte) 0);
		//----------------
		byte[] tmp1 = xor(key, pad(BLOCK, new byte[] {}, OPAD ));
		byte[] tmp2 = xor(key, pad(BLOCK, new byte[] {}, IPAD ));
		tmp2 = hash(cat(tmp2, msg));
		return hash(cat(tmp1, tmp2));
	}
	
	private static byte[] hash(byte[] a) throws Exception
	{
		MessageDigest md = MessageDigest.getInstance("SHA1");
		return md.digest(a);
	}

	private static byte[] xor(byte[] a, byte[] b)
	{
		if (a.length != b.length) throw new RuntimeException("diff size");
		byte[] result = new byte[a.length];
		for (int i = 0; i < result.length; i++)
			result[i] = (byte) (a[i] ^ b[i]);
		return result;
	}
	
	private static byte[] cat(byte[] a, byte[] b)
	{
		return CryptoTools.hexToBytes(CryptoTools.bytesToHex(a) + CryptoTools.bytesToHex(b));
	}
	
	private static byte[] pad(int target, byte[] ar, byte b)
	{
		byte[] result = new byte[target];
		for (int i = 0; i < target; i++)
			result[i] = (i < ar.length) ? ar[i] : b;
		return result;
	}
	
	private static byte[] JCEhmac(byte[] msg, byte[] key) throws Exception
	{
		Mac m = Mac.getInstance("HmacSha1");
		Key k = new SecretKeySpec(key, "HmacSha1");
		m.init(k);
		return m.doFinal(msg);
	}

}

