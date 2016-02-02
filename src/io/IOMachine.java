package io;
import turing.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class IOMachine {
	/**
	 * startState : array containing starting states
	 * stopState : array containing stopping state
	 * symbols : contains the alphabet of the automate
	 * parsedProgram : contains the automate line per line splitted in 3 parts (in, symbol, out)
	 */
	private String[] startState, stopState, symbols;
	private HashMap<String,Etat> stateList;
	private ArrayList<Transition> parsedProgram;
	
	public void parseProgram(String scriptFile) {
		BufferedReader bufferedReader = null;
		String[] instr = null;
		stateList = new HashMap<String,Etat>();
		parsedProgram = new ArrayList<Transition>();
		try {
		    bufferedReader = new BufferedReader(new FileReader(scriptFile));
		    String s = "";
		    int line = 0;
		    while ((s = bufferedReader.readLine()) != null) {
		    	if (line == 0)
		    		symbols = s.split(",");
		    	else if (line == 1) {
		    		String[] states = s.split(",");
		    		for (int i=0; i<states.length; i++) {
		    			stateList.put(states[i], new Etat(states[i]));
		    		}
		    	} else if (line == 2) {
		    		startState = s.split(",");
		    	} else if (line == 3) {
		    		stopState = s.split(",");
		    	} else {
		    		if (s != "" && s != "\n") {
			    		instr = s.split("-");
					    parsedProgram.add(new Transition(stateList.get(instr[0]),instr[1],stateList.get(instr[2]),instr[3],instr[4]));
			    	}
		    	}
		    	line++;
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	/**
	 * Get all the transitions
	 * @return Transition array containing exploded lines
	 */
	public ArrayList<Transition> getInstructions() throws Exception {
		if (parsedProgram == null) {
			throw new Exception("No program loaded");
		}
		return parsedProgram;
	}
	public String[] getStartingStates() {
		return startState;
	}
	public String[] getStoppingStates() {
		return stopState;
	}
	public String[] getSymbols() {
		return symbols;
	}
}
