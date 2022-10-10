package foundation;
import java.math.BigInteger;

public class Inverse
{
	// find the mod inverse of x in the world of n
	public static void main(String[] args)
	{
		BigInteger n = new BigInteger(args[0]);
		BigInteger x = new BigInteger(args[1]);
		System.out.println(x.modInverse(n));

	}

}
