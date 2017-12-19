package antiSpamFilter;

/**
 * @author gspas1-iscteiul
 */
public class FPeFNSet {

	private double FN;
	private double FP;

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