package STopics;

import java.util.Random;

import util.CryptoTools;

public class Secret {
	public static void main(String[] args) throws Exception
	{
		Secret_splitting();
	}
	public static void Secret_splitting() throws Exception {
		int W = 5;
		int length_bit = 80;
		String bits = "291639075201575653178417";
		byte[] temp = bits.getBytes();
		byte[] secret = new byte[length_bit];
		for (int i = 0; i < temp.length; i++) {
			secret[i] = temp[i];
		}
		Random rng = new Random();
		byte[][] S_k = new byte[W][length_bit];
		for (int i = 0; i < W - 1; i++) {
			int random = rng.nextInt(1000);
			while (random == 0) {
				random = rng.nextInt(1000);
			}
			byte[] random_byte = CryptoTools.convertIntToByteArray(random);
			for (int j = 0; j < random_byte.length; j++) {
				S_k[i][j] = random_byte[j];
			}
		}
		
		S_k[W-1] = S_k[0];
		System.out.println("Share 1: " + CryptoTools.convertByteArrayToInt(S_k[0]));

		for (int i = 1; i < W - 1; i++) {
			S_k[W-1] = CryptoTools.xor(S_k[i - 1], S_k[i]);
			System.out.println("Share " + (i + 1) + ": " + CryptoTools.convertByteArrayToInt(S_k[i]));
		}
		S_k[W-1] = CryptoTools.xor(secret, S_k[W-2]);
		System.out.println("Share " + W + ": " + CryptoTools.convertByteArrayToInt(S_k[W-1]));
		
		//Test
		System.out.print("Testing: ");
		byte[] test = new byte[length_bit];
		for (int i = 1; i < W; i++) {
			test = CryptoTools.xor(S_k[i-1], S_k[i]);
		}
		for (int i = 0; i < length_bit; i++) {
			if (secret[i] != test[i]) {
				System.out.println("Union not Equal");
				return;
			}
		}
		System.out.println("Union is Equal");
		
	}
}
