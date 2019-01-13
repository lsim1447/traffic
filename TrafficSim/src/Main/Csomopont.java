package Main;

import java.util.LinkedList;

public class Csomopont implements Runnable {

	private int eset=0;
	private LinkedList<Integer> idok;
	private LinkedList<Integer> list; //csomopontbeli
	private Jelzolampa[][] lampak;			 //osszes
	private int key;
	private int[] autosz;
	
	public Csomopont(LinkedList<Integer> idok, LinkedList<Integer> jelzolampak, Jelzolampa[][] lampak, int key, int[] autosz) {
		super();
		this.idok = idok;
		this.list = jelzolampak;
		this.lampak = lampak;
		this.key = key;
		this.autosz = autosz;
	}

	
	
	public LinkedList<Integer> getList() {
		return list;
	}

	public int getEset() {
		return eset;
	}

	public void setEset(int eset) {
		this.eset = eset;
	}

	public LinkedList<Integer> getIdok() {
		return idok;
	}

	public void setIdok(LinkedList<Integer> idok) {
		this.idok = idok;
	}

	private void harmasLampa() {
		if (Math.abs(key - list.get(0)) == Math.abs(key - list.get(2))) {
			if (key < list.get(1)) {
				//System.out.println("lefeleNez "+ key + " " + eset);
				switch (eset) {
				case 0:
					lampak[list.get(0)][key].lampaBeallit(false, false, false);
					lampak[list.get(2)][key].lampaBeallit(true, true, false);
					lampak[list.get(1)][key].lampaBeallit(false, false, true);
					break;
				case 1:
					lampak[list.get(0)][key].lampaBeallit(false, false, true);
					lampak[list.get(2)][key].lampaBeallit(false, false, false);
					lampak[list.get(1)][key].lampaBeallit(false, true, true);
					break;
				case 2:
					lampak[list.get(0)][key].lampaBeallit(true, false, true);
					lampak[list.get(2)][key].lampaBeallit(true, false, false);
					lampak[list.get(1)][key].lampaBeallit(false, false, false);
				default:
					break;
				}

			} else {
				//System.out.println("felfeleNez "+ key + " " + eset);
				switch (eset) {
				case 0:
					lampak[list.get(1)][key].lampaBeallit(false, false, true);
					lampak[list.get(0)][key].lampaBeallit(true, true, false);
					lampak[list.get(2)][key].lampaBeallit(false, false, false);
					break;
				case 1:
					lampak[list.get(1)][key].lampaBeallit(false, true, true);
					lampak[list.get(0)][key].lampaBeallit(false, false, false);
					lampak[list.get(2)][key].lampaBeallit(false, false, true);
					break;
				case 2:
					lampak[list.get(1)][key].lampaBeallit(false, false, false);
					lampak[list.get(0)][key].lampaBeallit(true, false, false);
					lampak[list.get(2)][key].lampaBeallit(true, false, true);
				default:
					break;
				}
			}
		} else if (key < list.get(1)) {
			//System.out.println("jobraNez "+ key + " " + eset);
			switch (eset) {
			case 0:
				lampak[list.get(0)][key].lampaBeallit(true, false, false);
				lampak[list.get(2)][key].lampaBeallit(false, false, false);
				lampak[list.get(1)][key].lampaBeallit(true, false, true);
				break;
			case 1:
				lampak[list.get(0)][key].lampaBeallit(false, false, false);
				lampak[list.get(2)][key].lampaBeallit(false, true, true);
				lampak[list.get(1)][key].lampaBeallit(false, false, true);
				break;
			case 2:
				lampak[list.get(0)][key].lampaBeallit(true, true, false);
				lampak[list.get(2)][key].lampaBeallit(false, false, true);
				lampak[list.get(1)][key].lampaBeallit(false, false, false);
			default:
				break;
			}
		} else {
			//System.out.println("balraNez "+ key + " " + eset);
			switch (eset) {
			case 0:
				lampak[list.get(0)][key].lampaBeallit(false, false, true);
				lampak[list.get(2)][key].lampaBeallit(true, true, false);
				lampak[list.get(1)][key].lampaBeallit(false, false, false);
				break;
			case 1:
				lampak[list.get(0)][key].lampaBeallit(false, true, true);
				lampak[list.get(2)][key].lampaBeallit(false, false, false);
				lampak[list.get(1)][key].lampaBeallit(false, false, true);
				break;
			case 2:
				lampak[list.get(0)][key].lampaBeallit(false, false, false);
				lampak[list.get(2)][key].lampaBeallit(true, false, false);
				lampak[list.get(1)][key].lampaBeallit(true, false, true);
			default:
				break;
			}
		}
	}

