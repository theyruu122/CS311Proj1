import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class fsaLanguageParser {

	private int states;
	private ArrayList<String> fStates;
	private ArrayList<String> alphabet;
	private HashMap<String, String> transitions;
	private ArrayList<String> exactTrans;
	
	public fsaLanguageParser() {
		this.states = 0;
		this.fStates = new ArrayList<String>();
		this.transitions = new HashMap<String, String>();
		this.exactTrans = new ArrayList<String>();
	}

	private boolean isInLanguage(String s) {
		
		if(s.length() == 0) {
			return emptyCase();
		}
		
		String state = "0"; //initial state always 0 
		int count = 0;
		boolean exit = false;
		String symbol = Character.toString(s.charAt(count));
		
		while(!exit) {
			if(s.length() != 1) {	
				symbol = Character.toString(s.charAt(count));
			}
			else {
				count = 0;
			}
			if(count == s.length() - 1) { //Last symbol
				String tranKey = state + symbol;
				if(this.transitions.containsKey(tranKey)) {
					state = this.transitions.get(tranKey);
					System.out.printf("\n\tTransition from %s is %s\n", tranKey,state);
					
					if(this.fStates.contains(state)) { 
						exit = true;
						System.out.printf("\n%s IS in final state\n" , symbol); // DEBUG
						return true;
					}
					else {
						exit = true;
						System.out.printf("\n%s NOT in final state\n" , symbol); // DEBUG
						return false;
					}
				}
				else {
					System.out.printf("\n%s has no transition(from %s) \n" , symbol, state); //DEBUG
					exit = true;
					return false;
				}

			}
			count++;
			if(this.alphabet.contains(symbol)) {
				String tranKey = state + symbol;
				if(this.transitions.containsKey(tranKey)) {
					state = this.transitions.get(tranKey);
					System.out.printf("\n\tTransition from %s is %s\n", tranKey,state);
				}
				else { // no transition; trap
					exit = true;
					System.out.printf("\n%s has no transition from %s at char %d\n" , symbol, state, count); //DEBUG
					return false;
				}
			}
			else { //symbol not in language
				exit = true;
				System.out.printf("\n%s NOT IN LANGUAGE\n" , symbol); //DEBUG
				return false;
			}
			
		}
		
		return false;
	}
	
	
	public void parseSample(String s) {
		
		System.out.print("String: " +s);
		if(this.isInLanguage(s)) {
			System.out.printf(" \n\t\t %s is ACCEPTED\n",s);
		}
		else {
			System.out.printf(" \n\t\t %s is REJECTED\n",s);
		}
	}
	
	/**
	 * Empty case MUST be defined in input as "#" for alphabet and transitions
	 * But should be a blank (empty) line as a test String
	 */
	private boolean emptyCase() {
		String empty = "#";
		
		// Machine only accepts Deterministic FSA
		// If empty string is in language, it must transition from initial (state 0) to final
		String state = "0";
		if(this.alphabet.contains(empty)) {
			String tranKey = state + empty;
			if(this.transitions.containsKey(tranKey)) {
				state = this.transitions.get(tranKey);
				if(this.fStates.contains(state)) { 
					return true;
				}
			}
			else { // no transition; trap
				return false;
			}
			
		}
		return false; // empty case not in alphabet, or has transition
	}

	public void parseTransition(String s) {
		this.exactTrans.add(s);
		String g = s.replace("(", "");
		g = g.replace(")", "");
		String[] t = g.split("\\s");
		
		if (t.length > 3) {
			System.out.println("Invalid Transition");
		}

		String key = t[0] + t[1];
		this.transitions.put(key, t[2]);

	}

	public void parseAlphabet(String s) {
		this.alphabet = new ArrayList<String>(Arrays.asList(s.split("\\s")));
	}

	public void parseFinalStates(String s) {
		String[] tokens = s.split("\\s");
		for (String str : tokens) {
			this.fStates.add(str);
		}
	}

	public void parseNumberOfStates(String s) {
		try {
			this.states = Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			System.out.printf("Invalid # of States");
		}

	}
	
	public void printSummary(int n) {
		System.out.println("Finite State Automaton #" + n);
		System.out.println("(1)number of states: " + this.states);
		System.out.print("(2)final states: ");
		for(String a : this.fStates) {
			System.out.print(a + " ");
		}
		System.out.println();
		System.out.print("(3)alphabet: ");
		for(String a : this.alphabet) {
			System.out.print(a + " ");
		}
		System.out.println();
		System.out.println("(4)transitions: ");
		for(String a: this.exactTrans) {
			System.out.println("\t" + a);
		}
		System.out.println("(5)strings: ");
		
	}
}
