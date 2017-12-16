package antiSpamFilter;

import java.util.LinkedList;

/**
 * @author gspas1-iscteiul
 *
 */
public class Message {

	private String id;
	private LinkedList<Rule> rules;

	public Message(String id, LinkedList<Rule> rules) {
		this.setID(id);
		this.setRules(rules);
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public LinkedList<Rule> getRules() {
		return rules;
	}

	public void setRules(LinkedList<Rule> rules) {
		this.rules = rules;
	}
}