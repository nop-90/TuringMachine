package turing;

import java.util.*;

public class Etat {

	private String id;
	//String représente le symbole lu
	//Etat représente l'état associé à la lecture d'un symbole
	private HashMap<String,Transition> transitions;

	public Etat (String idState){
		this.id = idState;
		this.transitions = new HashMap<String, Transition>();
	}

	public Etat(String idState, HashMap<String,Transition> transitions){
		this.id=idState;
		this.transitions = transitions;
	}

	public String getId () { return this.id;}
	public HashMap<String,Transition> getTransitions(){	return this.transitions;}
	
	public void addTransition (String key, Transition t) {
		this.transitions.put(key, t);
	}

	/**
	* retourne la transition corespondante a la lecture du symbole
	*/
	public Transition getTransi(String symbole){
		return this.transitions.get(symbole);
	}
}
