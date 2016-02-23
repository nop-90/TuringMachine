package turing;

public class Transition {
	private Etat depart;
	private Etat arrive;

	private String symbole;
	private String LR;
	private String reecriture;


	public Transition (Etat depart , String symbole , Etat arrive , String LR , String reecriture){
		this.depart=depart;
		this.symbole = symbole;
		this.arrive = arrive;
		this.LR = LR; 
		this.reecriture = reecriture;
	}

	public Etat getDepart(){return this.depart;}
	public Etat getArrive(){return this.arrive;}
	public String getSymbole(){return this.symbole;}
	public String getLR(){return this.LR;}
	public String getReec(){return this.reecriture;}

}
