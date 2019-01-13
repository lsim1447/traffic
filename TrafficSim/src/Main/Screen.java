package Main;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import javax.swing.JPanel;

public class Screen extends JPanel {
	private static final long serialVersionUID = 6842717052226657484L;
	private int n;
	private int[][] ut;
	private boolean kezdes = true;
	private LinkedList<Auto> autoList;
	private int width;
	private int height;
	private int[] tx;
	private int[] ty;
	private final int autoMeret=15;
	private Jelzolampa[][] lampak;
	private HashMap<Integer, LinkedList<Integer>> csomopontok;

	public Screen(int n, int[][] ut, LinkedList<Auto> autoList, Jelzolampa[][] lampak, HashMap<Integer, LinkedList<Integer>> csomopontok) {
		super();
		this.n = n;
		this.ut = ut;

		this.autoList = autoList;
		tx = new int[autoList.size()];
		ty = new int[autoList.size()];
		this.csomopontok = csomopontok;
		this.lampak = lampak;
	}

	public void paint(Graphics g){
		int darab=0;
		if (kezdes){
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			width = (int) screenSize.getWidth();
			height = (int) screenSize.getHeight()-50;
			g.clearRect(0, 0, width, height);
			g.setColor(Color.black);
			width /=n-2;
			height /=n-2;
			System.out.println();
			for (int i = 1; i < ut.length-1; i++) {
				for (int j = 1; j < ut.length-1; j++) {
					if (ut[i][j]==1) g.fillRect((i-1)*width, (j-1)*height, width, height);
					System.out.print(ut[i][j] + " ");
				}
				System.out.println();
			}
			
			kezdes=false;
			for (Auto auto : autoList) {
				int x = auto.getX();
				int y = auto.getY();
				g.setColor(Color.GREEN);
				g.fillRect(x,y,autoMeret,autoMeret);
			}
		}
		else{
			for (int i = 0; i < autoList.size(); i++) {
				g.setColor(Color.BLACK);
				g.fillRect(tx[i],ty[i],autoMeret,autoMeret);
			}

			for (int i = 0; i < autoList.size(); i++) {
				if (!autoList.get(i).isCelbaErt()){
					int x = autoList.get(i).getX();
					int y = autoList.get(i).getY();
					tx[i]=x;
					ty[i]=y;

					g.setColor(Color.GREEN);
					g.fillRect(x,y,autoMeret,autoMeret);
				}
			}
			g.setColor(Color.RED);
			
			for (int i = 1; i < ut.length-1; i++) {
				for (int j = 1; j < ut.length-1; j++) {
					if (ut[i][j]==1){
						if (haromSzomszed(i, j)){
							int key = indexMeghatarozas(i, j);
							/*if (key==9){
								System.out.println();
							}*/
							LinkedList<Integer> list = csomopontok.get(key);
							if (list.size()==4){
								int lx = (i-1) * width;
								int ly = (j-1) * height;

								int kx = lx;
								int ky = ly;
								kx+=width-autoMeret;
								ky+=height+autoMeret;
								String ertek = "" + lampak[list.get(2)][key].getOsszeg();
								if (!ertek.equals("0")) g.drawString(ertek, kx, ky);

								kx = lx;
								ky = ly;
								ertek = "" + lampak[list.get(1)][key].getOsszeg();
								if (!ertek.equals("0")) g.drawString(ertek, kx, ky);

								kx = lx-autoMeret;
								ky = ly;
								ky+=height;
								ertek = "" + lampak[list.get(0)][key].getOsszeg();
								if (!ertek.equals("0")) g.drawString(ertek, kx, ky);

								kx = lx;
								ky = ly;
								ky+=autoMeret;
								kx+=width;
								ertek = "" + lampak[list.get(3)][key].getOsszeg();
								if (!ertek.equals("0")) g.drawString(ertek, kx, ky);
							}
							else{
								if  (j == 1){//Ricsi vagyis en irtam :)
									++darab;
									int lx = (i-1) * width;
									int ly = (j-1) * height;

									int kx = lx;
									int ky = ly;
									kx+=width;
									ky+=autoMeret;
									String ertek = "" + lampak[list.get(2)][key].getOsszeg();
									if (!ertek.equals("0")) g.drawString(ertek, kx, ky);

									kx = lx;
									ky = ly;
									kx-=autoMeret;
									ky+=2*height-2*autoMeret;
									ertek = "" + lampak[list.get(0)][key].getOsszeg();
									if (!ertek.equals("0")) g.drawString(ertek, kx, ky);

									kx = lx;
									ky = ly;
									ky+=3*autoMeret;
									kx+=width-autoMeret;
									ertek = "" + lampak[list.get(1)][key].getOsszeg();
									if (!ertek.equals("0")) g.drawString(ertek, kx, ky);
								}
								else if (i == ut.length-2){//Ricsi vagyis en irtam :) 
									++darab;
									//System.out.println(darab+" darab");
									//System.out.println("j="+j);
									int lx = (i-1) * width;
									int ly = (j-1) * height;

									int kx = lx;
									int ky = ly;
									//kx+=width;
									//ky+=autoMeret;
									String ertek = "" + lampak[list.get(1)][key].getOsszeg();
									if (!ertek.equals("0")) g.drawString(ertek, kx, ky);

									kx = lx+width-autoMeret;
									ky = ly+height+autoMeret;
									ertek = "" + lampak[list.get(2)][key].getOsszeg();
									if (!ertek.equals("0")) g.drawString(ertek, kx, ky);

									kx = lx-autoMeret;
									ky = ly+height;
									ertek = "" + lampak[list.get(0)][key].getOsszeg();
									if (!ertek.equals("0")) g.drawString(ertek, kx, ky);
								}
								else if (i == 1){//Ricsi vagyis en irtam :)
									++darab;
									//System.out.println(darab+" darab");
									//System.out.println("j="+j);
									int lx = (i-1) * width;
									int ly = (j-1) * height;

									int kx = lx;
									int ky = ly;
									//kx+=width;
									//ky+=autoMeret;
									String ertek = "" + lampak[list.get(0)][key].getOsszeg();
									if (!ertek.equals("0")) g.drawString(ertek, kx, ky);

									kx = lx+width;
									ky = ly+autoMeret;
									ertek = "" + lampak[list.get(2)][key].getOsszeg();
									if (!ertek.equals("0")) g.drawString(ertek, kx, ky);

									kx = lx+width-autoMeret;
									ky = ly+height+autoMeret;
									ertek = "" + lampak[list.get(1)][key].getOsszeg();
									if (!ertek.equals("0")) g.drawString(ertek, kx, ky);

								}
								else if (j == ut.length-2){//Ricsi vagyis en irtam :)
									++darab;
									//System.out.println(darab+" darab");
									//System.out.println("j="+j);
									int lx = (i-1) * width;
									int ly = (j-1) * height;

									int kx = lx;
									int ky = ly;
									//kx+=width;
									//ky+=autoMeret;
									String ertek = "" + lampak[list.get(1)][key].getOsszeg();
									if (!ertek.equals("0")) g.drawString(ertek, kx, ky);

									kx = lx+width;
									ky = ly+autoMeret;
									ertek = "" + lampak[list.get(2)][key].getOsszeg();
									if (!ertek.equals("0")) g.drawString(ertek, kx, ky);

									kx = lx-autoMeret;
									ky = ly+2*autoMeret;
									ertek = "" + lampak[list.get(0)][key].getOsszeg();
									if (!ertek.equals("0")) g.drawString(ertek, kx, ky);

								}
							}
						}
					}
				}
			}
		}
	}

	private boolean haromSzomszed(int i, int j){
		int db = 0;
		if (ut[i-1][j]==1) db++;
		if (ut[i][j+1]==1) db++;
		if (ut[i+1][j]==1) db++;
		if (ut[i][j-1]==1) db++;
		return db>2;
	}

	private int indexMeghatarozas(int lx, int ly){
		int db = -1;
		for (int i = 1; i < ut.length-1; i++) {
			for (int j = 1; j < ut.length-1; j++) {
				if (ut[i][j] == 1){
					if ((i==j && j==1) || (i==ut.length-j-1 && i==1) || (ut.length-i-1==j && j==1) || (i==j && i==ut.length-2)) db++;
					else if (haromSzomszed(i, j)) db++;
				}
				if (i == lx &&  j == ly){
					return db;
				}
			}
		}
		return db;
	}
}


