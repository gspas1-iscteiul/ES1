package antiSpamFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AutomaticConfiguration {
	
	private ManualConfiguration manualConfiguration;
	private JPanel centralPanel;
	private RulesReader ruleReader = new RulesReader();
	private LinkedList<Rule> rules = new LinkedList<Rule>();

	public AutomaticConfiguration(JPanel centralPanel, ManualConfiguration manualConfiguration) {
		this.centralPanel = centralPanel;
		this.manualConfiguration = manualConfiguration;
	}
	
	
	/**
	 * This operation constructs the part of the central panel related 
	 * to the Automatic configuration with is labels, fields, tables and buttons
	 */
	public void operationsOfAutomaticConfiguration() {
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

		DefaultTableModel model = new DefaultTableModel(2,2); //tamanho apenas temporário, alterado mais abaixo
		String[] colNames = {"Regras","Pesos"};
		model.setColumnIdentifiers(colNames);
		
		JTable table = new JTable(model);
		
		JScrollPane scrollArea = new JScrollPane(table);
		centralPanel.add(scrollArea);
		
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
		
		ruleReader.readFileRules("C://Users//Guilherme Pereira//git//ES1-2017-IC1-67//AntiSpamConfigurationForProfessionalMailbox//rules.cf");
		
		rules = ruleReader.getRules();
		model.setNumRows(rules.size());
		
		constructAndOperateOnButtonGenerateAutomaticConfiguration(table, model, fieldFPAuto, fieldFNAuto);
	
		JButton buttonCalculateNewFPeFN = new JButton("Calcular novos FP e FN após alterações nos pesos");
		buttonCalculateNewFPeFN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // ACHO que por numeros serem mt pequenos não calcula bem
				manualConfiguration.calculationOfFNManual(table, fieldFNAuto);
				manualConfiguration.calculationOfFPManual(table, fieldFPAuto);
			}
		});
		
		centralPanel.add(buttonCalculateNewFPeFN);
		
		JButton buttonSaveAutomaticConfiguration = new JButton("Gravar Configuração Automática");
		buttonSaveAutomaticConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manualConfiguration.saveConfiguration(table);
			}
		});
		
		centralPanel.add(buttonSaveAutomaticConfiguration);
	}
	
	//meter javadocs
	private void constructAndOperateOnButtonGenerateAutomaticConfiguration(JTable table, DefaultTableModel model, JTextField fieldFPAuto, JTextField fieldFNAuto) {
		JButton buttonGenerateAutomaticConfiguration = new JButton("Gerar Configuração Automática Ótima");
		buttonGenerateAutomaticConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//para meter regras na tabela
				for (int i = 0; i < rules.size(); i++) {
					table.setValueAt(rules.get(i).getName(), i, 0);
					table.setValueAt(0.0, i, 1);
				}
				
//				AntiSpamFilterAutomaticConfiguration a = new AntiSpamFilterAutomaticConfiguration();
//				try {
//					a.start();
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//				
//				System.out.println("JÁ ESTÁ");
				
				//Ler ficheiro de FP e FN gerado e ver linha com menor FP
				
				FPeFNAutomaticConfigurationReader FPeFNReader = new FPeFNAutomaticConfigurationReader();			
				FPeFNReader.readFileWithFPsandFNs("C://Users//Guilherme Pereira//git//ES1-2017-IC1-67//experimentBaseDirectory//referenceFronts//AntiSpamFilterProblem.NSGAII.rf");
				
				LinkedList<FPeFNSet> fpEfnSets = FPeFNReader.getFPeFNSets();
				
				int indexOfSmallestFP = 0;
				for (int i = 1; i < fpEfnSets.size(); i++) {
					double fp = fpEfnSets.get(i).getFP();
					
					if (fp < fpEfnSets.get(indexOfSmallestFP).getFP()){
						indexOfSmallestFP = i;
					}
				}
				
				//Ler ficheiro de pesos gerado e colocar o vetor de pesos da linha com menor FP na interface
				WeightsAutomaticConfigurationReader weightsReader = new WeightsAutomaticConfigurationReader();
				weightsReader.readFileWithWeights("C://Users//Guilherme Pereira//git//ES1-2017-IC1-67//experimentBaseDirectory//referenceFronts//AntiSpamFilterProblem.NSGAII.rs");
				
				LinkedList<WeightsSet> weightsSet = weightsReader.getWeightsSets();

				for (int i = 0; i < weightsSet.size(); i++) {
					if (i == indexOfSmallestFP) {
						LinkedList<Double> weights = weightsSet.get(i).getWeights();
						for (int j = 0; j < weights.size(); j++) {
							table.setValueAt(weights.get(j), j, 1);
						}						
					}					
				}
				
				//Colocar FN e FP na interface
				for (int i = 0; i < fpEfnSets.size(); i++) {
					if (i == indexOfSmallestFP) {
						fieldFPAuto.setText("" + fpEfnSets.get(i).getFP());
						fieldFNAuto.setText("" + fpEfnSets.get(i).getFN());
					}
				}
			}
		});
						
		centralPanel.add(buttonGenerateAutomaticConfiguration);
	}
}