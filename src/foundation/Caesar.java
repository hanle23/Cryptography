package foundation;

import java.io.UnsupportedEncodingException;

import util.CryptoTools;

public class Caesar
{
	public static void encrypt(String fileName, int key) throws Exception {
		byte[] raw = CryptoTools.fileToBytes(fileName);
		byte[] pt = CryptoTools.clean(raw);
		// --------------------------------------------Encrypt the data
		byte[] ct = new byte[pt.length];
		for (int i = 0; i < pt.length; i++)
		{
			ct[i] = (byte) (((pt[i] - 'A') + key) % 26 + 'A');
		}		
		String newFileName = fileName.substring(0, fileName.indexOf("."));
		newFileName += ".ct";
		CryptoTools.bytesToFile(ct, newFileName);
	}
	
	public static int exhaustive(byte[] ct, int[] frq) {
		// --------------------------------------------Exhaustive
		// Try every possible key and find the frequency vector closest to English by dot product
		// for (int f = 0; f < 26; f++) 	System.out.printf("%.2f ", CryptoTools.ENGLISH[f]); System.out.println();
		double maxDot = 0;
		int targetKey = 0;
		for (int k = 0; k < 26; k++)
		{
			byte[] cand = new byte[ct.length];
			for (int i = 0; i < ct.length; i++)
			{
				cand[i] = (byte) ((ct[i] - 'A' - k) % 26);
				if (cand[i] < 0) cand[i] += 26;
				cand[i] += 'A';
			}
			frq = CryptoTools.getFrequencies(cand);
			//for (int f : frq) System.out.printf("%.2f ", f / (double) ct.length); System.out.println();
			double dot = 0;
			for (int i = 0; i < 26; i++)
			{
				dot += (frq[i] / (double) ct.length) * CryptoTools.ENGLISH[i];
			}
			//System.out.println("Candidate key key = " + k + "==> Dot = " + dot);
			if (dot > maxDot) 
			{
				maxDot = dot;
				targetKey = k;
			}
		}
		System.out.println("Based on max dot product, key is: " + targetKey);
		return targetKey;
	}
	
	public static int[] frequency(byte[] ct) {
		// --------------------------------------------Cryptanalytic
		// The most frequent letter in ct is likely the shifted E
		int[] frq = CryptoTools.getFrequencies(ct);
		int maxI = 0;
		for (int i = 0; i < 26; i++)
		{
			if (frq[i] > frq[maxI])
				maxI = i;
		}
		int shift = maxI - 4;
		if (shift < 0) 	shift += 26;
		System.out.println("Based on most frequent, key is: " + shift);
		return frq;
	}
	
	public static String decrypt(byte[] ct, int key) throws UnsupportedEncodingException {
		String result = "";
		byte[] pt = new byte[ct.length];
		for (int i = 0; i < ct.length; i++)
		{
			pt[i] = (byte) ((ct[i] - 'A') - key % 26);
			if (pt[i] < 0) {
				pt[i] += 26;
			}
			pt[i] = (byte) (pt[i] + 'A');
		}
		result = CryptoTools.byteToString(pt);
		return result;
	}

	public static void main(String[] args) throws Exception
	{
		byte[] ct = CryptoTools.fileToBytes("data/MSG2.ct");
		int[] frq = frequency(ct);
		String result = decrypt(ct, 22);
		System.out.println(result);

	}

}