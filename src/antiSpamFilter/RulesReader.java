package antiSpamFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author gspas1-iscteiul
 */
public class RulesReader {

	private LinkedList<Rule> rules = new LinkedList<Rule>();

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
	
	public LinkedList<Rule> getRules() {
		return rules;
	}
}