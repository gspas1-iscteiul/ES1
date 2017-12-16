package antiSpamFilter;

import java.util.LinkedList;

/**
 * @author gspas1-iscteiul
 *
 */
public class WeightsSet {

	private LinkedList<Double> weights;

	public WeightsSet(LinkedList<Double> weights) {
		this.setWeights(weights);
	}

	public LinkedList<Double> getWeights() {
		return weights;
	}

	public void setWeights(LinkedList<Double> weights) {
		this.weights = weights;
	}
}