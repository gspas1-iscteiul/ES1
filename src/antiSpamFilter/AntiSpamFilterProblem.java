package antiSpamFilter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

public class AntiSpamFilterProblem extends AbstractDoubleProblem {

	private static final long serialVersionUID = 1L;

	public AntiSpamFilterProblem() {
	    this(335);
	}

	public AntiSpamFilterProblem(Integer numberOfVariables) {
	    setNumberOfVariables(numberOfVariables); //numberOfVariables=335
	    setNumberOfObjectives(2); // FP e FN
	    setName("AntiSpamFilterProblem");

	    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

	    for (int i = 0; i < getNumberOfVariables(); i++) {
	      lowerLimit.add(-5.0);
	      upperLimit.add(5.0);
	    }

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);
	}

	public void evaluate(DoubleSolution solution){
	    double[] falsosPositivosEFalsosNegativos = new double[getNumberOfObjectives()];
	    double[] pesos = new double[getNumberOfVariables()];
	    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
	    	pesos[i] = solution.getVariableValue(i);
	    }

	    RulesReader ruleReader = new RulesReader();
		ruleReader.readFileRules("C://Users//Guilherme Pereira//git//ES1-2017-IC1-67//AntiSpamConfigurationForProfessionalMailbox//rules.cf");
		
		LinkedList<Rule> rules = ruleReader.getRules();
		
		MessagesAndRulesReader spamMessagesReader = new MessagesAndRulesReader();
		
	    //cálculo FN
	    spamMessagesReader.readFileSpamAndHam("C://Users//Guilherme Pereira//git//ES1-2017-IC1-67//spam.log");
		
		LinkedList<Message> spamMessages = spamMessagesReader.getMessages();
	    
	    double sumRulesWeight = 0;
	    falsosPositivosEFalsosNegativos[0] = 0.0; //FP
	    
		for (int i = 0; i < spamMessages.size(); i++) {
			LinkedList<Rule> messageRules = spamMessages.get(i).getRules();
			sumRulesWeight = 0;
			for (int j = 0; j < messageRules.size(); j++) {
				String s = messageRules.get(j).getName(); //da-me a regra j da mensagem i do ficheiro Spam

				for (int k = 0; k < rules.size(); k++) {
					if(rules.get(k).getName().equals(s)) {						
						sumRulesWeight = sumRulesWeight + pesos[k];
					}
				}
			}
			if (sumRulesWeight < 5) {
				falsosPositivosEFalsosNegativos[0] += 1;
			}
		}

	    solution.setObjective(0, falsosPositivosEFalsosNegativos[0]);
	    
	    //cálculo FP
	    spamMessagesReader.readFileSpamAndHam("C://Users//Guilherme Pereira//git//ES1-2017-IC1-67//ham.log");
		
		LinkedList<Message> hamMessages = spamMessagesReader.getMessages();
	    
	    double sumRulesWeight1 = 0;
	    falsosPositivosEFalsosNegativos[1] = 0.0; //FN
	    
		for (int i = 0; i < hamMessages.size(); i++) {
			LinkedList<Rule> messageRules = hamMessages.get(i).getRules();
			sumRulesWeight1 = 0;
			for (int j = 0; j < messageRules.size(); j++) {
				String s = messageRules.get(j).getName(); //da-me a regra j da mensagem i do ficheiro Spam

				for (int k = 0; k < rules.size(); k++) {
					if(rules.get(k).getName().equals(s)) {						
						sumRulesWeight1 = sumRulesWeight1 + pesos[k];
					}
				}
			}
			if (sumRulesWeight1 > 5) {
				falsosPositivosEFalsosNegativos[1] += 1;
			}
		}
	    
	    solution.setObjective(1, falsosPositivosEFalsosNegativos[1]);
	 }
}