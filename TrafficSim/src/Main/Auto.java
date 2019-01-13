package Main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.LinkedList;

public class Auto implements Runnable{
	private int utHossz;
	private LinkedList<Integer> utSzakasz;
	private int[][] ut;
	private int x;
	private int y;
	private int width;
	private int height;
	private int ido = 100;
	private final int autoMeret=15;
	private Jelzolampa[][] lampak;
	private int[][] graf;
	private HashMap<Integer, Csomopont> csomopontIdok;
	private int[] autosz;
	private boolean celbaErt=false;
	private int elozoCsomopont = 0;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Auto(LinkedList<Integer> utSzakasz, int[][] graf, int[][] ut, Jelzolampa[][] lampak, HashMap<Integer, Csomopont> csomopontIdok, int[] autosz) {
		super();
		this.csomopontIdok = csomopontIdok;
		this.autosz = autosz;
		this.utSzakasz = utSzakasz;
		this.ut = ut;
		this.graf = graf;
		elozoCsomopont = utSzakasz.getFirst();
		int[] t = megkeresCsomopont(utSzakasz.getFirst());
		//System.out.println("ut1: " + utSzakasz.getFirst());
		utSzakasz.removeFirst();
		//System.out.println("ut2: " + utSzakasz.getFirst());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) screenSize.getWidth();
		height = (int) screenSize.getHeight()-50;
		width /=ut.length-2;
		height /=ut.length-2;
		this.lampak=lampak;
		int[] t2 = megkeresCsomopont(utSzakasz.getFirst());
		if (t[0]+t[1]<t2[0]+t2[1]) {
		} else {
		}
		x = t[0] * width;
		y = t[1] * height;
		int kovetkezoCsomopont = utSzakasz.getFirst();
		this.lampak[elozoCsomopont][kovetkezoCsomopont].novelUton();
	}

