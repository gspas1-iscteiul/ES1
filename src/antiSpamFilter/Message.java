package antiSpamFilter;

import java.util.LinkedList;

/**
 * @author Guilherme Pereira
 */
public class Message {

	private String id;
	private LinkedList<Rule> rules;

	/**
	 * This constructor specifies the id and the rules of a message
	 * @param id The id of the message
	 * @param rules The rules of the message
	 */
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