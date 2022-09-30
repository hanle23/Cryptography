package util;
import java.util.Random;
public class IC {
	//throw n dice, prob of a sum = s
	public static void main(String[] args) {
		Random rng = new Random();
		int success = 0;
		int max = 10000000;
		for (int exp = 1; exp <= max; exp++) {
			double x = rng.nextDouble();
			double y = rng.nextDouble();
			if (x*x + y*y <= 1) success++;
			
		}
		System.out.println(4 * success / (double)max + " pi = " + Math.PI);
	}
}
