package util;
//Java program Miller-Rabin primality test
import java.math.*;

class GFG {

	public static boolean returnPrime(BigInteger number) {
	    //check via BigInteger.isProbablePrime(certainty)
	    if (!number.isProbablePrime(5)) {
	    	System.out.println("Compare with 5");
	        return false;
	    }
	    	

	    //check if even
	    BigInteger two = new BigInteger("2");
	    if (!two.equals(number) && BigInteger.ZERO.equals(number.mod(two))) {
	    	System.out.println("Check for even");
	    	return false;
	    }
	        

	    //find divisor if any from 3 to 'number'
	    for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(number) < 1; i = i.add(two)) { //start from 3, 5, etc. the odd number, and look for a divisor if any
	        if (BigInteger.ZERO.equals(number.mod(i))) //check if 'i' is divisor of 'number'
	        	System.out.println(i);
	            return false;
	    }
	    return true;
	}
	
	// Driver program
	public static void main(String args[]) {
		String number = "17";
		BigInteger target = new BigInteger(number.getBytes());
		System.out.println("Is prime: " + returnPrime(target));
	}
}


