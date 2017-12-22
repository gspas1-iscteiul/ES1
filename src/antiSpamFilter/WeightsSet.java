package antiSpamFilter;

import java.util.LinkedList;

/**
 * @author Guilherme Pereira
 */
public class WeightsSet {

	private LinkedList<Double> weights;

	public WeightsSet(LinkedList<Double> weights) {
		this.weights = weights;
	}

	public LinkedList<Double> getWeights() {
		return weights;
	}
}