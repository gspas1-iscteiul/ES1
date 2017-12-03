package antiSpamFilter;

public class Regra {
	
	private String nome;

	public Regra(String linha) {
		this.setNome(linha);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}