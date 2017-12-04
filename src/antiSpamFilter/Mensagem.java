package antiSpamFilter;

import java.util.LinkedList;

public class Mensagem {

	private String id;
	private LinkedList<Regra> regras;

	public Mensagem(String id, LinkedList<Regra> regras) {
		this.setID(id);
		this.setRegras(regras);
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public LinkedList<Regra> getRegras() {
		return regras;
	}

	public void setRegras(LinkedList<Regra> regras) {
		this.regras = regras;
	}
	
}