package antiSpamFilter.test;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import antiSpamFilter.AntiSpamFilterConfiguration;
import antiSpamFilter.AutomaticConfiguration;
import antiSpamFilter.FPeFNAutomaticConfigurationReader;
import antiSpamFilter.FPeFNSet;
import antiSpamFilter.ManualConfiguration;
import antiSpamFilter.Message;
import antiSpamFilter.MessagesAndRulesReader;
import antiSpamFilter.Rule;
import antiSpamFilter.RulesReader;
import antiSpamFilter.WeightsAutomaticConfigurationReader;
import antiSpamFilter.WeightsSet;

/**
 * @author Guilherme Pereira
 * 
 * with the contribution of Ricardo Silva
 */
public class TestCase {

	/**
	 * Test method for {@link antiSpamFilter.AntiSpamFilterConfiguration#getFrame()}.
	 */
	@Test
	public void testFrame() {
		AntiSpamFilterConfiguration a = new AntiSpamFilterConfiguration();
		assertNotNull(a.getFrame());
	}
	
	
	/**
	 * Test method for {@link antiSpamFilter.AntiSpamFilterConfiguration#getGlobalPanel()}.
	 */
	@Test
	public void testGlobalPanel() {
		AntiSpamFilterConfiguration a = new AntiSpamFilterConfiguration();
		assertNotNull(a.getGlobalPanel());
	}
	
	/**
	 * Test method for {@link antiSpamFilter.AntiSpamFilterConfiguration#getTopPanel()}.
	 */
	@Test
	public void testTopPanel() {
		AntiSpamFilterConfiguration a = new AntiSpamFilterConfiguration();
		assertNotNull(a.getTopPanel());
	}
	
	
	/**
	 * Test method for {@link antiSpamFilter.AntiSpamFilterConfiguration#getFieldRules()/getFieldHam()/getFieldSpam()}.
	 */	
	@Test
	public void testFields() {
		AntiSpamFilterConfiguration a = new AntiSpamFilterConfiguration();
		assertNotNull(a.getFieldRules());
		assertNotNull(a.getFieldHam());
		assertNotNull(a.getFieldSpam());
	}
	
	
	/**
	 * Test method for {@link antiSpamFilter.ManualConfiguration#calculationOfFPManual(javax.swing.JTable, javax.swing.JTextField)}.
	 */
	@Test
	public void testCalculationOfFPManual() {
		double[] weights = new double[335];
		for (int i = 0; i < weights.length; i++) {
			weights[i] = 0.0;
		}
		
		JTextField fieldRules = new JTextField("C:\\Users\\Guilherme Pereira\\git\\ES1-2017-IC1-67\\AntiSpamConfigurationForProfessionalMailbox\\rules.cf");
		JTextField fieldSpam = new JTextField("C:\\Users\\Guilherme Pereira\\git\\ES1-2017-IC1-67\\spam.log");
		JTextField fieldHam = new JTextField("C:\\Users\\Guilherme Pereira\\git\\ES1-2017-IC1-67\\ham.log");

		ManualConfiguration m = new ManualConfiguration(null, fieldRules, fieldSpam, fieldHam);
		int numberFP = (int) m.calculationOfFPManual(weights);
		
		assertTrue(numberFP==0);
		
		assertTrue(m.getFieldHam() == fieldHam);
		assertTrue(m.getFieldSpam() == fieldSpam);
		assertTrue(m.getFieldRules() == fieldRules);
	}
	
	
	/**
	 * Test method for {@link antiSpamFilter.ManualConfiguration#calculationOfFNManual(javax.swing.JTable, javax.swing.JTextField)}.
	 */
	@Test
	public void testCalculationOfFNManual() {
		double[] weights = new double[335];
		for (int i = 0; i < weights.length; i++) {
			weights[i] = 0.0;
		}
		
		JTextField fieldRules = new JTextField("C:\\Users\\Guilherme Pereira\\git\\ES1-2017-IC1-67\\AntiSpamConfigurationForProfessionalMailbox\\rules.cf");
		JTextField fieldSpam = new JTextField("C:\\Users\\Guilherme Pereira\\git\\ES1-2017-IC1-67\\spam.log");
		JTextField fieldHam = new JTextField("C:\\Users\\Guilherme Pereira\\git\\ES1-2017-IC1-67\\ham.log");

		ManualConfiguration m = new ManualConfiguration(null, fieldRules, fieldSpam, fieldHam);
		int numberFN = (int) m.calculationOfFNManual(weights);
		
		assertTrue(numberFN==239);
		
		assertTrue(m.getFieldHam() == fieldHam);
		assertTrue(m.getFieldSpam() == fieldSpam);
		assertTrue(m.getFieldRules() == fieldRules);
	}
	
	
	/**
	 * Test method for {@link antiSpamFilter.ManualConfiguration#constructAndOperateOnButtonReadRules(javax.swing.JTable, javax.swing.table.DefaultTableModel)}.
	 */
	@Test
	public void testConstructAndOperateOnButtonReadRules() {
		DefaultTableModel model = new DefaultTableModel(2,2); //tamanho apenas temporário, alterado mais abaixo
		String[] colNames = {"Regras","Pesos"};
		model.setColumnIdentifiers(colNames);

		JTable table = new JTable(model);
				
		JTextField fieldRules = new JTextField("C:\\Users\\Guilherme Pereira\\git\\ES1-2017-IC1-67\\AntiSpamConfigurationForProfessionalMailbox\\rules.cf");
		JTextField fieldSpam = new JTextField("C:\\Users\\Guilherme Pereira\\git\\ES1-2017-IC1-67\\spam.log");
		JTextField fieldHam = new JTextField("C:\\Users\\Guilherme Pereira\\git\\ES1-2017-IC1-67\\ham.log");

		ManualConfiguration m = new ManualConfiguration(null, fieldRules, fieldSpam, fieldHam);
		
		int size = m.constructAndOperateOnButtonReadRules(table, model);
		assertTrue(size==335);
		
		assertTrue(m.getFieldHam() == fieldHam);
		assertTrue(m.getFieldSpam() == fieldSpam);
		assertTrue(m.getFieldRules() == fieldRules);
	}
	
	
	/**
	 * Test method for {@link antiSpamFilter.FPeFNAutomaticConfigurationReader#getFPeFNSets()}.
	 */
	@Test
	public void testGetFPeFNSets() {
		FPeFNAutomaticConfigurationReader r = new FPeFNAutomaticConfigurationReader();
		assertNotNull(r.getFPeFNSets());
	}
	
