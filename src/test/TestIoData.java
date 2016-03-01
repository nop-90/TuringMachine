package test;
import java.awt.SystemColor;

import turing.*;
import io.*;

public class TestIoData {

  public static void main (String[] args) {

    IOMachine iom = new IOMachine();
    iom.parseProgram("test.txt");
    
    Etat depart = iom.getState(iom.getStartingStates());
    Etat fin = iom.getState(iom.getStoppingStates());

    //a recup sur IHM
    String entreeUser = "ba";

    TeteLecture tl = new TeteLecture(entreeUser, depart);
    System.out.println(tl.toString());
    
    //DEBUT parcours du ruban    
    String leSymbole = tl.lectureSymbole();
    Etat etatActuel = tl.getEtatActuel();
    Transition transAFaire = etatActuel.getTransi(leSymbole);
    
    //if pour un pas a pas
    //while pour tout executer
    while (!tl.lectureSymbole().equals("") && !tl.getEtatActuel().getId().equals(fin.getId())) {//tant que le symbole n'est pas blanc || etat final obtenu
    	leSymbole = tl.lectureSymbole();
        etatActuel = tl.getEtatActuel();
        transAFaire = etatActuel.getTransi(leSymbole);
    	
    	tl.reecriture(transAFaire.getReec());
    	tl.moveIndex(transAFaire.getLR());
    	tl.setEtat(transAFaire.getArrive());
    	
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//etat de la tete de lecture
    	System.out.println(tl.toString());
    }
    //FIN parcours du ruban	
    	
    //DEBUT resultat
    String resultat = "----------\nRESULTAT:\n";
    
    if (tl.getEtatActuel().getId().equals(fin.getId())) {
    	resultat += "ce mot est accepté";
    } else {
    	resultat += "ce mot n'est pas accepté";
    }
    
    //FIN resultat
    
    System.out.println(resultat);


  }
}
