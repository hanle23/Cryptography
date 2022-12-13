package hash;

import java.security.*;
import util.CryptoTools;

public class HashFunction
{
	
	public static void YMAC() throws Exception {
		String msg = "Maximum price is @@ plus tax";
		String key = "52456BAF456AB5555DEFA4";
		String ymac = "A500C1589108B7DED7B35C82DF0D141F4BC9FC65";
		MessageDigest md = MessageDigest.getInstance("SHA1");
		for (int nn = 11; nn < 100; nn++)
		{
		String test = msg.substring(0,17) + nn + msg.substring(19);
		String kmk = key + CryptoTools.bytesToHex(test.getBytes()) + key;
		String hash = CryptoTools.bytesToHex(md.digest(CryptoTools.hexToBytes(kmk)));
		if (hash.equals(ymac)) System.out.println(nn);
		}
	}
	
	public static void hash_original() throws Exception {
		byte[] msg = CryptoTools.hexToBytes("A500C1589108B7DED7B35C82DF0D141F4BC9FC65");
		
		String algo = "SHA-1";
		MessageDigest md = MessageDigest.getInstance(algo);
		byte[] digest = md.digest(msg);
		System.out.println(new String(digest));
	}
	public static void main(String[] args) throws Exception
	{
		YMAC();
	}
}

