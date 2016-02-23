package turing;

import java.util.*;

public class TeteLecture {

	private String ruban;
	private int indexRub;
	private Etat etatActuel;

	public TeteLecture () {
		this.ruban = "";
		this.indexRub = 0;
		this.etatActuel = null;
	}

	public TeteLecture (String entreeUser, Etat etatInit) {
		this.ruban = entreeUser;
		this.indexRub = 0;
		this.setEtat(etatInit);
	}
	
	public String getRuban () {return this.ruban;}
	public int getIndexRub () {return this.indexRub;}
	public Etat getEtatActuel () {return this.etatActuel;}

	/**
	* permet de changer l'etat dans lequel on se trouve
	*/
	public void setEtat (Etat newEtat) {
		this.etatActuel = newEtat;
	}

	/**
	 * deplacement de l'index vers la droite ou la gauche
	 * pas de deplacement vers la gauche si l'index est deja a zero
	 * @param LR est le sens de deplacement
	 */
	public void moveIndex (String LR) {
		if (LR.equals("L")) {
			if (this.indexRub > 0) {
				this.indexRub--;
			}
		} else {
			this.indexRub++;
		}
	}

	/**
	* lecture du symbole sur le ruban a la position actuel rubIndex
	*/
	public String lectureSymbole () {
		String ret = "";
		if (this.indexRub < this.ruban.length()) {
			ret = this.ruban.substring(indexRub, indexRub+1);
		}
		return ret;
	}
	
	public void reecriture (String newSymbole) {
		String debut = this.ruban.substring(0, this.indexRub);
		String fin = this.ruban.substring(indexRub+1, this.ruban.length());
		
		this.ruban = debut+newSymbole+fin;
	}
	
	public String toString () {
		String ret = "----------"
			+"\nle ruban: "+ this.ruban
			+"\nindex du ruban: " + this.indexRub
			+"\netat actuel: " + this.etatActuel.getId()
				;
		return ret;
	}

}
