package antiSpamFilter;

/**
 * @author Guilherme Pereira
 */
public class Rule {
	
	private String name;

	/**
	 * This constructor specifies the name of a rule
	 * @param name The name of the rule
	 */
	public Rule(String name) {
		this.name = name;
	}

	
	/**
	 * Method to return the name of the rule
	 * @return String - Name of the rule
	 */
	public String getName() {
		return name;
	}
}