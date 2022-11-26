package asymmetric;

import java.io.PrintStream;
import java.math.BigInteger;

public class RSA
{

	public static void main(String[] args)
	{
		// Generate the RSA Key Material
		PrintStream out = System.out;
		out.println("--------------------------------Generate RSA Key Material");
		int cert = 20;
		BigInteger one = BigInteger.ONE;
		
		BigInteger p = new BigInteger("264208679307705732524907225971531207681");
		BigInteger q = new BigInteger("200181170185227101268806368199715987557");
		// Sanity check
		out.println("--------------------------------Sanity check on p/q");
		if (p.isProbablePrime(cert) && q.isProbablePrime(cert))
			out.println("p/q are good");
		else
			out.println("p/q are not prime !!!");
		//--------------------------------------------
//		BigInteger n = p.multiply(q);
		BigInteger n = new BigInteger("130918180062915485900954597631313629864836201978227133319187624210227148776154322707071245742980686741118030867148438370439660292136998104360860705032542533349698053368186836516200888554212669145740474501485880521654642182193789294300080988945147087020219803028448157467640485725166289781136027695109151280023");
		BigInteger phi = p.subtract(one);
		phi = phi.multiply(q.subtract(one));
		
		BigInteger e = new BigInteger("421");
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
		out.println("e public = " + e);
		out.println("d = " + d);
		out.println("phi = " + phi);
		
		// --------------------------------Use RSA
		out.println("--------------------------------Use RSA");
		String msg = "H";
		out.println("Message: " + msg);
		BigInteger m = new BigInteger(msg.getBytes());
		// Sanity check
		out.println("--------------------------------Sanity check on m");
		if (m.compareTo(n) < 0)
			out.println("m is < n");
		else
			out.println("m too big !!!");
		//--------------------------------------------
		out.println("--------------------------------Decrypt");
//		BigInteger ct = m.modPow(e, n);
		BigInteger ct = new BigInteger("46903772711485649870400600542340635647113782148471559341585401119110429267342");
		out.println("Cyper Text: " + ct);
		BigInteger received = ct.modPow(d, n);
		out.println("Decrypt: " + received);
		out.println("To String: " + new String(received.toByteArray()));
		
		
		
		
	}

}
