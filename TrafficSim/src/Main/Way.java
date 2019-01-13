package Main;

import java.util.LinkedList;
import java.util.Random;

public class Way {
	private int[][] ut;
	private int n;
	private int kezdoPont = 0;
	private Random rand = new Random();
	private int vegPont;
	private int utHosszusag = 1;
	
	public Way(int nPar, int[][] x, int vp){
		this.n = nPar;
		this.ut = new int[n+1][n+1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				ut[i][j] = x[i][j];
			}
		}
		if (vp<n){
			this.vegPont = vp;
			
		} else {
			switch (vp % 3) {
			case 0:
				this.vegPont=7;
				break;
			case 1:
				this.vegPont=n-3;
				break;
			case 2:
				this.vegPont=n / 2;
				break;
			default:
				this.vegPont = 7;
				break;
			}
		}
		
		do{
			kezdoPont = rand.nextInt(nPar);
		}while(kezdoPont==vegPont);
		
	}
	
	public LinkedList<Integer> utvonal()
	{
		int szamlalo = 1;
		LinkedList<Integer> linkedlist = new LinkedList<Integer>();
		int db;
		int k = 0;
		int vegP7 = vegPont / 7;
		int par = kezdoPont;
		linkedlist.add(kezdoPont);
		boolean oke = false;
		int[] temp = new int[10];
		//System.out.println("kezdoPont: " + kezdoPont);
		//System.out.println("vegPont: " + vegPont);
		while (par != vegPont){
			int kl = -1;
			szamlalo ++;
			db = 0;
			for (int j = 0; j < ut.length; j++) {
				if (ut[par][j] != 0){
					temp[db] = j;
					db ++;
				}
			}
			oke = false;
			do{
				oke = false;
				if (szamlalo > utHosszusag){
					int kezdP = par / 7; 
					if (kezdP == vegP7){
						if (par < vegPont){
							kl=par+1;
						}
						else{
							kl=par-1;
						}
					}
					else if (kezdP < vegP7){
						kl=par+7;
					}
					else{
						kl=par-7;
					}
					for (int i = 0; i < temp.length; i++) {
						if (temp[i]==kl){
							k=i;
							break;
						}
					}
				}
				else k = rand.nextInt(db);
				if (temp[k] == par) oke = true;
				if (linkedlist.size() > 1){
					if (linkedlist.get(linkedlist.size() - 2) == temp[k]) oke = true;
				}
			} while(oke);
			par = temp[k];
			linkedlist.add(par);
			for (int i = 0; i < temp.length; i++) {
				temp[i] = -1;
			}
		}
		return linkedlist;
	}
}