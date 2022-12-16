package hash;

import java.math.BigInteger;
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
	
	public static void signature() throws Exception {
		byte[] msg = "Meet me at 5 pm tomorrow".getBytes();
		BigInteger n = new BigInteger("94587468335128982981605019776781234618384857805657005686084562260910788622013722070926491690843853690071248130134427832324966728582532832363221542231787068203763027067400082835394459857525017707284768411819006776211493735326500782954621660256501187035611332577696332459049538105669711385995976912007767106063");
		BigInteger e = new BigInteger("74327");
		BigInteger d = new BigInteger("7289370196881601766768920490284861650464951706793000236386405648425161747775298344104658393385359209126267833888223695609366844098655240542152017354442883676634193191857568369042999854440242050353181703706753485749165295123694487676952198090537385200990850805837963871485320168470788328336240930212290450023");
		String algo = "SHA-512";
		MessageDigest md = MessageDigest.getInstance(algo);
		byte[] digest = md.digest(msg);
		BigInteger digested = new BigInteger(digest);
		BigInteger ct = digested.modPow(d, n);
		
		BigInteger pt = ct;
		pt = pt.modPow(e, n);
		byte[] pt_byte = pt.toByteArray();
		byte[] confirm_byte = md.digest(msg);
		System.out.println(pt_byte.length==confirm_byte.length);
		System.out.println(pt_byte.getClass() == confirm_byte.getClass());
		for (int i = 0; i < pt_byte.length; i++) {
			if (pt_byte[i] != confirm_byte[i]) {
				System.out.println("Not Equal");
				return;
			}
		}
		System.out.println("true");
	}
	
	public static void signature_2() throws Exception {
		BigInteger nA = new BigInteger("171024704183616109700818066925197841516671277");
		BigInteger eA = new BigInteger("1571");
		
		BigInteger pB = new BigInteger("98763457697834568934613");
		BigInteger qB = new BigInteger("8495789457893457345793");
		BigInteger eB = new BigInteger("87697");
		BigInteger nB = pB.multiply(qB);
		
		BigInteger m = new BigInteger("418726553997094258577980055061305150940547956");
		BigInteger s = new BigInteger("749142649641548101520133634736865752883277237");
		
		BigInteger phiB = pB.subtract(BigInteger.ONE).multiply(qB.subtract(BigInteger.ONE));
		BigInteger dB = eB.modInverse(phiB);
		
		BigInteger pt = m.modPow(dB, nB);
		BigInteger check = s.modPow(dB, nB);
		check = check.modPow(eA, nA);
		System.out.println(pt.equals(check));
		System.out.println(new String(pt.toByteArray()));
	}
	public static void main(String[] args) throws Exception
	{
		signature_2();
	}
}

