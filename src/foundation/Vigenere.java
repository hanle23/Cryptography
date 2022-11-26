package foundation;

import util.CryptoTools;

public class Vigenere {
	
    public static byte[] decrypt(byte[] text, final String key)

    {
    	byte[] res = new byte[text.length];
        for (int i = 0, j = 0; i < text.length; i++)
        {
            res[i] = (byte) ((text[i] - key.charAt(j) + 26) % 26 + 'A');
            j = ++j % key.length();
        }

        return res;

    }
	public static void main(String[] args) throws Exception {
		byte[] ct = CryptoTools.fileToBytes("data/MSG4.ct");
		String key = "A";

		for (int i = 0; i < 15; i++) {
			byte[] result = decrypt(ct, key);
			int[] frq = CryptoTools.getFrequencies(result);
			double dot = 0;
			for (int j = 0; j < 26; j++){
				dot += (frq[j] / (double) ct.length) * CryptoTools.ENGLISH[j];
			}
			//System.out.println("Candidate key key = " + k + "==> Dot = " + dot);
			System.out.println(dot);
			key += "A";
		}

			
		
	}
}
