package asymmetric;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Random;

public class RSAreal
{

	public static void main(String[] args)
	{
		// Generate the RSA Key Material
		PrintStream out = System.out;
		out.println("--------------------------------Generate RSA Key Material");
		int cert = 20;
		int bit = 1024;
		Random rng = new Random();
		BigInteger one = BigInteger.ONE;
		
		BigInteger p = new BigInteger(bit,cert,rng);
		BigInteger q = new BigInteger(bit,cert,rng);
		// Sanity check
		out.println("--------------------------------Sanity check on p/q");
		if (p.isProbablePrime(cert) && q.isProbablePrime(cert))
			out.println("p/q are good");
		else
			out.println("p/q are not prime !!!");
		//--------------------------------------------
		BigInteger n = p.multiply(q);
		BigInteger phi = p.subtract(one);
		phi = phi.multiply(q.subtract(one));
		
		BigInteger e;
		do
		{
			e = new BigInteger(16, rng);
		} while (!e.gcd(phi).equals(one));		
		// Sanity check
		out.println("--------------------------------Sanity check on e");
		if (e.gcd(phi).equals(one))
			out.println("e is good");
		else
			out.println("e not co-prime with phi");
		//--------------------------------------------
		BigInteger d = e.modInverse(phi);
		
		// Sanity check
		out.println("--------------------------------Sanity check on e/d");
		if (e.multiply(d).mod(phi).equals(one))
			out.println("e/d are reciprocals");
		else
			out.println("e/d are no good !!!");
		//--------------------------------------------
		out.println("--------------------------------Components");
		
		out.println("p = " + p);
		out.println("q = " + q);
		out.println("n = " + n);
		out.println("modulus size = " + n.bitCount());
		out.println("e = " + e);
		out.println("d = " + d);
		out.println("phi = " + phi);
		
		// --------------------------------Use RSA
		out.println("--------------------------------Use RSA");
		String msg = "Hello RSA!";
		out.println("Message: " + msg);
		BigInteger m = new BigInteger(msg.getBytes());
		// Sanity check
		out.println("--------------------------------Sanity check");
		if (m.compareTo(n) < 0)
			out.println("m < n");
		else
			out.println("m too big !!!");
		//--------------------------------------------
		out.println("--------------------------------Decrypt");
		BigInteger ct = m.modPow(e, n);
		out.println("Cyper Text: " + ct);
		BigInteger received = ct.modPow(d, n);
		out.println("Decrypt: " + received);
		out.println("To String: " + new String(received.toByteArray()));

	}

}