	/**
	 * Test method for {@link antiSpamFilter.FPeFNSet#getFN()/getFP()}.
	 */
	@Test
	public void testGetFNeFP() {
		FPeFNSet f = new FPeFNSet(2,3);
		int FN = (int) f.getFN();
		int FP = (int) f.getFP();
		assertTrue(FN == 2);
		assertTrue(FP == 3);
	}
	
	
	/**
	 * Test method for {@link antiSpamFilter.Message#getID()}.
	 */
	@Test
	public void testGetID() {
		String id = "01";
		
		Message m = new Message(id, null);
		assertTrue(m.getID() == "01");
	}
	
	/**
	 * Test method for {@link antiSpamFilter.Message#getRules()}.
	 */
	@Test
	public void testGetMessageRules() {
		LinkedList<Rule> rules = new LinkedList<>();
		Rule r = new Rule("rule1");
		rules.add(r);
		
		Message m = new Message(null, rules);
		assertNotNull(m.getRules());
		
		assertTrue(m.getRules().size() == 1);
	}
	
	
	/**
	 * Test method for {@link antiSpamFilter.MessagesAndRulesReader#getMessages()}.
	 */
	@Test
	public void testGetMessages() {
		MessagesAndRulesReader r = new MessagesAndRulesReader();
		r.readFileSpamAndHam("C:\\Users\\Guilherme Pereira\\git\\ES1-2017-IC1-67\\spam.log");
		assertTrue(r.getMessages().size() == 239);

		MessagesAndRulesReader r1 = new MessagesAndRulesReader();
		r1.readFileSpamAndHam("C:\\Users\\Guilherme Pereira\\git\\ES1-2017-IC1-67\\ham.log");
		assertTrue(r1.getMessages().size() == 695);
	}
	
	
	/**
	 * Test method for {@link antiSpamFilter.Rule#getName()}.
	 */
	@Test
	public void testGetName() {
		Rule r = new Rule("rule1");
		assertTrue(r.getName() == "rule1");
	}
	
	
	/**
	 * Test method for {@link antiSpamFilter.RulesReader#getRules()}.
	 */
	@Test
	public void testGetRules() {
		RulesReader r = new RulesReader();
		r.readFileRules("C:\\Users\\Guilherme Pereira\\git\\ES1-2017-IC1-67\\AntiSpamConfigurationForProfessionalMailbox\\rules.cf");
		assertTrue(r.getRules().size() == 335);
	}
	
	
	/**
	 * Test method for {@link antiSpamFilter.WeightsAutomaticConfigurationReader#getWeightsSets()}.
	 */
	@Test
	public void testGetWeightsSets() {
		WeightsAutomaticConfigurationReader r = new WeightsAutomaticConfigurationReader();
		assertNotNull(r.getWeightsSets());
	}
	
	
	/**
	 * Test method for {@link antiSpamFilter.WeightsSet#getWeights()}.
	 */
	@Test
	public void testGetWeights() {
		LinkedList<Double> weights = new LinkedList<Double>();
		weights.add(1.0);
		
		WeightsSet w = new WeightsSet(weights);
		assertTrue(w.getWeights().size() == 1);
	}
	
	
	/**
	 * Test method for {@link antiSpamFilter.AutomaticConfiguration#getRuleReader()}.
	 */
	@Test
	public void testGetRuleReader() {
		AutomaticConfiguration a = new AutomaticConfiguration(null, null);
		assertNotNull(a.getRuleReader());
	}
	
	
	/**
	 * Test method for {@link antiSpamFilter.AutomaticConfiguration#getRules()}.
	 */
	@Test
	public void testGetRulesAuto() {
		AutomaticConfiguration a = new AutomaticConfiguration(null, null);
		assertNotNull(a.getRules());
	}
	
	
	/**
	 * Test method for {@link antiSpamFilter.ManualConfiguration#getFieldSpam()/getFieldHam()/getFieldRules()}.
	 */
	@Test
	public void testGetFields() {
		JTextField fieldRules = new JTextField("C:\\Users\\Guilherme Pereira\\git\\ES1-2017-IC1-67\\AntiSpamConfigurationForProfessionalMailbox\\rules.cf");
		JTextField fieldSpam = new JTextField("C:\\Users\\Guilherme Pereira\\git\\ES1-2017-IC1-67\\spam.log");
		JTextField fieldHam = new JTextField("C:\\Users\\Guilherme Pereira\\git\\ES1-2017-IC1-67\\ham.log");

		ManualConfiguration m = new ManualConfiguration(null, fieldRules, fieldSpam, fieldHam);

		assertNotNull(m.getFieldSpam());
		assertNotNull(m.getFieldHam());
		assertNotNull(m.getFieldRules());
		
		assertTrue(m.getFieldSpam() == fieldSpam);
		assertTrue(m.getFieldHam() == fieldHam);
		assertTrue(m.getFieldRules() == fieldRules);
	}
}