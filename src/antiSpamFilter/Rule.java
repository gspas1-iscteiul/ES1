package antiSpamFilter;

/**
 * @author gspas1-iscteiul
 */
public class Rule {
	
	private String name;

	public Rule(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}