package util;
import java.util.Random;
public class Dice {
	//throw n dice, prob of a sum = s
	public static void main(String[] args) {
		Random rng = new Random();
		int n = 7;
		int s = 19;
		int success = 0;
		int max = 100000;
		for (int exp = 1; exp <= max; exp++) {
			int sum = 0;
			for (int dice = 0; dice < n; dice++) {
				sum += rng.nextInt(6) + 1;
			}
			if (sum == s) success++;
			
		}
		System.out.println(success / (double)max);
	}
}
