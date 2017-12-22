package antiSpamFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Guilherme Pereira
 */
public class RulesReader {

	private LinkedList<Rule> rules = new LinkedList<Rule>();

	/**
	 * This operation reads the file with the given name in a specific way
	 * @param name The name of the file to be read 
	 */
	public void readFileRules(String name) {
		try {
			Scanner scanner = new Scanner (new File(name));
			try{
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					Rule r = new Rule(line);
					rules.add(r);
				}
			} finally {
				scanner.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("O ficheiro não foi encontrado");		
		}
	}
	
	
	/**
	 * Method to return the rules of a file
	 * @return LinkedList - Rules of a file
	 */
	public LinkedList<Rule> getRules() {
		return rules;
	}
}