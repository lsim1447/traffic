package Main;

public class Jelzolampa {

	private boolean jobb=true;
	private boolean bal=true;
	private boolean elore=true; 
	private int countElore=0;
	private int countJobbra=0;
	private int countBalra=0;
	private int atengedJobb=0;
	private int atengedBal=0;
	private int atengedElore=0;
	private int osszJobb=0;
	private int osszBal=0;
	private int osszElore=0;
	private int uton=0;
	
	public int getUton(){
		return uton;
	}
	
	public void novelUton(){
		uton++;
	}

	public void csokkentUton(){
		if (uton>0) uton--;
	}

	public int getOsszJobb() {
		return osszJobb;
	}

	public void setOsszJobb(int osszJobb) {
		this.osszJobb = osszJobb;
	}

	public int getOsszBal() {
		return osszBal;
	}

	public void setOsszBal(int osszBal) {
		this.osszBal = osszBal;
	}

	public int getOsszElore() {
		return osszElore;
	}

	public void setOsszElore(int osszElore) {
		this.osszElore = osszElore;
	}

	public boolean isJobb() {
		return jobb;
	}

	public void setJobb(boolean jobb) {
		this.jobb = jobb;
	}

	public boolean isBal() {
		return bal;
	}

	public void setBal(boolean bal) {
		this.bal = bal;
	}

	public boolean isElore() {
		return elore;
	}

	public void setElore(boolean elore) {
		this.elore = elore;
	}

	public int getCountElore() {
		return countElore;
	}

	public void setCountElore(int countElore) {
		this.countElore = countElore;
	}

	public int getCountJobbra() {
		return countJobbra;
	}

	public void setCountJobbra(int countJobbra) {
		this.countJobbra = countJobbra;
	}

	public int getCountBalra() {
		return countBalra;
	}

	public void setCountBalra(int countBalra) {
		this.countBalra = countBalra;
	}

	public Jelzolampa(){

	}
	
	public boolean hova(String hova){
		if (hova.equals("bal")) return bal;
		else if (hova.equals("jobb")) return jobb;
		else return elore;
	}
	
	public void osszesBealit(boolean b){
		elore=b;
		bal=b;
		jobb=b;
	}
	
	public void lampaBeallit(boolean a, boolean b, boolean c){
		elore=a;
		bal=b;
		jobb=c;
	}
	
	public void szamlalLampanal(String hova){
		if (hova.equals("bal")){
			countBalra++;
		}
		else if (hova.equals("jobb")){
			countJobbra++;
		}
		else {
			countElore++;
		}
	}
	
	public void kivonLampanal(String hova, int ertek){
		if (hova.equals("bal")) {
			if(countBalra > 0)countBalra--;
			osszBal+= ertek;
		}
		else if (hova.equals("jobb")){
			if(countJobbra > 0)countJobbra--;
			osszJobb+=ertek;
		}
		else {
			if(countElore > 0)countElore--;
			osszElore+=ertek;
		}
	}
	
	public int getOsszeg(){
		return countBalra + countElore + countJobbra;
	}
	
	public int getAteneged(String hova){
		if (hova.equals("bal")) return atengedBal; 
		else if (hova.equals("jobb")) return atengedJobb;
		else return atengedElore;
	}
	
	public void setAteneged(String hova, int szam){
		if (hova.equals("bal")) atengedBal = szam; 
		else if (hova.equals("jobb")) atengedJobb = szam;
		else atengedElore = szam;
	}
}
