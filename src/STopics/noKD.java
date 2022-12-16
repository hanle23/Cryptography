package STopics;

import java.math.BigInteger;

import util.CryptoTools;

public class noKD {
	
	public static void main(String[] args) throws Exception
	{
		Diffie_Hellman();
	}
	public static void Diffie_Hellman() throws Exception {
		BigInteger p = new BigInteger("341769842231234673709819975074677605139");
		BigInteger g = new BigInteger("37186859139075205179672162892481226795");
		BigInteger aX = new BigInteger("83986164647417479907629397738411168307");
		BigInteger bX = new BigInteger("140479748264028247931575653178988397140");
		
		BigInteger aY = g.modPow(aX, p);
		BigInteger bY = g.modPow(bX, p);
		BigInteger aK = bY.modPow(aX, p);
		BigInteger bK = aY.modPow(bX, p);
		System.out.println("aK == bK: " + aK.equals(bK));
		byte[] key = aK.toByteArray();
		System.out.println("Result: " + CryptoTools.bytesToHex(key));
	}

}
