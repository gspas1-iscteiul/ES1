package antiSpamFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class LeitorDeRegras {

	private LinkedList<Regra> regras = new LinkedList<Regra>();

	public void lerFicheiro(String nome) {
		try {
			Scanner scanner = new Scanner (new File(nome));
			try{
				while (scanner.hasNextLine()) {
					String linha = scanner.nextLine();
					Regra r = new Regra(linha);
					regras.add(r);
				}
			} finally {
				scanner.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("O ficheiro não foi encontrado");		
		}
	}
	
	public LinkedList<Regra> getRegras() {
		return regras;
	}
}