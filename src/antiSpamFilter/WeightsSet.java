package antiSpamFilter;

import java.util.LinkedList;

/**
 * @author Guilherme Pereira
 */
public class WeightsSet {

	private LinkedList<Double> weights;

	/**
	 * This constructor specifies the weights of a set of weights
	 * @param weights The weights of the set
	 */
	public WeightsSet(LinkedList<Double> weights) {
		this.weights = weights;
	}

	
	/**
	 * Method to return the weights of the set
	 * @return LinkedList - The weights of the set
	 */
	public LinkedList<Double> getWeights() {
		return weights;
	}
}