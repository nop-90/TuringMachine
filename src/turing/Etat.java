package turing;

import java.util.*;


public class Etat {
	
	private String id;
	//String représente le symbole lu
	//Etat représente l'état associé à la lecture d'un symbole
	private ArrayList<Transition> transitions;
	
	public Etat(String idState){
		this.id=idState;
		this.transitions = new ArrayList<Transition>();
	}
	public void addTransition(Transition new_trans) {
		this.transitions.add(new_trans);
	}
	/*public Etat getNext(String symbole){
		return repartition.get(symbole);
	}*/
	
	public ArrayList<Transition> getTransitions(){
		return this.transitions;
	}
}