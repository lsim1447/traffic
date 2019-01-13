package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Form2 extends JFrame implements Runnable{

	private JPanel contentPane;
	private Genetikus genetikus;
	private int figy[];

	/**
	 * Create the frame.
	 */
	public Form2(Genetikus genetikus, int[] figy) {
		this.genetikus = genetikus;
		this.figy = figy;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 456, 318);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea ta2 = new JTextArea();
		ta2.setEditable(false);
		ta2.setBounds(32, 42, 124, 197);
		contentPane.add(ta2);
		
		JTextArea ta1 = new JTextArea();
		ta1.setEditable(false);
		ta1.setBounds(177, 42, 124, 197);
		contentPane.add(ta1);
		
		JButton btnMutal = new JButton("Mutal");
		btnMutal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				genetikus.mutal();
				ta2.setText(ta1.getText());
				ta1.setText(genetikus.Ertekek());
			}
		});
		btnMutal.setBounds(323, 79, 89, 23);
		contentPane.add(btnMutal);
		
		JButton btnKeresztez = new JButton("Keresztez");
		btnKeresztez.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				genetikus.keresztez();
				ta2.setText(ta1.getText());
				ta1.setText(genetikus.Ertekek());
			}
		});
		btnKeresztez.setBounds(323, 125, 89, 23);
		contentPane.add(btnKeresztez);
		JButton btnKirajzol = new JButton("Kirajzol");
		btnKirajzol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				figy[0] = 0;
			}
		});
		btnKirajzol.setBounds(323, 173, 89, 23);
		contentPane.add(btnKirajzol);
		setVisible(true);

	}
}
