package util;
import java.util.Random;
public class Rain {
	//throw n dice, prob of a sum = s
	public static void main(String[] args) throws Exception {
		byte[] pt = CryptoTools.fileToBytes("data/queen.pt");
		Random rng = new Random();
		int success = 0;
		int max = 1000;
		for (int exp = 1; exp <= max; exp++) {
			int pos1 = rng.nextInt(pt.length);
			int pos2 = rng.nextInt(pt.length);
			if (pos1 != pos2 && pt[pos1] == pt[pos2]) success++;
			
		}
		System.out.println(success / (double)max);
	}
}
