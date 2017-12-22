package antiSpamFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Guilherme Pereira
 */
public class MessagesAndRulesReader {

	private LinkedList<Message> messages = new LinkedList<Message>();

	/**
	 * This operation reads the file with the name 'name' in a specific way
	 * @param name The name of the file to be read 
	 */
	public void readFileSpamAndHam(String name) {
		try {
			Scanner scanner = new Scanner (new File(name));
			try{
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] tokens = line.split("	");
					
					LinkedList<Rule> rules = new LinkedList<Rule>();

					for (int i = 1; i < tokens.length; i++) {
						Rule r = new Rule(tokens[i]);
						rules.add(r);
					}
					
					Message m = new Message(tokens[0], rules);
					messages.add(m);
				}
			} finally {
				scanner.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("O ficheiro não foi encontrado");		
		}
	}
	
	
	/**
	 * Method to return the messages of the file
	 * @return LinkedList - Messages of the file
	 */
	public LinkedList<Message> getMessages() {
		return messages;
	}
}