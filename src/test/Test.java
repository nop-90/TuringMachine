package test;
import java.awt.SystemColor;

import turing.*;

public class Test {

  public static void main (String[] args) {

    Etat depart = new Etat("depart");
    Etat un = new Etat("un");
    Etat deux = new Etat("deux");
    Etat fin = new Etat("FIN");
    
    Transition t1 = new Transition(depart, "a", un, "R", "b");
    Transition t2 = new Transition(depart, "b", deux, "L", "a");
	    depart.addTransition(t1.getSymbole(), t1);
	    depart.addTransition(t2.getSymbole(), t2);
    Transition t3 = new Transition(un, "a", un, "R", "a");
    Transition t4 = new Transition(un, "b", depart, "L", "a");
	    un.addTransition(t3.getSymbole(), t3);
	    un.addTransition(t4.getSymbole(), t4);
    Transition t5 = new Transition(deux, "a", fin, "R", "b");
    Transition t6 = new Transition(deux, "b", un, "R", "a");
	    deux.addTransition(t5.getSymbole(), t5);
	    deux.addTransition(t6.getSymbole(), t6);
    Transition t7 = new Transition(fin, "a", fin, "L", "a");
    Transition t8 = new Transition(fin, "b", deux, "L", "b");
	    fin.addTransition(t7.getSymbole(), t7);
	    fin.addTransition(t8.getSymbole(), t8);

    //a recup sur IHM
    String entreeUser = "ba";

    TeteLecture tl = new TeteLecture(entreeUser, depart);
    System.out.println(tl.toString());
    
    //DEBUT parcours du ruban    
    String leSymbole = tl.lectureSymbole();
    Etat etatActuel = tl.getEtatActuel();
    Transition transAFaire = etatActuel.getTransi(leSymbole);
    
    while (!tl.lectureSymbole().equals("")) {//tant que le symbole n'est pas blanc
    	leSymbole = tl.lectureSymbole();
        etatActuel = tl.getEtatActuel();
        transAFaire = etatActuel.getTransi(leSymbole);
    	
    	tl.reecriture(transAFaire.getReec());
    	tl.moveIndex(transAFaire.getLR());
    	tl.setEtat(transAFaire.getArrive());
    	
    	//etat de la tete de lecture
    	System.out.println(tl.toString());
    }
    //FIN aprcours du ruban	
    	
    //DEBUT resultat
    String resultat = "RESULTAT:\n";
    
    if (tl.getEtatActuel().equals(fin)) {
    	resultat += "ce mot est accepté";
    } else {
    	resultat += "ce mot n'est pas accepté";
    }
    
    //FIN resultat
    
    System.err.println(resultat);


  }
}
