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

/**
 * @author guilhermepereira
 *
 */
public class AntiSpamFilterConfiguration {

	private JFrame frame;
	private JPanel globalPanel;
	private JPanel topPanel;
	private JPanel centralPanel;
	private RulesReader ruleReader;
	private MessagesAndRulesReader spamMessagesReader;
	private MessagesAndRulesReader hamMessagesReader;
	private LinkedList<Rule> rules;
	private LinkedList<Message> spamMessages;
	private LinkedList<Message> hamMessages;
	private JTextField fieldRules;
	private JTextField fieldSpam ;
	private JTextField fieldHam;
	private DefaultTableModel model;

	private AntiSpamFilterConfiguration() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		frame.setTitle("Filtro Anti-Spam");
		
		addFrameContent();
	}

	private void addFrameContent() {
		globalPanel = new JPanel();
		globalPanel.setLayout(new BorderLayout());
		frame.add(globalPanel);
		
		constructTopPanel();
		constructAndOperateOnCentralPanel();
	}
	
	/**
	 * This operation constructs the top panel with is labels and fields
	 */
	private void constructTopPanel() {
		topPanel = new JPanel();
		
		globalPanel.add(topPanel,BorderLayout.NORTH);		
		topPanel.setLayout(new GridLayout(5,2));

		JLabel label1 = new JLabel(" Caminho para os ficheiros de configuração:");
		topPanel.add(label1);
		
		JLabel labelSpace1 = new JLabel("");
		topPanel.add(labelSpace1);
		
		fieldRules = new JTextField();
		topPanel.add(fieldRules);
		
		JLabel labelRules = new JLabel(" rules.cf");
		topPanel.add(labelRules);
		
		fieldSpam = new JTextField();
		topPanel.add(fieldSpam);
		
		JLabel labelSpam = new JLabel(" spam.log");
		topPanel.add(labelSpam);
		
		fieldHam = new JTextField();
		topPanel.add(fieldHam);
		
		JLabel labelHam = new JLabel(" ham.log");
		topPanel.add(labelHam);
	}
	
	/**
	 * This operation constructs the top panel with is labels and fields
	 */
	private void constructAndOperateOnCentralPanel() {
		centralPanel = new JPanel();
		
		globalPanel.add(centralPanel,BorderLayout.CENTER);		
		centralPanel.setLayout(new GridLayout(15,2));
		
		manualConfiguration();
		automaticConfiguration();
	}

	private void manualConfiguration() {
		JLabel label1 = new JLabel(" Afinação Manual");
		centralPanel.add(label1);
		
		JLabel labelSpace1 = new JLabel("");
		centralPanel.add(labelSpace1);

		model = new DefaultTableModel(2,2); //tamanho apenas temporário, alterado mais abaixo
		String[] colNames = {"Regras","Pesos"};
		model.setColumnIdentifiers(colNames);

		JTable table = new JTable(model);
		
		JScrollPane scrollArea = new JScrollPane(table);
		centralPanel.add(scrollArea);
				
		JLabel labelSpace2 = new JLabel("");
		JLabel labelFP = new JLabel(" FP:");
		JLabel labelFN = new JLabel(" FN:");
		JTextField fieldFP = new JTextField();
		JTextField fieldFN = new JTextField();
		JLabel labelSpace3 = new JLabel("");
		JLabel labelSpace4 = new JLabel("");
		
		centralPanel.add(labelSpace2);
		centralPanel.add(labelFP);
		centralPanel.add(labelFN);
		centralPanel.add(fieldFP);
		centralPanel.add(fieldFN);
		centralPanel.add(labelSpace3);
		centralPanel.add(labelSpace4);
		
		constructAndOperateOnButtonReadRules(table, model);
		constructAndOperateOnButtonEvaluateConfiguration(table, fieldFN, fieldFP);
		constructAndOperateOnButtonSaveConfiguration(table);
	}

	private void constructAndOperateOnButtonReadRules(JTable table, DefaultTableModel model) {
		JButton buttonReadRules = new JButton("Obter regras");
		buttonReadRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = fieldRules.getText(); //Caminho é C:\Users\Guilherme Pereira\git\ES1-2017-IC1-67\rules.cf
				
				ruleReader = new RulesReader();
				ruleReader.readFileRules(path);
				
				rules = ruleReader.getRules();
				
				model.setNumRows(rules.size());
				
				for (int i = 0; i < rules.size(); i++) {
					table.setValueAt(rules.get(i).getName(), i, 0);
					double d = 0.0;
					table.setValueAt(d, i, 1);
				}
				
			    System.out.println("Regras lidas com sucesso");
			}
		});
		
		centralPanel.add(buttonReadRules);
	}
	
	private void constructAndOperateOnButtonEvaluateConfiguration(JTable table, JTextField fieldFN, JTextField fieldFP) {
		JButton buttonEvaluateConfiguration = new JButton("Avaliar Configuração");
		buttonEvaluateConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//spam.log
				String pathSpam = fieldSpam.getText(); //Caminho é C:\Users\Guilherme Pereira\git\ES1-2017-IC1-67\spam.log

				spamMessagesReader = new MessagesAndRulesReader();
				spamMessagesReader.readFileSpamAndHam(pathSpam);
				
				spamMessages = spamMessagesReader.getMessages();
				
				calculationOfFNManual(table, fieldFN);
			    System.out.println("Falsos Negativos calculados com sucesso");
				
				//ham.log
				String pathHam = fieldHam.getText(); //Caminho é C:\Users\Guilherme Pereira\git\ES1-2017-IC1-67\ham.log
				
				hamMessagesReader = new MessagesAndRulesReader();
				hamMessagesReader.readFileSpamAndHam(pathHam);
				
				hamMessages = hamMessagesReader.getMessages();
				
				calculationOfFPManual(table, fieldFP);
			    System.out.println("Falsos Positivos calculados com sucesso");
			}
		});	
		
		centralPanel.add(buttonEvaluateConfiguration);
	}

	private void constructAndOperateOnButtonSaveConfiguration(JTable table) {
		JButton buttonSaveConfiguration = new JButton("Gravar Configuração");
		buttonSaveConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    try {
			    BufferedWriter buffWrite = new BufferedWriter(new FileWriter(fieldRules.getText()));
			   
			    for (int i = 0; i < rules.size(); i++) {
			        buffWrite.append(rules.get(i).getName() + " ");
					double d = Double.valueOf(table.getValueAt(i,1).toString());
					buffWrite.append(d + "\n");
				}
			    
			    buffWrite.close();
			    System.out.println("Configuração manual gravada com sucesso");
			    
			    } catch(IOException e1) {
			    	e1.printStackTrace();
			    }				
			}
		});
	
		centralPanel.add(buttonSaveConfiguration);
	}
	
	private void calculationOfFNManual(JTable table, JTextField fieldFN) {
		double sumRulesWeight = 0;
		int numberOfFN = 0;
		for (int i = 0; i < spamMessages.size(); i++) {
			LinkedList<Rule> messageRules = spamMessages.get(i).getRules();
			sumRulesWeight = 0;
			for (int j = 0; j < messageRules.size(); j++) {
				String s = messageRules.get(j).getName(); //da-me a regra j da mensagem i do ficheiro Spam

				for (int k = 0; k < rules.size(); k++) {
					if(rules.get(k).getName().equals(s)) {
						double d = Double.valueOf(table.getValueAt(k,1).toString());
						sumRulesWeight = sumRulesWeight + d;
					}
				}
			}
			if (sumRulesWeight < 5) {
				numberOfFN = numberOfFN + 1;
			}
		}
		fieldFN.setText("" + numberOfFN);
	}
	
	private void calculationOfFPManual(JTable table, JTextField fieldFP) {
		double sumRulesWeight = 0;
		int numberOfFP = 0;
		for (int i = 0; i < hamMessages.size(); i++) {
			LinkedList<Rule> messageRules = hamMessages.get(i).getRules();
			sumRulesWeight = 0;
			for (int j = 0; j < messageRules.size(); j++) {
				String s = messageRules.get(j).getName(); //da-me a regra j da mensagem i do ficheiro Ham

				for (int k = 0; k < rules.size(); k++) {
					if(rules.get(k).getName().equals(s)) {
						double d = Double.valueOf(table.getValueAt(k, 1).toString());
						sumRulesWeight = sumRulesWeight + d;
					}
				}
			}
			if (sumRulesWeight > 5) {
				numberOfFP = numberOfFP + 1;
			}
		}
		fieldFP.setText("" + numberOfFP);
	}
	
	private void automaticConfiguration() {
		JLabel labelSpace1 = new JLabel("");
		centralPanel.add(labelSpace1);
		JLabel labelSpace2 = new JLabel("");
		centralPanel.add(labelSpace2);
		JLabel labelSpace3 = new JLabel("");
		centralPanel.add(labelSpace3);
		
		JLabel label1 = new JLabel(" Afinação Automática");
		centralPanel.add(label1);

		JLabel labelSpace4 = new JLabel("");
		centralPanel.add(labelSpace4);

		JTable table2 = new JTable(model);
		
		JScrollPane scrollArea2 = new JScrollPane(table2);
		centralPanel.add(scrollArea2);

		JLabel labelSpace6 = new JLabel("");
		JLabel labelFPAuto = new JLabel(" FP:");
		JLabel labelFNAuto = new JLabel(" FN:");
		JTextField fieldFPAuto = new JTextField();
		JTextField fieldFNAuto = new JTextField();
		JLabel labelSpace7 = new JLabel("");
		JLabel labelSpace8 = new JLabel("");

		centralPanel.add(labelSpace6);
		centralPanel.add(labelFPAuto);
		centralPanel.add(labelFNAuto);
		centralPanel.add(fieldFPAuto);
		centralPanel.add(fieldFNAuto);
		centralPanel.add(labelSpace7);
		centralPanel.add(labelSpace8);
		
		JButton buttonGenerateAutomaticConfiguration = new JButton("Gerar Configuração Automática Ótima");
		buttonGenerateAutomaticConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AntiSpamFilterAutomaticConfiguration a = new AntiSpamFilterAutomaticConfiguration();
				try {
					a.start();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton buttonSaveAutomaticConfiguration = new JButton("Gravar Configuração Automática");
		buttonSaveAutomaticConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
				
		centralPanel.add(buttonGenerateAutomaticConfiguration);
		centralPanel.add(buttonSaveAutomaticConfiguration);		
	}
	
	private void open() {
		frame.setVisible(true);
		frame.setSize(700,980);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AntiSpamFilterConfiguration a = new AntiSpamFilterConfiguration();
		a.open();
	}
}