package antiSpamFilter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class FiltroManual {

	private JFrame frame;
	private JPanel globalPanel;
	private JPanel panelCima;
	private JPanel panelCentro;
	private LeitorDeRegras leitorRegras;
	private LeitorDeMensagensERegras leitorMensagensSpam;
	private LeitorDeMensagensERegras leitorMensagensHam;
	private LinkedList<Regra> regras;
	private LinkedList<Mensagem> mensagensSpam;
	private LinkedList<Mensagem> mensagensHam;
	private JTextField fieldRules;
	private JTextField fieldSpam ;
	private JTextField fieldHam;

	public FiltroManual() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		frame.setTitle("Filtro Anti-Spam");
		
		addFrameContent();
	}

	private void addFrameContent() {
		globalPanel = new JPanel();
		globalPanel.setLayout(new BorderLayout());
		frame.add(globalPanel);
		
		construirEAtuarSobrePainelCima();
		construirEAtuarSobrePainelCentroEBaixo();
//		construirEAtuarSobrePainelBaixo();
	}
	
	private void construirEAtuarSobrePainelCima() {
		panelCima = new JPanel();
		
		globalPanel.add(panelCima,BorderLayout.NORTH);		
		panelCima.setLayout(new GridLayout(5,2));

		JLabel label1 = new JLabel(" Caminho para os ficheiros de configura��o:");
		panelCima.add(label1);
		
		JLabel labelEspa�o1 = new JLabel("");
		panelCima.add(labelEspa�o1);
		
		fieldRules = new JTextField();
		panelCima.add(fieldRules);
		
		JLabel labelRules = new JLabel(" rules.cf");
		panelCima.add(labelRules);
		
		fieldSpam = new JTextField();
		panelCima.add(fieldSpam);
		
		JLabel labelSpam = new JLabel(" spam.log");
		panelCima.add(labelSpam);
		
		fieldHam = new JTextField();
		panelCima.add(fieldHam);
		
		JLabel labelHam = new JLabel(" ham.log");
		panelCima.add(labelHam);
	}
	
	private void construirEAtuarSobrePainelCentroEBaixo() {
		panelCentro = new JPanel();
		
		globalPanel.add(panelCentro,BorderLayout.CENTER);		
		panelCentro.setLayout(new GridLayout(15,2));
		
		afinacaoManual();
		afinacaoAutomatica();
	}

	private void afinacaoManual() {
		JLabel label1 = new JLabel(" Afina��o Manual");
		panelCentro.add(label1);
		
		JLabel labelEspa�o1 = new JLabel("");
		panelCentro.add(labelEspa�o1);

		DefaultTableModel model = new DefaultTableModel(2,2); //tamanho apenas tempor�rio, alterado mais abaixo
		String[] colNames = {"Regras","Pesos"};
		model.setColumnIdentifiers(colNames);

		JTable table = new JTable(model);
		
		JScrollPane scrollArea = new JScrollPane(table);
		panelCentro.add(scrollArea);
				
		JLabel labelEspa�o2 = new JLabel("");
		JLabel labelFP = new JLabel(" FP:");
		JLabel labelFN = new JLabel(" FN:");
		JTextField fieldFP = new JTextField();
		JTextField fieldFN = new JTextField();
		JLabel labelEspa�o3 = new JLabel("");
		JLabel labelEspa�o4 = new JLabel("");
		
		panelCentro.add(labelEspa�o2);
		panelCentro.add(labelFP);
		panelCentro.add(labelFN);
		panelCentro.add(fieldFP);
		panelCentro.add(fieldFN);
		panelCentro.add(labelEspa�o3);
		panelCentro.add(labelEspa�o4);
		
		construirEAtuarSobreBotaoObterRegras(table, model);
		construirEAtuarSobreBotaoAvaliarConfiguracao(table, fieldFN, fieldFP);
		construirEAtuarSobreBotaoGravarConfiguracao(table);
	}

	private void construirEAtuarSobreBotaoObterRegras(JTable table, DefaultTableModel model) {
		JButton buttonObterRegras = new JButton("Obter regras");
		buttonObterRegras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String caminho = fieldRules.getText(); //Caminho � C:\Users\Guilherme Pereira\git\ES1-2017-IC1-67\rules.cf
				
				leitorRegras = new LeitorDeRegras();
				leitorRegras.lerFicheiro(caminho);
				
				regras = leitorRegras.getRegras();
				
				model.setNumRows(regras.size());
				
				for (int i = 0; i < regras.size(); i++) {
					table.setValueAt(regras.get(i).getNome(), i, 0);
					double d = 0.0;
					table.setValueAt(d, i, 1);
				}			
			}
		});
		
		panelCentro.add(buttonObterRegras);
	}
	
	private void construirEAtuarSobreBotaoAvaliarConfiguracao(JTable table, JTextField fieldFN, JTextField fieldFP) {
		JButton buttonAvaliarConfigura��o = new JButton("Avaliar Configura��o");
		buttonAvaliarConfigura��o.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//spam.log
				String caminhoSpam = fieldSpam.getText(); //Caminho � C:\Users\Guilherme Pereira\git\ES1-2017-IC1-67\spam.log.txt

				leitorMensagensSpam = new LeitorDeMensagensERegras();
				leitorMensagensSpam.lerFicheiro(caminhoSpam);
				
				mensagensSpam = leitorMensagensSpam.getMensagens();
				
				calculoFNManual(table, fieldFN);
				
				//ham.log
				String caminhoHam = fieldHam.getText(); //Caminho � C:\Users\Guilherme Pereira\git\ES1-2017-IC1-67\ham.log.txt
				
				leitorMensagensHam = new LeitorDeMensagensERegras();
				leitorMensagensHam.lerFicheiro(caminhoHam);
				
				mensagensHam = leitorMensagensHam.getMensagens();
				
				calculoFPManual(table, fieldFP);
			}
		});	
		
		panelCentro.add(buttonAvaliarConfigura��o);
	}

	private void construirEAtuarSobreBotaoGravarConfiguracao(JTable table) {
		JButton buttonGravarConfigura��o = new JButton("Gravar Configura��o");
		buttonGravarConfigura��o.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    try {
			    BufferedWriter buffWrite = new BufferedWriter(new FileWriter("C://Users//Guilherme Pereira//git//ES1-2017-IC1-67//rules.cf"));
			    for (int i = 0; i < regras.size(); i++) {
			        buffWrite.append(regras.get(i).getNome() + " ");
					double d = Double.valueOf(table.getValueAt(i,1).toString());
					buffWrite.append(d + "\n");
				}
			    buffWrite.close();
			    System.out.println("Configura��o manual gravada com sucesso");
			    } catch(IOException e1) {
			    	e1.printStackTrace();
			    }				
			}
		});
	
		panelCentro.add(buttonGravarConfigura��o);
	}
	
	private void calculoFNManual(JTable table, JTextField fieldFN) {
		double somaPesosRegras = 0;
		int numeroFN = 0;
		for (int i = 0; i < mensagensSpam.size(); i++) {
			LinkedList<Regra> regrasMensagem = mensagensSpam.get(i).getRegras();
			somaPesosRegras = 0;
			for (int j = 0; j < regrasMensagem.size(); j++) {
				String s = regrasMensagem.get(j).getNome(); //da-me a regra j da mensagem i do ficheiro Spam

				for (int k = 0; k < regras.size(); k++) {
					if(regras.get(k).getNome().equals(s)) {
						double d = Double.valueOf(table.getValueAt(k,1).toString());
						somaPesosRegras = somaPesosRegras + d;
					}
				}
			}
			if (somaPesosRegras < 5) {
				numeroFN = numeroFN + 1;
			}
		}
		fieldFN.setText("" + numeroFN);
	}
	
	private void calculoFPManual(JTable table, JTextField fieldFP) {
		double somaPesosRegras = 0;
		int numeroFP = 0;
		for (int i = 0; i < mensagensHam.size(); i++) {
			LinkedList<Regra> regrasMensagem = mensagensHam.get(i).getRegras();
			somaPesosRegras = 0;
			for (int j = 0; j < regrasMensagem.size(); j++) {
				String s = regrasMensagem.get(j).getNome(); //da-me a regra j da mensagem i do ficheiro Ham

				for (int k = 0; k < regras.size(); k++) {
					if(regras.get(k).getNome().equals(s)) {
						double d = Double.valueOf(table.getValueAt(k, 1).toString());
						somaPesosRegras = somaPesosRegras + d;
					}
				}
			}
			if (somaPesosRegras > 5) {
				numeroFP = numeroFP + 1;
			}
		}
		fieldFP.setText("" + numeroFP);
	}
	
	private void afinacaoAutomatica() {
		JLabel labelEspa�o1 = new JLabel("");
		panelCentro.add(labelEspa�o1);
		JLabel labelEspa�o2 = new JLabel("");
		panelCentro.add(labelEspa�o2);
		JLabel labelEspa�o3 = new JLabel("");
		panelCentro.add(labelEspa�o3);
		
		JLabel label1 = new JLabel(" Afina��o Autom�tica");
		panelCentro.add(label1);

		JLabel labelEspa�o4 = new JLabel("");
		panelCentro.add(labelEspa�o4);
				
		DefaultTableModel model2 = new DefaultTableModel(2,2); //tamanho apenas tempor�rio, alterado mais abaixo
		String[] colNames = {"Regras","Pesos"};
		model2.setColumnIdentifiers(colNames);

		JTable table2 = new JTable(model2);
		
		JScrollPane scrollArea2 = new JScrollPane(table2);
		panelCentro.add(scrollArea2);

		JLabel labelEspa�o6 = new JLabel("");
		JLabel labelFPAuto = new JLabel(" FP:");
		JLabel labelFNAuto = new JLabel(" FN:");
		JTextField fieldFPAuto = new JTextField();
		JTextField fieldFNAuto = new JTextField();
		JLabel labelEspa�o7 = new JLabel("");
		JLabel labelEspa�o8 = new JLabel("");

		panelCentro.add(labelEspa�o6);
		panelCentro.add(labelFPAuto);
		panelCentro.add(labelFNAuto);
		panelCentro.add(fieldFPAuto);
		panelCentro.add(fieldFNAuto);
		panelCentro.add(labelEspa�o7);
		panelCentro.add(labelEspa�o8);
		
		JButton buttonGerarConfigura��oAutom�tica = new JButton("Gerar Configura��o Autom�tica �tima");
		buttonGerarConfigura��oAutom�tica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JButton buttonGravarConfigura��oAutom�tica = new JButton("Gravar Configura��o Autom�tica");
		buttonGravarConfigura��oAutom�tica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
				
		panelCentro.add(buttonGerarConfigura��oAutom�tica);
		panelCentro.add(buttonGravarConfigura��oAutom�tica);		
	}
	
	public void open() {
		frame.setVisible(true);
		frame.setSize(700,980);
	}

	public static void main(String[] args) {
		FiltroManual i = new FiltroManual();
		i.open();
	}
}