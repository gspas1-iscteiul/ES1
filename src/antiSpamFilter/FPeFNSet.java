package antiSpamFilter;

/**
 * @author Guilherme Pereira
 */
public class FPeFNSet {

	private double FN;
	private double FP;

	
	/**
	 * This constructor receives the number of False Negatives and the number of False Positives
	 * @param FN The number of False Negatives
	 * @param FP The number of False Positives
	 */
	public FPeFNSet(double FN, double FP) {
		this.FN = FN;
		this.FP = FP;
	}

	
	/**
	 * Method to return the number of False Negatives
	 * @return Double - Number of False Negatives
	 */
	public double getFN() {
		return FN;
	}

	
	/**
	 * Method to return the number of False Positives
	 * @return Double - Number of False Positives
	 */
	public double getFP() {
		return FP;
	}
}