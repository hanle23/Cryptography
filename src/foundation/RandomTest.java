package foundation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import util.CryptoTools;

public class RandomTest {
	public static void main(String[] args) throws Exception {
		List<Double> bag = new ArrayList<Double>();
		for (int i = 0; i < 10; i ++) {
			for (int j = 0; j < CryptoTools.ENGLISH.length; j++) {
				bag.add(CryptoTools.ENGLISH[j]);
			}
			
		}
		Random rng = new Random();
		int success = 0;
		int max = 10000;
		for (int exp = 1; exp <= max; exp++) {
			int pos1 = rng.nextInt(bag.size());
			int pos2 = rng.nextInt(bag.size());
			if (pos1 != pos2 && bag.get(pos1).equals(bag.get(pos2))) success++;
			System.out.println("pos1: "  + bag.get(pos1));
			System.out.println("pos2: "  + bag.get(pos2));
			System.out.println("exp: " + exp + " Success:" + success);
		}
		System.out.println(success / (double)max);
	}
}
