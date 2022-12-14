package foundation;

import java.io.UnsupportedEncodingException;

import util.CryptoTools;

public class Caesar
{
	public static byte[] encrypt(String fileName, int key) throws Exception {
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
		return ct;
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
	
	public static int compute_key(byte[] ct) {
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
		return shift;
	}
	
	public static byte[] decrypt(byte[] ct, int key) throws UnsupportedEncodingException {
		byte[] result = new byte[ct.length];
		for (int i = 0; i < ct.length; i++)
		{
			result[i] = (byte) ((ct[i] - 'A') - key % 26);
			if (result[i] < 0) {
				result[i] += 26;
			}
			result[i] = (byte) (result[i] + 'A');
		}
		return result;
	}
	
	public static byte[] decrypt_exhaust(byte[] ct) throws UnsupportedEncodingException {
		byte[] pt = new byte[ct.length];
		int[] frequency = CryptoTools.getFrequencies(ct);
		int key = exhaustive(ct, frequency);
		pt = decrypt(ct, key);
		return pt;
	}
	
	public static byte[] decrypt_crypta(byte[] ct) throws UnsupportedEncodingException {
		byte[] pt = new byte[ct.length];
		int key = compute_key(ct);
		pt = decrypt(ct, key);
		return pt;
	}

	public static void main(String[] args) throws Exception
	{
		byte[] ct = encrypt("data/MSG1.txt", 19);
		System.out.println(new String(decrypt_crypta(ct)));
		
		
	}

}