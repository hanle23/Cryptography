package foundation;

import java.math.BigInteger;

import util.CryptoTools;

public class Affine {
	public static byte[] decrypt(byte[] ct, int a, int b) throws Exception {
		byte[] pt = new byte[ct.length];
		BigInteger inverse = BigInteger.valueOf(a).modInverse(BigInteger.valueOf(26));
		for (int i = 0; i < ct.length; i++) {
			pt[i] = (byte) (inverse.intValue() * (ct[i] - b) % 26 + 'A');
		}
		return pt;
		
	}
	
	public static void Exhaust(String fileName) throws Exception {
		byte[] ct = CryptoTools.fileToBytes(fileName);
		int[] aValues = {1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
		int targetA = 0;
		int targetB = 0;
		double maxDot = 0;
		for (int a = 0; a < aValues.length; a++) {
			for (int b = 0; b < 26; b++) {
				byte[] test = decrypt(ct, aValues[a], b);
				int[] frq = CryptoTools.getFrequencies(test);
				double dot = 0;
				for (int i = 0; i < 26; i++)
				{
					dot += (frq[i] / (double) ct.length) * CryptoTools.ENGLISH[i];
				}
				if (dot > maxDot) 
				{
					maxDot = dot;
					targetA = aValues[a];
					targetB = b;
				}
			}
		}
		System.out.println("Key A: " + targetA + ", Key B: " + targetB + ", DOT value: " + maxDot);
	}
	public static void main(String[] args) throws Exception {
		Exhaust("data/MSG3.ct");
	}
}
