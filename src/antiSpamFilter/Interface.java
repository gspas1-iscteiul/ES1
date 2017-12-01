package antiSpamFilter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Interface {

	private JFrame frame;
	private JPanel globalPanel;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;

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
		
		construirPainelCima(globalPanel);
		construirPainelCentro(globalPanel);
		construirPainelBaixo(globalPanel);
	}
	
	private void construirPainelCima(JPanel globalPanel2) {
		panel1 = new JPanel();
		
		globalPanel.add(panel1,BorderLayout.NORTH);		
		panel1.setLayout(new GridLayout(5,2));

		JLabel label1 = new JLabel(" Caminho para os ficheiros de configuração:");
		panel1.add(label1);
		
		JLabel label2 = new JLabel("");
		panel1.add(label2);
		
		JTextField field1 = new JTextField(); //tem de se ler o caminho inserido aqui
		panel1.add(field1);
		
		JLabel label3 = new JLabel(" rules.cf");
		panel1.add(label3);
		
		JTextField field2 = new JTextField(); //tem de se ler o caminho inserido aqui
		panel1.add(field2);
		
		JLabel label4 = new JLabel(" spam.log");
		panel1.add(label4);
		
		JTextField field3 = new JTextField(); //tem de se ler o caminho inserido aqui
		panel1.add(field3);
		
		JLabel label5 = new JLabel(" ham.log");
		panel1.add(label5);
	}
	
	private void construirPainelCentro(JPanel globalPanel2) {
		panel2 = new JPanel();
		
		globalPanel.add(panel2,BorderLayout.CENTER);		
		panel2.setLayout(new GridLayout(11,2));
		
		JLabel label1 = new JLabel(" Configuração Manual");
		JLabel label2 = new JLabel("");
		JLabel labeltemporaria = new JLabel(" Colocar tabela de regras e pesos");
		JLabel label3 = new JLabel("");
		JLabel label4 = new JLabel(" FP:");
		JLabel label5 = new JLabel("");
		JTextField field1 = new JTextField();
		JLabel label6 = new JLabel("");
		JLabel label7 = new JLabel(" FN:");
		JLabel label8 = new JLabel("");
		JTextField field2 = new JTextField();
		JLabel label9 = new JLabel("");
		JLabel label10 = new JLabel("");
		JLabel label11 = new JLabel("");
		
		panel2.add(label1);
		panel2.add(label2);
		panel2.add(labeltemporaria);
		panel2.add(label3);
		panel2.add(label4);
		panel2.add(label5);
		panel2.add(field1);
		panel2.add(label6);
		panel2.add(label7);
		panel2.add(label8);
		panel2.add(field2);
		panel2.add(label9);
		panel2.add(label10);
		panel2.add(label11);
		
		JButton button1 = new JButton("Gerar Configuração");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JLabel label12 = new JLabel("");
		
		JButton button2 = new JButton("Avaliar Configuração");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JLabel label13 = new JLabel("");
		
		JButton button3 = new JButton("Gravar Configuração");
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		panel2.add(button1);
		panel2.add(label12);
		panel2.add(button2);
		panel2.add(label13);
		panel2.add(button3);
	}
	
	private void construirPainelBaixo(JPanel globalPanel2) {
		panel3 = new JPanel();
		
		globalPanel.add(panel3,BorderLayout.SOUTH);		
		panel3.setLayout(new GridLayout(9,2));
		
		JLabel label1 = new JLabel(" Configuração Automática");
		JLabel label2 = new JLabel("");
		JLabel labeltemporaria = new JLabel(" Colocar tabela de regras e pesos");
		JLabel label3 = new JLabel("");
		JLabel label4 = new JLabel(" FP:");
		JLabel label5 = new JLabel("");
		JTextField field1 = new JTextField();
		JLabel label6 = new JLabel("");
		JLabel label7 = new JLabel(" FN:");
		JLabel label8 = new JLabel("");
		JTextField field2 = new JTextField();
		JLabel label9 = new JLabel("");
		JLabel label10 = new JLabel("");
		JLabel label11 = new JLabel("");

		panel3.add(label1);
		panel3.add(label2);
		panel3.add(labeltemporaria);
		panel3.add(label3);
		panel3.add(label4);
		panel3.add(label5);
		panel3.add(field1);
		panel3.add(label6);
		panel3.add(label7);
		panel3.add(label8);
		panel3.add(field2);
		panel3.add(label9);
		panel3.add(label10);
		panel3.add(label11);
		
		JButton button1 = new JButton("Gerar Configuração Automática Ótima");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JButton button2 = new JButton("Gravar Configuração");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
				
		panel3.add(button1);
		panel3.add(button2);
	}

	public void open() {
		frame.setVisible(true);
		frame.setSize(550,700);
	}

	public static void main(String[] args) {
		Interface i = new Interface();
		i.open();
	}
}