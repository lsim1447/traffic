package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	private int[][] ut;
	private int[][] graf;
	private int n;
	private Random rand = new Random();
	private int autoSzam = 100;
	private int[] autosz;
	private Jelzolampa[][] lampak;
	private HashMap<Integer, LinkedList<Integer>> csomopontok;
	private HashMap<Integer, Csomopont> csomopontIdok;
	private LinkedList<Thread> thk;
	private HashMap<Integer, LinkedList<Integer>> utvonalak;
	private JPanel contentPane;
	private static int figy[] = new int[]{1};

	public Main(int[] autosz, HashMap<Integer, LinkedList<Integer>> utvonalak) throws HeadlessException {
		super();
		this.utvonalak = utvonalak;
		this.autosz = autosz;
		autoSzam=autosz[0];
		thk = new LinkedList<Thread>();
		readFile("Varos2.txt");
		grafosit();
		felraklampa();
		feltoltCsomopont();
		feltoltCsomopontIdo();
		if (autosz[1]==0){
			init();
		} else {
			initSz();
		}
		@SuppressWarnings("unused")
		Way w = new Way(graf.length, graf, rand.nextInt(graf.length));
	}

	public Main(int[] autosz, HashMap<Integer, LinkedList<Integer>> legjobbidok, HashMap<Integer, LinkedList<Integer>> utvonalak) throws HeadlessException {
		super();
		this.utvonalak = utvonalak;
		this.autosz = autosz;
		autoSzam=autosz[0];
		this.csomopontok = legjobbidok;
		thk = new LinkedList<Thread>();
		readFile("Varos2.txt");
		grafosit();
		felraklampa();
		feltoltCsomopont();
		feltoltCsomopontIdo(legjobbidok);
		if (autosz[1]==0){
			init();
		} else {
			initSz();
		}
		@SuppressWarnings("unused")
		Way w = new Way(graf.length, graf, rand.nextInt(graf.length));
	}


	private void feltoltCsomopontIdo(HashMap<Integer, LinkedList<Integer>> legjobbidok) {

		csomopontIdok = new HashMap<Integer, Csomopont>();
		for (Entry<Integer, LinkedList<Integer>> entry : csomopontok.entrySet()) {
			int key = entry.getKey();
			LinkedList<Integer> value = entry.getValue();

			Csomopont csomopont = new Csomopont(legjobbidok.get(key), value, lampak, key, autosz);
			Thread t = new Thread(csomopont);
			t.start();
			thk.add(t);
			csomopontIdok.put(key, csomopont);
		}
	}

	public HashMap<Integer, Csomopont> getCsomopontIdok() {
		return csomopontIdok;
	}

	public HashMap<Integer, LinkedList<Integer>> getUtvonalak() {
		return utvonalak;
	}



	private void feltoltCsomopontIdo() {

		csomopontIdok = new HashMap<Integer, Csomopont>();
		for (Entry<Integer, LinkedList<Integer>> entry : csomopontok.entrySet()) {
			int key = entry.getKey();
			LinkedList<Integer> value = entry.getValue();
			LinkedList<Integer> temp = new LinkedList<Integer>();
			if (value.size() == 4){
				temp = idoRandomTomb(1500, 3000, 4500, 10);
			} else {
				temp = idoRandomTomb(1500, 3000, 4500, 3);
			}
			Csomopont csomopont = new Csomopont(temp, value, lampak, key, autosz);
			Thread t = new Thread(csomopont);
			t.start();
			thk.add(t);
			csomopontIdok.put(key, csomopont);
		}
	}

	private void readFile(String filename) {
		Scanner scanner;
		try {
			scanner = new Scanner(new File(filename));

			n = scanner.nextInt();
			ut = new int[n][n];
			for (int i = 0; i < ut.length; i++) {
				for (int j = 0; j < ut.length; j++) {
					ut[i][j] = scanner.nextInt();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean haromSzomszed(int i, int j) {
		int db = 0;
		if (ut[i - 1][j] == 1)
			db++;
		if (ut[i][j + 1] == 1)
			db++;
		if (ut[i + 1][j] == 1)
			db++;
		if (ut[i][j - 1] == 1)
			db++;
		return db > 2;
	}

	public void felraklampa() {
		lampak = new Jelzolampa[graf.length][graf.length];
		for (int i = 0; i < graf.length; i++) {
			for (int j = 0; j < graf.length; j++) {
				if (graf[i][j] != 0) {
					lampak[i][j] = new Jelzolampa();
				} else {
					lampak[i][j] = null;
				}
			}
		}
	}

	private void grafosit() {
		int db = 0;
		for (int i = 1; i < ut.length - 1; i++) {
			for (int j = 1; j < ut.length - 1; j++) {
				if ((i == j && j == 1) || (i == n - j - 1 && i == 1) || (n - i - 1 == j && j == 1)
						|| (i == j && i == n - 2)) {
					// System.out.println("sarok");
					++db;
				} else if (ut[i][j] == 1 && haromSzomszed(i, j)) {
					// System.out.println("d" + i + " " + j);
					++db;
				}
			}
		}

		graf = new int[db][db];
		boolean b = false;
		int hossz = 0;
		int[] sorHossz = new int[100];
		int sorDB = 0;
		int gr = 0;

		for (int j = 1; j < ut.length - 1; j++) {
			hossz++;
			if ((j == 1) || (j == n - 2)) {
				if (b == false) {
					b = true;
				} else {
					graf[gr][gr + 1] = hossz;
					gr++;
					gr++;
					b = false;
					sorHossz[sorDB] = hossz;
					sorDB++;
					hossz = 0;
				}
			} else if (ut[1][j] == 1 && haromSzomszed(1, j)) {
				graf[gr][gr + 1] = hossz;
				gr++;
				sorHossz[sorDB] = hossz;
				sorDB++;
				hossz = 0;
			}
		}

		int oszlopKoz = gr;
		b = false;
		hossz = 0;
		int[] oszlopHossz = new int[100];
		sorDB = 0;
		gr = 0;

		for (int j = 1; j < ut.length - 1; j++) {
			hossz++;
			if ((j == 1) || (j == n - 2)) {
				if (b == false) {
					b = true;
				} else {
					graf[gr][gr + oszlopKoz] = hossz;
					b = false;
					gr += oszlopKoz;
					oszlopHossz[sorDB] = hossz;
					sorDB++;
					hossz = 0;
				}
			} else if (ut[j][1] == 1 && haromSzomszed(j, 1)) {
				graf[gr][gr + oszlopKoz] = hossz;
				gr += oszlopKoz;
				oszlopHossz[sorDB] = hossz;
				sorDB++;
				hossz = 0;
			}
		}

		gr = gr / oszlopKoz;
		int p = 0;
		for (int i = 0; i < gr + 1; i++) {
			for (int j = 0; j < oszlopKoz - 1; j++) {
				graf[p][p + 1] = sorHossz[j];
				graf[p + 1][p] = sorHossz[j];
				// System.out.println(p + " "+ p+1 + " " + sorHossz[j]);
				p++;
			}
			p++;
		}
		p = 0;
		for (int i = 0; i < oszlopKoz; i++) {
			for (int j = 0; j < gr; j++) {
				graf[p][p + oszlopKoz] = oszlopHossz[j];
				graf[p + oszlopKoz][p] = oszlopHossz[j];
				// System.out.println(p + " "+ p+oszlopKoz + " " + sorHossz[j]);
				p += oszlopKoz;
			}
			p = i + 1;
		}
	}

	private void init() {
		setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		this.setSize(width, height);
		//this.setAlwaysOnTop(true);
		this.setLayout(new GridLayout(1, 1, 0, 0));
		this.getContentPane().setBackground(Color.WHITE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		LinkedList<Auto> autoList = new LinkedList<Auto>();
		if (autosz[2]==0){
			utvonalak = new HashMap<Integer, LinkedList<Integer>>();
			for (int i = 0; i < autoSzam; i++) {
				Random r = new Random();
				Way way = new Way(graf.length, graf, r.nextInt(2*graf.length));
				Auto a = new Auto(way.utvonal(), graf, ut, lampak, csomopontIdok, autosz);
				autoList.add(a);
				utvonalak.put(i, way.utvonal());
				Thread tr = new Thread(a);
				tr.start();
			}
		} else {
			for (Entry<Integer, LinkedList<Integer>> entry : utvonalak.entrySet()) {
				LinkedList<Integer> list = entry.getValue();
				Auto a = new Auto(list, graf, ut, lampak, csomopontIdok, autosz);
				autoList.add(a);
				Thread tr = new Thread(a);
				tr.start();
			}
		}

		Screen s = new Screen(n, ut, autoList, lampak, csomopontok);
		this.add(s);
		this.setFocusable(true);
		this.setVisible(true);
		while (true) {

			s.repaint();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void initSz() {
		//System.out.println("itt se?");
		LinkedList<Auto> autoList = new LinkedList<Auto>();
		for (int i = 0; i < autoSzam; i++) {
			Random r = new Random();
			Way way = new Way(graf.length, graf, r.nextInt(graf.length*2));

			Auto a = new Auto(way.utvonal(), graf, ut, lampak, csomopontIdok, autosz);
			autoList.add(a);
			Thread tr = new Thread(a);
			tr.start();
			thk.add(tr);
		}

		//System.out.println("varok");
		while(autosz[0]>20){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//System.out.println(autosz[0]);
		}
		//System.out.println("nezek");
		int osszeg=0;
		for (int i = 0; i < lampak.length; i++) {
			for (int j = 0; j < lampak.length; j++) {
				if (lampak[i][j]!=null){
					osszeg+=lampak[i][j].getOsszElore()+ lampak[i][j].getOsszJobb()+ lampak[i][j].getOsszBal();
				}
			}
		}
		//System.out.println("vege " + osszeg);
		for (Thread t : thk) {
			if (t != null){
				t.stop();
			}
		}
	}

	public LinkedList<Integer> idoRandomTomb(int ido1, int ido2, int ido3, int n){
		LinkedList<Integer> t = new LinkedList<Integer>();
		Random rand = new Random();
		for(int i = 0; i < n; ++i){
			int  x = rand.nextInt(3) + 1;
			if(x == 1){
				t.add(ido1);
			} else if(x == 2){
				t.add(ido2);
			} else {
				t.add(ido3);
			}
		}
		return t;
	}

	private void feltoltCsomopont() {
		csomopontok = new HashMap<Integer, LinkedList<Integer>>();

		for (int i = 0; i < graf.length; i++) {
			LinkedList<Integer> tempList = new LinkedList<Integer>();
			for (int j = 0; j < graf.length; j++) {
				if (lampak[j][i] != null) {
					tempList.add(j);
				}
			}
			if (tempList.size() != 2) {
				csomopontok.put(i, tempList);
			} else {
				//System.out.println(i);
				lampak[tempList.getFirst()][i].setBal(true);
				lampak[tempList.getFirst()][i].setJobb(true);
				lampak[tempList.getFirst()][i].setElore(true);
				tempList.removeFirst();
				lampak[tempList.getFirst()][i].setBal(true);
				lampak[tempList.getFirst()][i].setJobb(true);
				lampak[tempList.getFirst()][i].setElore(true);
			}
		}
	}

	public static void main(String[] args) {
		int[] autosz = {500,1,0};
		HashMap<Integer, LinkedList<Integer>> legjobbIdoklista = new HashMap<Integer, LinkedList<Integer>>();
		HashMap<Integer, Integer> legjobbIdok = new HashMap<Integer, Integer>();
		HashMap<Integer, LinkedList<Integer>> utvonalak = new HashMap<Integer, LinkedList<Integer>>();

		Main m = new Main(autosz, utvonalak);
		for (Entry<Integer, Csomopont> entry : m.getCsomopontIdok().entrySet()) {
			int key = entry.getKey();
			Csomopont cs = entry.getValue();
			legjobbIdoklista.put(key, cs.getList());
			legjobbIdok.put(key, cs.osszesVarakozas());
		}
		autosz[2]=0;
		Genetikus genetikus = new Genetikus();
		int hanyszor = 50;
		while(hanyszor>0){
			autosz[0]=500;
			m = new Main(autosz, utvonalak);
			//System.out.println("khiih");
			int osszegIdo = 0;
			for (Entry<Integer, Csomopont> entry : m.getCsomopontIdok().entrySet()) {
				int key = entry.getKey();
				Csomopont cs = entry.getValue();
				int osszvarakozas = cs.osszesVarakozas();
				if (osszvarakozas < legjobbIdok.get(key)){
					legjobbIdok.remove(key);
					legjobbIdok.put(key, osszvarakozas);
					legjobbIdoklista.remove(key);
					legjobbIdoklista.put(key, cs.getList());
				}
				osszegIdo +=osszvarakozas;
			}
			hanyszor--;
			genetikus.hozzaad(osszegIdo, m.getCsomopontIdok());
		}
		genetikus.megTart10();
		Form2 f2 = new Form2(genetikus, figy);
		Thread t = new Thread(f2);
		t.start();
		while(true){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(figy[0] != 1){
				f2.setVisible(false);
				f2.dispose();
				t.stop();
				autosz = new int[]{500, 0, 0};
				legjobbIdoklista = genetikus.getLegjobbIdoLista();
				utvonalak = new HashMap<Integer, LinkedList<Integer>>();
				Main szimulacio = new Main(autosz, legjobbIdoklista, utvonalak);
				break;
			}
		}

	}

	/*public void Form2(Genetikus genetikus) {
		JFrame frame2= new JFrame("F2");
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setBounds(100, 100, 456, 318);
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
		ta1.setText(genetikus.Ertekek());

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
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				frame2.dispose();
				int[] autosz = {500, 0, 0};
				HashMap<Integer, LinkedList<Integer>> legjobbIdoklista = genetikus.getLegjobbIdoLista();
				HashMap<Integer, LinkedList<Integer>> utvonalak = new HashMap<Integer, LinkedList<Integer>>();
				Main szimulacio = new Main(autosz, legjobbIdoklista, utvonalak);
			}
		});
		btnKirajzol.setBounds(323, 173, 89, 23);
		contentPane.add(btnKirajzol);
		frame2.add(contentPane);
		frame2.setVisible(true);
	}
	 */}