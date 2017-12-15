package antiSpamFilter;

public class FPeFNSet {

	private double FN;
	private double FP;

	public FPeFNSet(double FN, double FP) {
		setFN(FN);
		setFP(FP);
	}

	public double getFN() {
		return FN;
	}

	public void setFN(double FN) {
		this.FN = FN;
	}

	public double getFP() {
		return FP;
	}

	public void setFP(double FP) {
		this.FP = FP;
	}
}