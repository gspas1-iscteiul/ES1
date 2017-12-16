package antiSpamFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ManualConfiguration {
	
	private JPanel centralPanel;
	private RulesReader ruleReader;
	private MessagesAndRulesReader spamMessagesReader;
	private MessagesAndRulesReader hamMessagesReader;
	private LinkedList<Rule> rules = new LinkedList<Rule>();
	private LinkedList<Message> spamMessages = new LinkedList<>();
	private LinkedList<Message> hamMessages = new LinkedList<>();
	private JTextField fieldRules;
	private JTextField fieldSpam ;
	private JTextField fieldHam;

	public ManualConfiguration(JPanel centralPanel, JTextField fieldRules, JTextField fieldSpam, JTextField fieldHam) {
		this.centralPanel = centralPanel;
		this.fieldRules = fieldRules;
		this.fieldSpam = fieldSpam;
		this.fieldHam = fieldHam;
	}
	
	/**
	 * This operation constructs the part of the central panel 
	 * related to the Manual Configuration with is labels, fields and tables
	 * and has three methods related to the buttons that are supposed to exist,
	 * each one with different functions
	 */
	public void operationsOfManualConfiguration() {
		JLabel label1 = new JLabel(" Afina��o Manual");
		centralPanel.add(label1);
		
		JLabel labelSpace1 = new JLabel("");
		centralPanel.add(labelSpace1);

		DefaultTableModel model = new DefaultTableModel(2,2); //tamanho apenas tempor�rio, alterado mais abaixo
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

	/**
	 * This operation constructs and operates on a button that causes the file 
	 * uploaded by the user on the supposed field to be read
	 * @param table The table that must be filled with the rules that have been read from the file and with the weight for each rule
	 */
	private void constructAndOperateOnButtonReadRules(JTable table, DefaultTableModel model) {
		JButton buttonReadRules = new JButton("Obter regras");
		buttonReadRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = fieldRules.getText(); //Caminho � C:\Users\Guilherme Pereira\git\ES1-2017-IC1-67\AntiSpamConfigurationForProfessionalMailbox\rules.cf
				
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
	
	/**
	 * This operation causes the files uploaded by the user on the supposed fields 
	 * (fieldSPam and fieldHam) to be read and has two methods to work with the messages
	 * on those files and to calculate the amount of False Negatives and False Positives
	 * on that files
	 * @param table The table that must be filled with the rules that have been read from the file and with the weight for each rule
	 * @param fieldFN The field that must be filled with the amount of False Negatives in the file Spam.log
	 * @param fieldFP The field that must be filled with the amount of False Positives in the file Ham.log
	 */	
	private void constructAndOperateOnButtonEvaluateConfiguration(JTable table, JTextField fieldFN, JTextField fieldFP) {
		JButton buttonEvaluateConfiguration = new JButton("Avaliar Configura��o");
		buttonEvaluateConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//spam.log
				calculationOfFNManual(table, fieldFN);
			    System.out.println("Falsos Negativos calculados com sucesso");
				
				//ham.log
				calculationOfFPManual(table, fieldFP);
			    System.out.println("Falsos Positivos calculados com sucesso");
			}
		});	
		
		centralPanel.add(buttonEvaluateConfiguration);
	}

	/**
	 * This operation causes the file uploaded by the user on the supposed field to be read
	 * @param table The table that must be filled with the rules that have been read from the file and with the weight for each rule
	 */
	private void constructAndOperateOnButtonSaveConfiguration(JTable table) {
		JButton buttonSaveConfiguration = new JButton("Gravar Configura��o");
		buttonSaveConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveConfiguration(table);	
			}
		});
	
		centralPanel.add(buttonSaveConfiguration);
	}
	
	public void saveConfiguration (JTable table) {
		try {
		    BufferedWriter buffWrite = new BufferedWriter(new FileWriter(fieldRules.getText()));
		   
		    for (int i = 0; i < rules.size(); i++) {
		        buffWrite.append(rules.get(i).getName() + " ");
				double d = Double.valueOf(table.getValueAt(i,1).toString());
				buffWrite.append(d + "\n");
			}
		    
		    buffWrite.close();
		    System.out.println("Configura��o gravada com sucesso");
		    
		 } catch(IOException e1) {
			 e1.printStackTrace();
		 }   	
	}

	/**
	 * This operation reads the rules of the messages of the file Spam.log, sums the 
	 * weights of each one of these rules and if this sum is less than 5, increases the
	 * value of the variable numberOfFN and shows that value on the field fieldFN
	 * @param table The table that must be filled with the rules that have been read from the file and with the weight for each rule
	 * @param fieldFN The field that must be filled with the amount of False Negatives in the file Spam.log
	 */
	public void calculationOfFNManual(JTable table, JTextField fieldFN) {
		String pathSpam = fieldSpam.getText(); //Caminho � C:\Users\Guilherme Pereira\git\ES1-2017-IC1-67\spam.log
		
		spamMessagesReader = new MessagesAndRulesReader();
		spamMessagesReader.readFileSpamAndHam(pathSpam);
		
		spamMessages = spamMessagesReader.getMessages();
		
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
						System.out.println();
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
	
	/**
	 * This operation reads the rules of the messages of the file Ham.log, sums the 
	 * weights of each one of these rules and if this sum is bigger than 5, increases the
	 * value of the variable numberOfFP and shows that value on the field fieldFP
	 * @param table The table that must be filled with the rules that have been read from the file and with the weight for each rule
	 * @param fieldFP The field that must be filled with the amount of False Positives in the file Spam.log
	 */
	public void calculationOfFPManual(JTable table, JTextField fieldFP) {
		String pathHam = fieldHam.getText(); //Caminho � C:\Users\Guilherme Pereira\git\ES1-2017-IC1-67\ham.log
		
		hamMessagesReader = new MessagesAndRulesReader();
		hamMessagesReader.readFileSpamAndHam(pathHam);
		
		hamMessages = hamMessagesReader.getMessages();
		
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
}