	private void negyesLampa(){
		switch (eset) {
		case 0:
			lampak[list.get(0)][key].lampaBeallit(true, true, true);
			lampak[list.get(1)][key].lampaBeallit(false, false, true);
			lampak[list.get(2)][key].lampaBeallit(false, false, false);
			lampak[list.get(3)][key].lampaBeallit(false, false, false);
			break;
		case 1:
			lampak[list.get(0)][key].lampaBeallit(false, false, true);
			lampak[list.get(1)][key].lampaBeallit(false, false, false);
			lampak[list.get(2)][key].lampaBeallit(true, true, true);
			lampak[list.get(3)][key].lampaBeallit(false, false, false);
			break;
		case 2:
			lampak[list.get(0)][key].lampaBeallit(false, false, false);
			lampak[list.get(1)][key].lampaBeallit(true, true, true);
			lampak[list.get(2)][key].lampaBeallit(false, false, false);
			lampak[list.get(3)][key].lampaBeallit(false, false, true);
			break;
		case 3:
			lampak[list.get(0)][key].lampaBeallit(false, false, false);
			lampak[list.get(1)][key].lampaBeallit(false, false, false);
			lampak[list.get(2)][key].lampaBeallit(false, false, true);
			lampak[list.get(3)][key].lampaBeallit(true, true, true);
			break;
		case 4:
			lampak[list.get(0)][key].lampaBeallit(false, false, true);
			lampak[list.get(1)][key].lampaBeallit(false, false, true);
			lampak[list.get(2)][key].lampaBeallit(true, false, true);
			lampak[list.get(3)][key].lampaBeallit(false, false, false);
			break;
		case 5:
			lampak[list.get(0)][key].lampaBeallit(false, false, false);
			lampak[list.get(1)][key].lampaBeallit(true, false, true);
			lampak[list.get(2)][key].lampaBeallit(false, false, true);
			lampak[list.get(3)][key].lampaBeallit(false, false, true);
			break;
		case 6:
			lampak[list.get(0)][key].lampaBeallit(false, false, true);
			lampak[list.get(1)][key].lampaBeallit(false, false, false);
			lampak[list.get(2)][key].lampaBeallit(false, false, true);
			lampak[list.get(3)][key].lampaBeallit(true, false, true);
			break;
		case 7:
			lampak[list.get(0)][key].lampaBeallit(true, false, true);
			lampak[list.get(1)][key].lampaBeallit(false, false, true);
			lampak[list.get(2)][key].lampaBeallit(false, false, false);
			lampak[list.get(3)][key].lampaBeallit(false, false, true);
			break;
		case 8:
			lampak[list.get(0)][key].lampaBeallit(false, false, false);
			lampak[list.get(1)][key].lampaBeallit(true, false, true);
			lampak[list.get(2)][key].lampaBeallit(true, false, true);
			lampak[list.get(3)][key].lampaBeallit(false, false, false);
			break;
		case 9:
			lampak[list.get(0)][key].lampaBeallit(true, false, true);
			lampak[list.get(1)][key].lampaBeallit(false, false, false);
			lampak[list.get(2)][key].lampaBeallit(false, false, false);
			lampak[list.get(3)][key].lampaBeallit(true, false, true);
			break;
		default:
			break;
		}
	}


	@Override
	public void run() {
		if (autosz[1]==0){
			jelzolampaValt();
		} else {
			jelzolampaValtSz();
		}
	}

	private void jelzolampaValt(){
		while(autosz[0]>0){
			try {
				Thread.sleep(idok.get(eset));

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//if (key==17) System.out.println("key eset " + key + " " + eset);
			if (list.size() == 4){
				negyesLampa();
				eset++;
				eset=eset % 10;
				nullazAtenged();
			}
			else{
				harmasLampa();
				eset++;
				eset=eset % 3;
				nullazAtenged();
			}
		}
	}

	private void jelzolampaValtSz(){
		int time = 0;
		while(autosz[0]>20){
			if (idok.get(eset)==time){
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//if (key==17) System.out.println("key eset " + key + " " + eset);
				if (list.size() == 4){
					negyesLampa();
					eset++;
					eset=eset % 10;
					nullazAtenged();
				}
				else{
					harmasLampa();
					eset++;
					eset=eset % 3;
					nullazAtenged();
				}
				time=0;
			}
			time++;
		}
	}

	private void nullazAtenged(){
		for (Integer integer : list) {
			lampak[integer][key].setAteneged("bal", 0);
			lampak[integer][key].setAteneged("jobb", 0);
			lampak[integer][key].setAteneged("elore", 0);
		}
	}

	public int osszesVarakozas(){			
		int ossz=0;
		for (Integer integer :list ) {
			ossz += lampak[key][integer].getOsszBal() + lampak[key][integer].getOsszJobb() + lampak[key][integer].getOsszElore();  
		}
		return ossz;
			
	}
}
