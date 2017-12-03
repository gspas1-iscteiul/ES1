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

public class Interface {

	private JFrame frame;
	private JPanel globalPanel;
	private JPanel panelCima;
	private JPanel panelCentro;
	private JPanel panelBaixo;
	private LeitorDeRegras leitor;
	private LinkedList<Regra> regras;
	private JTextField fieldRules;
	private JTextField fieldSpam ;
	private JTextField fieldHam;

	public Interface() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		frame.setTitle("Filtro Anti-Spam");
		
		addFrameContent();
	}

	private void addFrameContent() {
		globalPanel = new JPanel();
		globalPanel.setLayout(new BorderLayout());
		frame.add(globalPanel);
		
		construirPainelCima();
		construirPainelCentro();
		construirPainelBaixo();
	}
	
	private void construirPainelCima() {
		panelCima = new JPanel();
		
		globalPanel.add(panelCima,BorderLayout.NORTH);		
		panelCima.setLayout(new GridLayout(5,2));

		JLabel label1 = new JLabel(" Caminho para os ficheiros de configuração:");
		panelCima.add(label1);
		
		JLabel labelEspaço1 = new JLabel("");
		panelCima.add(labelEspaço1);
		
		fieldRules = new JTextField();
		panelCima.add(fieldRules);
		
		JLabel labelRules = new JLabel(" rules.cf");
		panelCima.add(labelRules);
		
		fieldSpam = new JTextField(); //tem de se ler o caminho inserido aqui
		panelCima.add(fieldSpam);
		
		JLabel labelSpam = new JLabel(" spam.log");
		panelCima.add(labelSpam);
		
		fieldHam = new JTextField(); //tem de se ler o caminho inserido aqui
		panelCima.add(fieldHam);
		
		JLabel labelHam = new JLabel(" ham.log");
		panelCima.add(labelHam);
	}
	
	private void construirPainelCentro() {
		panelCentro = new JPanel();
		
		globalPanel.add(panelCentro,BorderLayout.CENTER);		
		panelCentro.setLayout(new GridLayout(8,2));
		
		JLabel label1 = new JLabel(" Afinação Manual");
		panelCentro.add(label1);
		
		JLabel labelEspaço1 = new JLabel("");
		panelCentro.add(labelEspaço1);

		DefaultTableModel model = new DefaultTableModel(2,2); //tamanho apenas temporário
		String[] colNames = {"Regras","Pesos"};
		model.setColumnIdentifiers(colNames);

		JTable table = new JTable(model);
		
		JScrollPane scrollArea = new JScrollPane(table);
		panelCentro.add(scrollArea);
				
		JLabel labelEspaço2 = new JLabel("");
		JLabel labelFP = new JLabel(" FP:");
		JLabel labelFN = new JLabel(" FN:");
		JTextField fieldFP = new JTextField();
		JTextField fieldFN = new JTextField();
		JLabel labelEspaço3 = new JLabel("");
		JLabel labelEspaço4 = new JLabel("");
		
		panelCentro.add(labelEspaço2);
		panelCentro.add(labelFP);
		panelCentro.add(labelFN);
		panelCentro.add(fieldFP);
		panelCentro.add(fieldFN);
		panelCentro.add(labelEspaço3);
		panelCentro.add(labelEspaço4);
		
		JButton buttonGerarConfiguração = new JButton("Ler regras");
		buttonGerarConfiguração.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = fieldRules.getText(); //Caminho é C:\Users\Guilherme Pereira\git\ES1-2017-IC1-67\rules.cf
				
				leitor = new LeitorDeRegras();
				leitor.lerFicheiro(nome);
				
				regras = leitor.getRegras();
				
				model.setNumRows(regras.size());
				
				for (int i = 0; i < regras.size(); i++) {
					table.setValueAt(regras.get(i).getNome(), i, 0);
				}				
			}
		});
				
		JButton buttonAvaliarConfiguração = new JButton("Avaliar Configuração");
		buttonAvaliarConfiguração.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
				
		JButton buttonGravarConfiguração = new JButton("Gravar Configuração");
		buttonGravarConfiguração.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		panelCentro.add(buttonGerarConfiguração);
		panelCentro.add(buttonAvaliarConfiguração);
		panelCentro.add(buttonGravarConfiguração);
	}
	
	private void construirPainelBaixo() {
		panelBaixo = new JPanel();
		
		globalPanel.add(panelBaixo,BorderLayout.SOUTH);		
		panelBaixo.setLayout(new GridLayout(8,2));
		
		JLabel label1 = new JLabel(" Afinação Automática");
		panelBaixo.add(label1);

		JLabel labelEspaço1 = new JLabel("");
		panelBaixo.add(labelEspaço1);
		
//		DefaultTableModel model = new DefaultTableModel(2,2); // alterar o tamanho da tabela
//		String[] colNames = {"Regras","Pesos"};
//		model.setColumnIdentifiers(colNames);
//		
//		JTable table = new JTable(model);
//		
//		JScrollPane scrollArea= new JScrollPane(table);
//		panelBaixo.add(scrollArea);
		
		JLabel labeltemporaria = new JLabel(" Colocar tabela de regras e pesos");
		JLabel labelEspaço2 = new JLabel("");
		JLabel labelFP = new JLabel(" FP:");
		JLabel labelFN = new JLabel(" FN:");
		JTextField fieldFP = new JTextField();
		JTextField fieldFN = new JTextField();
		JLabel labelEspaço3 = new JLabel("");
		JLabel labelEspaço4 = new JLabel("");

		panelBaixo.add(labeltemporaria);
		panelBaixo.add(labelEspaço2);
		panelBaixo.add(labelFP);
		panelBaixo.add(labelFN);
		panelBaixo.add(fieldFP);
		panelBaixo.add(fieldFN);
		panelBaixo.add(labelEspaço3);
		panelBaixo.add(labelEspaço4);
		
		JButton buttonGerarConfiguraçãoAutomática = new JButton("Gerar Configuração Automática Ótima");
		buttonGerarConfiguraçãoAutomática.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JButton buttonGravarConfiguração = new JButton("Gravar Configuração");
		buttonGravarConfiguração.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
				
		panelBaixo.add(buttonGerarConfiguraçãoAutomática);
		panelBaixo.add(buttonGravarConfiguração);
	}

	public void open() {
		frame.setVisible(true);
		frame.setSize(550,670);
	}

	public static void main(String[] args) {
		Interface i = new Interface();
		i.open();
	}
}