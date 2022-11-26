package symmetric;

import util.CryptoTools;

public class OTP
{
	public static void main(String[] args)
	{
		byte[] ky = "JABHXPVOLLCIJ".getBytes();
		
		// Alice:
		byte[] pt = "SENDMOREMONEY".getBytes();
		byte[] ct = classic_xor(pt, ky);
		System.out.println("CT: " + CryptoTools.bytesToHex(ct));
		
		// Bob:
		byte[] bk = classic_xor(ct, ky);
		System.out.println("BACK: " + new String(bk));
	}
	
	private static byte[] xor(byte[] a, byte[] b)
	{
		if (a.length != b.length) throw new RuntimeException("Diff sizes!!");
		byte[] result = new byte[a.length];
		for (int i = 0; i < a.length; i++) result[i] = (byte) (a[i] ^ b[i]);
		return result;
	}
	
	private static byte[] classic_xor(byte[] a, byte[] b) {
		if (a.length != b.length) throw new RuntimeException("Diff sizes!!");
		byte[] result = new byte[a.length];
		for (int i = 0; i < a.length; i++) 
		{
			result[i] = (byte) ((a[i] + b[i]) % 26);
		}
		return result;
	}
}
