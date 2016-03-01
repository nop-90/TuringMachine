package ihm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Reactions implements ActionListener {
	
	private FenetrePrinc laFen;
	
	public Reactions( FenetrePrinc f ){
		this.laFen = f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		//Réactions sur les boutons
		if(source == this.laFen.getStartButton()){
			this.laFen.startTuring();
		}
		else if(source == this.laFen.getStopButton()){
			this.laFen.stopTuring();
		}
		else if(source == this.laFen.getInitButton()){
			this.laFen.initTuring("test.txt");
		}
		else if(source == this.laFen.getSave()){
			this.laFen.sauver();
		}
		else if(source == this.laFen.getCharger()){
			this.laFen.charger();
		}
		/*
		else if(source == this.laFen.getRunButton()){
			//this.laFen.runTuring();
		}
		*/
		
		
		//Réactions sur les menus
		if(source == this.laFen.getAutoMenuItem()){
			this.laFen.autoMode();
		}
		else if(source == this.laFen.getPasMenuItem()){
			this.laFen.pasAPasMode();
		}
		else if(source == this.laFen.getEtatMenuItem()){
			this.laFen.EtatParEtatMode();
		}

		
	}

	
}
