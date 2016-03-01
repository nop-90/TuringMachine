package ihm;
import io.*;
import turing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import turing.Etat;
import turing.TeteLecture;
import turing.Transition;


public class FenetrePrinc extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton startButton;
	private JButton stopButton;
	private JButton initButton;
	//private JButton runButton;

	private JPanel panBoutons;
	
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem autoMenuItem;
	private JMenuItem pasMenuItem;
	private JMenuItem etatMenuItem;
	private JTextField inputMot;
	
	//Ruban
	private JPanel affichagePanel;
	private JPanel TeteLecturePanel;
	private JScrollPane ruban; 
	private JList<Character> listSymboles;
	
	//zone affichage
	private JTextArea zoneAffi;
	private JTextArea zoneResu;
	
	//panneau nord
	private JTextField alphabet;
	private JTextField nomEtat;
	private JTextField etatDepart;
	private JTextField etatFin;
	private JTextArea lesTransi;
	
	private JMenu menu2;
	private JMenuItem charge;
	private JMenuItem sauver;
	
	
	private int autoModeSpeed = 500;//vitesse par default 0.5 sec
//	private String mot;
	
	private TeteLecture tl;
	private Etat depart;
	private Etat fin;
	private String log;
	
	private Thread tacheExe;
	private boolean stopTacheExe = false;
	/*
	 * 0:automatique
	 * 1:pas a pas
	 * 2:etat par etat
	 */
	private int modeExe;
	
	public FenetrePrinc(){
		super ( "Machine de Turing" );
		this.constructionIHM();
		this.setSize(1100,750);
		this.setVisible(true);
		this.attacherReactions();
		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		
	}
	
	public void constructionIHM() {
		this.panBoutons = new JPanel(new GridLayout(1,3));
	
		getContentPane().add(panBoutons, BorderLayout.SOUTH);
		
		//Initialisation Boutons
		this.startButton = new JButton("Start");
		this.stopButton = new JButton("Stop");
			this.stopButton.setEnabled(false);
		this.initButton = new JButton("Init");

		this.panBoutons.add(this.startButton);
		this.panBoutons.add(this.stopButton);
		this.panBoutons.add(this.initButton);
		
		//Initialisation Menu
		this.menuBar = new JMenuBar();
		this.menu = new JMenu("Vitesse");
		this.menu2 = new JMenu("Fichier");
		this.autoMenuItem = new JMenuItem("Automatique");
		this.pasMenuItem = new JMenuItem("Pas à pas");
		this.etatMenuItem = new JMenuItem("Etat pas état");
		this.inputMot= new JTextField();
		
		this.menuBar.add(this.menu);
		this.menuBar.add(this.menu2);
		this.menu.add(this.autoMenuItem);
		this.menu.add(this.pasMenuItem);
		this.menu.add(this.etatMenuItem);
		this.menuBar.add(this.inputMot);
		
		this.sauver = new JMenuItem("Sauver");
		this.charge = new JMenuItem("Charger");
		this.menu2.add(this.sauver);
		this.menu2.add(this.charge);
		
		this.setJMenuBar(menuBar);
		
		//Initialisation du ruban
		this.affichagePanel = new JPanel(new GridLayout(3,1));	
		this.TeteLecturePanel = new JPanel(new GridLayout(1,3));
		
		this.listSymboles = new JList<Character>();
			Font font = new Font("Font pour ruban", Font.BOLD, 100);
			this.listSymboles.setFont(font);
			//aligner dans la jlist
			DefaultListCellRenderer centerRenderer = new DefaultListCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			this.listSymboles.setCellRenderer(centerRenderer);
			
		this.listSymboles.setVisibleRowCount(1);

		this.listSymboles.setFixedCellHeight(125);
		this.listSymboles.setFixedCellWidth(100);
		this.listSymboles.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		this.listSymboles.setEnabled(false);
		getContentPane().add(this.affichagePanel, BorderLayout.CENTER);
		
		this.ruban = new JScrollPane(listSymboles);
		JScrollBar mdr = ruban.createHorizontalScrollBar();
		ruban.setHorizontalScrollBar(mdr);
		
		//panneau nord
		JPanel panNord = new JPanel();
		panNord.setLayout(new GridLayout(1, 2));
		JPanel panNordGauche = new JPanel(new GridLayout(4,2));
		JPanel panNordDroit = new JPanel(new BorderLayout());
		panNord.add(panNordGauche);
		panNord.add(panNordDroit);
		

		this.alphabet = new JTextField();
		this.nomEtat = new JTextField();
		this.etatDepart = new JTextField();
		this.etatFin = new JTextField();
		this.lesTransi = new JTextArea();
		this.lesTransi.setRows(2);

		
		panNordGauche.add(new JLabel("ALPHABET ( , )"));
		panNordGauche.add(this.alphabet);
		panNordGauche.add(new JLabel("NOM DES ETATS ( , )"));
		panNordGauche.add(this.nomEtat);
		panNordGauche.add(new JLabel("ETAT DE DEPART"));
		panNordGauche.add(this.etatDepart);
		panNordGauche.add(new JLabel("ETAT FINAL"));
		panNordGauche.add(this.etatFin);
		
	

		JScrollPane scroll = new JScrollPane (this.lesTransi,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panNordDroit.add(scroll, BorderLayout.CENTER);
		panNordDroit.add(new JLabel("TRANSITIONS (init-symboleLu-fin-RL-symboleReecriture) (une par ligne)"), BorderLayout.NORTH);

		
		//panneau sud
		JPanel panSud = new JPanel();
		panSud.setLayout(new GridLayout(2, 2));
		panSud.add(new JLabel("ZONE D'AFFICHAGE"));
		this.zoneAffi = new JTextArea();
		this.zoneAffi.setEditable(false);
		panSud.add(this.zoneAffi);
		panSud.add(new JLabel("RESULTAT"));
		this.zoneResu = new JTextArea();
		this.zoneResu.setEditable(false);
		panSud.add(this.zoneResu);
		
		//remplissage zone central
		this.affichagePanel.add(panNord);
		this.affichagePanel.add(this.ruban);
		this.affichagePanel.add(panSud);
		
	}
	
	public void attacherReactions(){
		Reactions reactions = new Reactions(this);
		
		//Attachement des reactions des boutons
		this.startButton.addActionListener(reactions);
		this.stopButton.addActionListener(reactions);
		this.initButton.addActionListener(reactions);
		this.sauver.addActionListener(reactions);
		this.charge.addActionListener(reactions);
		
		//Attachement des reactions des menus
		this.autoMenuItem.addActionListener(reactions);
		this.pasMenuItem.addActionListener(reactions);
		this.etatMenuItem.addActionListener(reactions);
	}

	public void startTuring(){
		initTuring("test.txt");
		this.stopButton.setEnabled(true);
		this.tacheExe = new Thread(){
			public void run() {
				try {
				IOMachine io = new IOMachine();
				//DEBUT parcours du ruban    
			    String leSymbole = tl.lectureSymbole();
			    Etat etatActuel = tl.getEtatActuel();
			    Transition transAFaire = etatActuel.getTransi(leSymbole);
			    
			    
					    //if pour un pas a pas
					    //while pour tout executer
				    //tant que le symbole n'est pas blanc || etat final obtenu
				    while (!tl.lectureSymbole().equals("") && !tl.getEtatActuel().getId().equals(fin.getId())) {
				    	if (stopTacheExe) {
				    		synchronized (this) {
				    			
				    			io.sauver("log.txt", log);
				    			log += "--------------- \n";
					    	    this.wait();
					    	}
				    	}
				    	
				    	leSymbole = tl.lectureSymbole();
				        etatActuel = tl.getEtatActuel();
				        transAFaire = etatActuel.getTransi(leSymbole);
				    	
				        
				        
				        if (transAFaire != null) {
				        	tl.reecriture(transAFaire.getReec());
					    	tl.moveIndex(transAFaire.getLR());
					    	tl.setEtat(transAFaire.getArrive());
					    	log += transAFaire.toString();
				        } else {
				        	System.out.println("Verifier les donnees");
				        	break;
				        }
				    	
				    	remplirList();
				    	
				    	//System.out.println(tl.toString());
				    	zoneAffi.setText(tl.toString());
	
				    	//rapidite d'exe
						Thread.sleep(autoModeSpeed);
						
				    	if (modeExe == 1
				    			||
				    		(modeExe == 2) && (tl.getEtatActuel() != etatActuel)) {
				    		stopTuring();
				    	}
				    }
				    
				  //DEBUT resultat
					io.sauver("log.txt", log);
				    zoneResu.setText(afficherResultat());
				   //FIN resultat
			    } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    //FIN parcours du ruban
			}
		};
		this.tacheExe.start();
	}
	
	public String afficherResultat () {
		String resultat = "";
	    
	    if (tl.getEtatActuel().getId().equals(fin.getId())) {
	    	resultat = "ce mot est accepté";
	    } else {
	    	resultat = "ce mot n'est pas accepté";
	    }
	    
	    return resultat;
	}
	
	public void stopTuring(){
		if (!this.stopTacheExe) {
			//ici on veut bloquer
			this.stopTacheExe = true;
		} else {
			//ici on veut reveiller
			synchronized (this.tacheExe) {
				this.tacheExe.notify();	
			}
			this.stopTacheExe = false;
		}
		this.nomBtn();
	}
	
	public void initTuring(String nomFichier){
		IOMachine iom = new IOMachine();
		try {
			 iom.parseProgram(nomFichier);
		} catch (Exception e) {
			this.zoneResu.setText("une erreur est survenue");
		}
	   
	    this.log = "";
	    this.depart = iom.getState(iom.getStartingStates());
	    this.fin = iom.getState(iom.getStoppingStates());

	    //a recup sur IHM
	    String ruban = this.inputMot.getText();

	    this.tl = new TeteLecture(ruban, this.depart);
	    
	    this.remplirList();
	    this.zoneAffi.setText(tl.toString());
	    this.zoneResu.setText("wait...");
	    this.stopButton.setEnabled(false);
	    
	    this.nomBtn();
	    
	}
	
	public void nomBtn () {
		if (this.stopTacheExe) {
			this.startButton.setText("reinitialise");
			this.stopButton.setText("continuer");
			this.initButton.setText("reinitialise");
		} else {
			this.startButton.setText("start");
			this.stopButton.setText("stop");
		}
	}

	public void autoMode(){
		this.modeExe = 0;
		
	    JOptionPane optionPane = new JOptionPane();
	    JSlider slider = getSlider(optionPane);
	    optionPane.setMessage(new Object[] { "Choix de la valeur (milisecondes):", slider });
	    optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
	    optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
	    JDialog dialog = optionPane.createDialog(this, "Temps d'attente entre chaque transition");
	    dialog.setVisible(true);
	    if(optionPane.getInputValue() != JOptionPane.UNINITIALIZED_VALUE){
		    this.autoModeSpeed = (int)optionPane.getInputValue();
		    System.out.println(this.autoModeSpeed);
	    }
	}
	
	public void pasAPasMode(){
		System.out.println("pas a pas");
		this.modeExe = 1;
	}
	
	public void EtatParEtatMode(){
		System.out.println("etat par etat");
		this.modeExe = 2;
	}
	
	private static JSlider getSlider(final JOptionPane optionPane) {
	    JSlider slider = new JSlider();
	    slider.setMinimum(0);
	    slider.setMaximum(1500);
	    slider.setMajorTickSpacing(500);
	    slider.setValue(500);
	    slider.setPaintTicks(true);
	    slider.setPaintLabels(true);
	    ChangeListener changeListener = new ChangeListener() {
	      public void stateChanged(ChangeEvent changeEvent) {
	        JSlider theSlider = (JSlider) changeEvent.getSource();
	        if (!theSlider.getValueIsAdjusting()) {
	          optionPane.setInputValue(new Integer(theSlider.getValue()));
	        }
	      }
	    };
	    slider.addChangeListener(changeListener);
	    return slider;
	  }
	
	public void remplirList(){
		String str = this.tl.getRuban();
		Character[] tab = new Character[str.length()]; 
		for (int i = 0 ; i < str.length() ; i++) {
			tab[i] = str.charAt(i);
		}
		
		this.listSymboles.setListData(tab);
		this.listSymboles.setSelectedIndex(this.tl.getIndexRub());
		this.moveScrollBarRuban();
		
		this.revalidate();
	}
	
	public void moveScrollBarRuban () {
		this.ruban.getHorizontalScrollBar().setValue(
			(this.tl.getIndexRub()
				//pour etre au milieu avec la scrollbarre
				//partie entiere de division de la taille de l'ecran par la taille d'une cellule de la jlist listSymbole
				-Math.round(this.getWidth()/this.listSymboles.getFixedCellWidth()/2))
			*this.listSymboles.getFixedCellWidth()
		);
	}
	
	public void sauver () {
		System.out.println("sauver");
		String contenu = ""
			+this.alphabet.getText()+"\n"
			+this.nomEtat.getText()+"\n"
			+this.etatDepart.getText()+"\n"
			+this.etatFin.getText()+"\n"
			+this.lesTransi.getText()
		;
		
		IOMachine io = new IOMachine();
		io.sauver("test.txt", contenu);
		
		this.zoneResu.setText("fichier sauvegardé");
	}
	
	public void charger () {
		System.out.println("charger");
		IOMachine io = new IOMachine();
		ArrayList<String> val = io.charger("test.txt");
		
		this.alphabet.setText(val.get(0));
		this.nomEtat.setText(val.get(1));
		this.etatDepart.setText(val.get(2));
		this.etatFin.setText(val.get(3));
		
		String res = "";
		for (int i = 4 ; i < val.size() ; i++) {
			res += val.get(i)+"\n";
		}
		this.lesTransi.setText(res);
		
		this.zoneResu.setText("fichier chargé");
	}

	public JButton getStartButton() {
		return startButton;
	}


	public JButton getStopButton() {
		return stopButton;
	}


	public JButton getInitButton() {
		return initButton;
	}
	
	public JMenuItem getSave () {
		return this.sauver;
	}
	
	public JMenuItem getCharger () {
		return this.charge;
	}

	public JMenuItem getAutoMenuItem() {
		return autoMenuItem;
	}

	public JMenuItem getPasMenuItem() {
		return pasMenuItem;
	}

	public JMenuItem getEtatMenuItem() {
		return etatMenuItem;
	}


}
