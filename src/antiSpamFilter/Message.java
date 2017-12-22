package antiSpamFilter;

import java.util.LinkedList;

/**
 * @author Guilherme Pereira
 */
public class Message {

	private String id;
	private LinkedList<Rule> rules;

	public Message(String id, LinkedList<Rule> rules) {
		this.id = id;
		this.rules = rules;
	}

	
	/**
	 * Method to return the id of the message
	 * @return String - Id of the message
	 */
	public String getID() {
		return id;
	}

	
	/**
	 * Method to return the rules of the message
	 * @return LinkedList - Rules of the message
	 */
	public LinkedList<Rule> getRules() {
		return rules;
	}
}