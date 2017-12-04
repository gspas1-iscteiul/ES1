package antiSpamFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class LeitorDeMensagensERegras {

	private LinkedList<Mensagem> mensagens = new LinkedList<Mensagem>();

	public void lerFicheiro(String nome) {
		try {
			Scanner scanner = new Scanner (new File(nome));
			try{
				while (scanner.hasNextLine()) {
					String linha = scanner.nextLine();
					String[] tokens = linha.split("	");
					
					LinkedList<Regra> regras = new LinkedList<Regra>();

					for (int i = 1; i < tokens.length; i++) {
						Regra r = new Regra(tokens[i]);
						regras.add(r);
					}
					
					Mensagem m = new Mensagem(tokens[0], regras);
					mensagens.add(m);
				}
			} finally {
				scanner.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("O ficheiro não foi encontrado");		
		}
	}
	
	public LinkedList<Mensagem> getMensagens() {
		return mensagens;
	}
}