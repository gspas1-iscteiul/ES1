package antiSpamFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class WeightsAutomaticConfigurationReader {

	private LinkedList<WeightsSet> weightsSets = new LinkedList<>();

	public void readFileWithWeights(String name) {
		try {
			Scanner scanner = new Scanner (new File(name));
			try{
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] tokens = line.split(" ");
					
					LinkedList<Double> weights = new LinkedList<>();
					
					for (int i = 0; i < tokens.length; i++) {
						double d = Double.parseDouble(tokens[i]);
						weights.add(d);
					}
					WeightsSet ws = new WeightsSet(weights);
					weightsSets.add(ws);
				}
			} finally {
				scanner.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("O ficheiro não foi encontrado");		
		}
	}
	
	public LinkedList<WeightsSet> getWeightsSets() {
		return weightsSets;
	}
}