	@Override
	public void run() {
		int x = 2;
		try {
			if (autosz[1]==0) autoMegy();
			else autoMegySz();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		autosz[0]--;
	}

	public void autoMegy() throws InterruptedException{
		boolean mehet = true;
		int kovetkezoX = 0;
		int kovetkezoY = 0;
		String irany = "";
		int elozoX=0;
		int elozoY=0;
		int koord[] = new int[2];
		koord[0] = 0;
		koord[1] = 0;
		int csomo = -1;
		int varakozasIdo = 0;
		while(!utSzakasz.isEmpty()){
			int[] t= null;
			
			if (mehet){
				csomo = utSzakasz.getFirst();
				elozoX=x;
				elozoY=y;
				t = megkeresCsomopont(utSzakasz.getFirst());
				utSzakasz.removeFirst();

				kovetkezoX = t[0] * width;
				kovetkezoY = t[1] * height;

				mozgatas(elozoX, elozoY, kovetkezoX, kovetkezoY);
				Thread.sleep(ido);
				if (utSzakasz.isEmpty()){
					lampak[elozoCsomopont][csomo].csokkentUton();
					celbaErt=true;
					return;
				}
				t = megkeresCsomopont(utSzakasz.getFirst());
				int kovkovX = t[0]*width;
				int kovkovY = t[1]*height;
				irany = iranyBealit(elozoX, elozoY, kovetkezoX, kovetkezoY, kovkovX, kovkovY);
				koord[0] = indexMeghatarozas(elozoX/width, elozoY/height);
				koord[1] = indexMeghatarozas(kovetkezoX/width, kovetkezoY/height);
			}

			//System.out.println("tovabmehetek: " + lampak[koord[0]][koord[1]].hova(irany) + " "+ irany+ " " + csomo);
			Csomopont cs = csomopontIdok.get(csomo);
			if (cs == null) {
				int csomoKovKov = utSzakasz.getFirst();
				if ((graf[csomo][csomoKovKov] > lampak[csomo][csomoKovKov].getUton())){
					lampak[elozoCsomopont][csomo].csokkentUton();
					lampak[csomo][csomoKovKov].novelUton();
					elozoCsomopont=csomo;
					x=kovetkezoX;
					y=kovetkezoY;
					if (mehet==false){
						//lampak[koord[0]][koord[1]].kivonLampanal(irany, varakozasIdo);
						varakozasIdo=0;
					}
					mehet=true;
				}
				else{
					if (mehet==true){
						//lampak[koord[0]][koord[1]].szamlalLampanal(irany);
					}
					Thread.sleep(500);
					mehet=false;
				}
			} else {
				int eset = cs.getEset();
				LinkedList<Integer> idok = cs.getIdok();
				int atenged = lampak[koord[0]][koord[1]].getAteneged(irany);
				int csomoKovKov = utSzakasz.getFirst();
				if (lampak[koord[0]][koord[1]].hova(irany) &&  (idok.get(eset)/500 > atenged) && (graf[csomo][csomoKovKov] > lampak[csomo][csomoKovKov].getUton())){
					if (mehet==false){
						lampak[koord[0]][koord[1]].kivonLampanal(irany, varakozasIdo);
						varakozasIdo=0;
					}
					lampak[koord[0]][koord[1]].setAteneged(irany, atenged+1);;
					lampak[elozoCsomopont][csomo].csokkentUton();
					lampak[csomo][csomoKovKov].novelUton();
					elozoCsomopont=csomo;
					x=kovetkezoX;
					y=kovetkezoY;
					mehet=true;
				}
				else {
					if (mehet==true){
						lampak[koord[0]][koord[1]].szamlalLampanal(irany);
					}
					mehet = false;
					Thread.sleep(500);
					varakozasIdo+=500;
				}
			}
		}
	}

	public void autoMegySz() throws InterruptedException{
		boolean mehet = true;
		int kovetkezoX = 0;
		int kovetkezoY = 0;
		String irany = "";
		int elozoX=0;
		int elozoY=0;
		int koord[] = new int[2];
		koord[0] = 0;
		koord[1] = 0;
		int varakozasIdo=0;
		int csomo = -1;
		while(!utSzakasz.isEmpty()){
			int[] t= null;
			if (mehet){
				csomo=utSzakasz.getFirst();
				elozoX=x;
				elozoY=y;
				t = megkeresCsomopont(utSzakasz.getFirst());
				utSzakasz.removeFirst();

				kovetkezoX = t[0] * width;
				kovetkezoY = t[1] * height;

				if (utSzakasz.isEmpty()){
					celbaErt=true;
					return;
				}
				t = megkeresCsomopont(utSzakasz.getFirst());
				int kovkovX = t[0]*width;
				int kovkovY = t[1]*height;
				irany = iranyBealit(elozoX, elozoY, kovetkezoX, kovetkezoY, kovkovX, kovkovY);
				koord[0] = indexMeghatarozas(elozoX/width, elozoY/height);
				koord[1] = indexMeghatarozas(kovetkezoX/width, kovetkezoY/height);
			}

			//System.out.println("tovabmehetek: " + lampak[koord[0]][koord[1]].hova(irany) + " "+ irany+ " " + csomo);
			Csomopont cs = csomopontIdok.get(csomo);
			if (cs == null) {
				x=kovetkezoX;
				y=kovetkezoY;
				//if (mehet ==false) lampak[koord[0]][koord[1]].kivonLampanal(irany, varakozasIdo);
				mehet=true;
			} else {
				int eset = cs.getEset();
				LinkedList<Integer> idok = cs.getIdok();
				int atenged = lampak[koord[0]][koord[1]].getAteneged(irany);
				if (lampak[koord[0]][koord[1]].hova(irany) &&  (idok.get(eset)/500 > atenged)){
					if (mehet==false){
						lampak[koord[0]][koord[1]].kivonLampanal(irany, varakozasIdo);
						varakozasIdo=0;
					}
					lampak[koord[0]][koord[1]].setAteneged(irany, atenged+1);;
					x=kovetkezoX;
					y=kovetkezoY;
					mehet=true;
				}
				else {
					if (mehet==true){
						lampak[koord[0]][koord[1]].szamlalLampanal(irany);
					}
					mehet = false;
					Thread.sleep(1);
					varakozasIdo+=1;
				}
			}
		}
	}


	private int indexMeghatarozas(int lx, int ly){
		int db = -1;
		lx++;
		ly++;
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

	private void mozgatas(int elozoX, int elozoY, int kovetkezoX, int kovetkezoY) throws InterruptedException{
		if (elozoX<kovetkezoX){
			x+=width;
			y+=height-autoMeret;
		}
		else if(elozoX>kovetkezoX){
			x-=autoMeret;
		}
		else if(elozoY<kovetkezoY){
			y+=height;
		}
		else{
			y-=autoMeret;
			x+=width-autoMeret;
		}
		if (elozoX<kovetkezoX){

			utHossz=(kovetkezoX-elozoX)+x-autoMeret;
			int megy = (kovetkezoX-elozoX) / 5;
			while(x+megy<utHossz-width){
				if (ido % 100 == 0){
					x+=megy;
				}
				//System.out.println("x= " + x);
			}
			x=utHossz-width;
		}
		else if (elozoY<kovetkezoY){
			utHossz=(kovetkezoY-elozoY)+y-autoMeret;
			int megy = height / 5;
			while(y+megy<utHossz-height){
				Thread.sleep(ido);
				y+=megy;
				//System.out.println("y= " + y);
			}
			y=utHossz-height;
		}
		else if (elozoX>kovetkezoX){
			utHossz=x-Math.abs(kovetkezoX-elozoX);
			int megy = Math.abs(kovetkezoX-elozoX) / 10;
			while(x-megy>utHossz+width+autoMeret){
				Thread.sleep(ido);
				x-=megy;
				//System.out.println("x= " + x);
			}
			x=utHossz+width+autoMeret;
		}
		else{
			utHossz=y-Math.abs(kovetkezoY-elozoY);
			int megy = height / 10;
			while(y-megy>utHossz+height+autoMeret){
				Thread.sleep(ido);
				y-=megy;
				//System.out.println("y= " + y);
			}
			y=utHossz+height+autoMeret;
		}
	}

	private String iranyBealit(int elozoX, int elozoY, int kovetkezoX, int kovetkezoY, int kovkovX, int kovkovY){
		String irany;
		if (elozoX<kovetkezoX || elozoY<kovetkezoY){

			if (elozoX == kovkovX || kovkovY == elozoY) {
				irany = "elore";
			}
			else if(elozoX < kovetkezoX){
				if (kovkovY<kovetkezoY) irany="bal";
				else irany="jobb";

			}
			else {
				if (kovkovX<kovetkezoX) irany="jobb";
				else irany="bal";
			}
		}
		else{
			if (elozoX == kovkovX || kovkovY == elozoY) {
				irany = "elore";
			}
			else if(elozoX > kovetkezoX){
				if (kovkovY<kovetkezoY) irany="jobb";
				else irany="bal";

			}
			else {
				if (kovkovX<kovetkezoX) irany="bal";
				else irany="jobb";
			}
		}
		return irany;
	}
	
	public boolean isCelbaErt() {
		return celbaErt;
	}

	public void setCelbaErt(boolean celbaErt) {
		this.celbaErt = celbaErt;
	}

	private boolean haromSzomszed(int i, int j){
		int db = 0;
		if (ut[i-1][j]==1) db++;
		if (ut[i][j+1]==1) db++;
		if (ut[i+1][j]==1) db++;
		if (ut[i][j-1]==1) db++;
		return db>2;
	}

	private int[] megkeresCsomopont(int pont){
		int db = -1;
		for (int i = 1; i < ut.length-1; i++) {
			for (int j = 1; j < ut.length-1; j++) {
				if (ut[i][j]==1){
					if ((i==j && j==1) || (i==ut.length-j-1 && i==1) || (ut.length-i-1==j && j==1) || (i==j && i==ut.length-2)) db++;
					else if (haromSzomszed(i, j)) db++;

					if (pont==db){
						int[] temp = {i-1,j-1};
						return temp; 
					}
				}
			}
		}
		return null;
	}


}