package hash;

import java.security.*;
import util.CryptoTools;

public class HashFunction
{
	public static void main(String[] args) throws Exception
	{
		byte[] msg = CryptoTools.hexToBytes("A500C1589108B7DED7B35C82DF0D141F4BC9FC65");
		
		String algo = "SHA-1";
		MessageDigest md = MessageDigest.getInstance(algo);
		byte[] digest = md.digest(msg);
		System.out.println(new String(digest));
	}
}

