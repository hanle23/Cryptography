package foundation;

import util.CryptoTools;

public class Caesar
{

	public static void main(String[] args) throws Exception
	{
		// --------------------------------------------Clean the data
		byte[] raw = CryptoTools.fileToBytes("data/queen.txt");
		for (byte b : raw)	System.out.print((char) b);	System.out.println();
		byte[] pt = CryptoTools.clean(raw);
		for (byte b : pt) 	System.out.print((char) b);	System.out.println();
		CryptoTools.bytesToFile(pt, "data/queen.pt");
		
		// --------------------------------------------Encrypt the data
		int key = 19;
		byte[] ct = new byte[pt.length];
		for (int i = 0; i < pt.length; i++)
		{
			ct[i] = (byte) (((pt[i] - 'A') + key) % 26 + 'A');
		}
		for (byte b : ct)	System.out.print((char) b);	System.out.println();
		CryptoTools.bytesToFile(ct, "data/queen.ct");
		
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
	}

}