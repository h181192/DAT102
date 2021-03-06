package no.hib.dat102;

public class Tallfoelge {
	public static void main(String[] args) {
		for (int n = 0; n < 10; n++) {
			System.out.println("nummer " + n + " : " + sum(n));
		}
	}

	/**
	 * Metoden er ikke gyldig for n < 0
	 * @param n
	 * @return
	 */
	public static int sum(int n) {
		if (n == 0) {
			return 2;
		} else if (n == 1) {
			return 5;
		} else {
			return ((5 * sum(n - 1)) - (6 * sum(n - 2)) + 2);
		}
	}
}
