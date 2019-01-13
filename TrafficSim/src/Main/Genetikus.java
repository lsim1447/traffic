package Main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Map.Entry;

public class Genetikus {
	private LinkedList<Integer> idok;
	private LinkedList<HashMap<Integer, Csomopont>> csomopontIdokList;

	public Genetikus() {
		super();
		csomopontIdokList = new LinkedList<HashMap<Integer, Csomopont>>();
		idok = new LinkedList<Integer>();
	}

	public HashMap<Integer, LinkedList<Integer>> getLegjobbIdoLista(){
		HashMap<Integer, LinkedList<Integer>> hm = new HashMap<Integer, LinkedList<Integer>>();
		for (Entry<Integer, Csomopont> entry : csomopontIdokList.getFirst().entrySet()) {
			hm.put(entry.getKey(), entry.getValue().getIdok());
		}
		return hm;
	}
	
	public void hozzaad(int osszVarakozasiIdo, HashMap<Integer, Csomopont> csomopontIdok){
		idok.add(osszVarakozasiIdo);
		csomopontIdokList.add(csomopontIdok);
	}

	public void megTart10(){
		for (int i = 0; i < 10; i++) {
			for (int j = i+1; j < idok.size(); j++) {
				if (idok.get(i)>idok.get(j)){
					int temp = idok.get(i);
					idok.set(i, idok.get(j));
					idok.set(j, temp);

					HashMap<Integer, Csomopont> thm = csomopontIdokList.get(i);
					csomopontIdokList.set(i, csomopontIdokList.get(j));
					csomopontIdokList.set(j, thm);

				}
			}
		}

		for (int i = 0; i < 40; i++) {
			idok.removeLast();
			csomopontIdokList.removeLast();
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
	
	public String Ertekek(){
		String ret = "";
		for (int time : idok) {
			ret += time + "\n";
		}
		return ret;
	}

	public void mutal(){
		for (int i = 0; i < 10; i++) {
			HashMap<Integer, Csomopont> thm = csomopontIdokList.get(i);
			Csomopont maxCsomopont = thm.get(1);
			for (Entry<Integer, Csomopont> entry : thm.entrySet()) {
				Csomopont value = entry.getValue();
				if (value != null && value.osszesVarakozas() > maxCsomopont.osszesVarakozas()){
					maxCsomopont = value;
				}
			}
			if (maxCsomopont.getList().size() == 4){
				for (int j = 0; j < 4; j++) {
					LinkedList<Integer> temp = idoRandomTomb(1500, 3000, 4500, 10);
					int szamlalo=0;

					HashMap<Integer, LinkedList<Integer>> hm = new HashMap<Integer, LinkedList<Integer>>();
					for (Entry<Integer, Csomopont> entry : thm.entrySet()) {
						if (maxCsomopont != entry.getValue()) hm.put(entry.getKey(), entry.getValue().getIdok());
						else hm.put(entry.getKey(), temp);
					}
					int[] autosz = {500, 1, 0};
					HashMap<Integer, LinkedList<Integer>> utvonalak = new HashMap<Integer, LinkedList<Integer>>();
					Main m = new Main(autosz, hm, utvonalak);


					int osszegIdo = 0;
					for (Entry<Integer, Csomopont> entry : m.getCsomopontIdok().entrySet()) {
						Csomopont cs = entry.getValue();
						int osszvarakozas = cs.osszesVarakozas();
						osszegIdo +=osszvarakozas;
					}
					hozzaad(osszegIdo, m.getCsomopontIdok());
				}
			}
			else{
				for (int j = 0; j < 4; j++) {
					LinkedList<Integer> temp = idoRandomTomb(1500, 3000, 4500, 3);
					int szamlalo=0;

					HashMap<Integer, LinkedList<Integer>> hm = new HashMap<Integer, LinkedList<Integer>>();
					for (Entry<Integer, Csomopont> entry : thm.entrySet()) {
						if (maxCsomopont != entry.getValue()) hm.put(entry.getKey(), entry.getValue().getIdok());
						else hm.put(entry.getKey(), temp);
					}
					int[] autosz = {500, 1, 0};
					HashMap<Integer, LinkedList<Integer>> utvonalak = new HashMap<Integer, LinkedList<Integer>>();
					Main m = new Main(autosz, hm, utvonalak);


					int osszegIdo = 0;
					for (Entry<Integer, Csomopont> entry : m.getCsomopontIdok().entrySet()) {
						Csomopont cs = entry.getValue();
						int osszvarakozas = cs.osszesVarakozas();
						osszegIdo +=osszvarakozas;
					}
					hozzaad(osszegIdo, m.getCsomopontIdok());
				}
			}
		}
		megTart10();
	}

	public void keresztez(){
		Random rand = new Random();
		int elso=0;
		int masodik=0;
		HashMap<Integer, Csomopont> elsoCsomopont;
		HashMap<Integer, Csomopont> masodikCsomopont;
		HashMap<Integer, LinkedList<Integer>> eredmeny = new HashMap<Integer, LinkedList<Integer>>();

		for (int i = 0; i < 40;i++) {
			elso=rand.nextInt(10);
			masodik=rand.nextInt(10);
			elsoCsomopont=csomopontIdokList.get(elso);
			masodikCsomopont=csomopontIdokList.get(masodik);

			for (int j = 0; j < 28; j++) {
				if (elsoCsomopont.get(j)!=null) {
					LinkedList<Integer> value=elsoCsomopont.get(j).getIdok();
					eredmeny.put(j, value);
				}
			}
			for (int k = 28; k < 56; k++) {
				if (masodikCsomopont.get(k)!=null) {
					LinkedList<Integer> value=elsoCsomopont.get(k).getIdok();
					eredmeny.put(k, value);
				}
			}

			int[] autosz = {500, 1, 0};
			HashMap<Integer, LinkedList<Integer>> utvonalak = new HashMap<Integer, LinkedList<Integer>>();
			Main m = new Main(autosz, eredmeny, utvonalak);
			int osszegIdo = 0;
			for (Entry<Integer, Csomopont> entry : m.getCsomopontIdok().entrySet()) {
				Csomopont cs = entry.getValue();
				int osszvarakozas = cs.osszesVarakozas();
				osszegIdo +=osszvarakozas;
			}
			hozzaad(osszegIdo, m.getCsomopontIdok());
		}
		megTart10();
	}
}
