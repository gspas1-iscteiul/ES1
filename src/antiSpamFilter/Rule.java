package antiSpamFilter;

/**
 * @author guilhermepereira
 *
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