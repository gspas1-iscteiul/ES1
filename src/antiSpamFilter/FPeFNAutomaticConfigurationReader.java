package antiSpamFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Guilherme Pereira
 */
public class FPeFNAutomaticConfigurationReader {

	private LinkedList<FPeFNSet> FPeFNSets = new LinkedList<>();

	/**
	 * This operation reads the file generated by jMetal with the given name in a specific way
	 * @param name The name of the file to be read 
	 */
	public void readFileWithFPsandFNs(String name) {
		try {
			Scanner scanner = new Scanner (new File(name));
			try{
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] tokens = line.split(" ");
					
					LinkedList<Double> fpefn = new LinkedList<>();
									
					for (int i = 0; i < tokens.length; i++) {
						double d = Double.parseDouble(tokens[i]);
						fpefn.add(d);
					}
					FPeFNSet fnEfp = new FPeFNSet(fpefn.get(0), fpefn.get(1));
					FPeFNSets.add(fnEfp);
				}
			} finally {
				scanner.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("O ficheiro n�o foi encontrado");		
		}
	}
	
	
	/**
	 * Method to return the sets of FP and FN
	 * @return LinkedList - Sets of FP and FN
	 */
	public LinkedList<FPeFNSet> getFPeFNSets() {
		return FPeFNSets;
	}
}