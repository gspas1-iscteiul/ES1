package antiSpamFilter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JPanel panelBaixo;
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
		construirEAtuarSobrePainelCentro();
		construirEAtuarSobrePainelBaixo();
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
	
	private void construirEAtuarSobrePainelCentro() {
		panelCentro = new JPanel();
		
		globalPanel.add(panelCentro,BorderLayout.CENTER);		
		panelCentro.setLayout(new GridLayout(8,2));
		
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
		construirEAtuarSobreBotaoGravarConfiguracao();
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

	private void construirEAtuarSobreBotaoGravarConfiguracao() {
		JButton buttonGravarConfigura��o = new JButton("Gravar Configura��o");
		buttonGravarConfigura��o.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
	
	
	private void construirEAtuarSobrePainelBaixo() {
		panelBaixo = new JPanel();
		
		globalPanel.add(panelBaixo,BorderLayout.SOUTH);		
		panelBaixo.setLayout(new GridLayout(9,2));
		
		JLabel label1 = new JLabel(" Afina��o Autom�tica");
		panelBaixo.add(label1);

		JLabel labelEspa�o1 = new JLabel("");
		panelBaixo.add(labelEspa�o1);
			
		JLabel labeltemporaria = new JLabel(" Colocar tabela de regras e pesos");
		JLabel labelEspa�o2 = new JLabel("");
		JLabel labelFP = new JLabel(" FP:");
		JLabel labelFN = new JLabel(" FN:");
		JTextField fieldFP = new JTextField();
		JTextField fieldFN = new JTextField();
		JLabel labelEspa�o3 = new JLabel("");
		JLabel labelEspa�o4 = new JLabel("");

		panelBaixo.add(labeltemporaria);
		panelBaixo.add(labelEspa�o2);
		panelBaixo.add(labelFP);
		panelBaixo.add(labelFN);
		panelBaixo.add(fieldFP);
		panelBaixo.add(fieldFN);
		panelBaixo.add(labelEspa�o3);
		panelBaixo.add(labelEspa�o4);
		
		JButton buttonGerarConfigura��oAutom�tica = new JButton("Gerar Configura��o Autom�tica �tima");
		buttonGerarConfigura��oAutom�tica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JButton buttonGravarConfigura��o = new JButton("Gravar Configura��o");
		buttonGravarConfigura��o.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
				
		panelBaixo.add(buttonGerarConfigura��oAutom�tica);
		panelBaixo.add(buttonGravarConfigura��o);
	}
	
	public void open() {
		frame.setVisible(true);
		frame.setSize(550,670);
	}

	public static void main(String[] args) {
		FiltroManual i = new FiltroManual();
		i.open();
	}
}