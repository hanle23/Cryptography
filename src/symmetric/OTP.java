package symmetric;

import java.io.UnsupportedEncodingException;

import util.CryptoTools;

public class OTP
{
	public static void main(String[] args) throws Exception
	{
		byte[] CT1 = CryptoTools.hexToBytes("0A4F0C08003503492F247442105B5757");
		byte[] CT2 = CryptoTools.hexToBytes("5E2769286B507A69494B066252343579");
		byte[] CT3 = CryptoTools.hexToBytes("170708454B1116002A2E2111725F5000");
		byte[] CT1_CT3 = xor(CT1, CT3);
		byte[] pt = xor(CT1_CT3, CT2);
		System.out.println(CryptoTools.byteToString(pt));
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
			result[i] = (byte) ((a[i] ^ b[i]) % 26);
		}
		return result;
	}
}
