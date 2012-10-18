/**
 * Date:   October 18, 2012
 * Course: CS311-01
 * 
 * Description: 
 *      Universal Finite State Automata
 */

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

    public void parseSample(String s) {

        String b = s;
        if (b.length() == 0) {
            b = "{empty string}";
        }
        if (this.isInLanguage(s)) {
            System.out.printf("%-34s %32s\n", b, "ACCEPTED");
        }
        else {
            System.out.printf("%-34s %32s\n", b, "REJECTED");
        }
    }

    /**
     * Empty case must be defined as "#" in alphabet and transitions.
     */
    private boolean emptyCase() {
        String empty = "#";
        String state = "0";
        if (this.alphabet.contains(empty)) {
            String tranKey = state + empty;
            if (this.transitions.containsKey(tranKey)) {
                state = this.transitions.get(tranKey);
                if (this.fStates.contains(state)) {
                    return true;
                }
            }
            else { // no transition; trap
                return false;
            }
        }
        return false;
    }

    /**
     * Parse transitions, which must appear as a triple between parenthesis.
     * Transitions are stored in a map as (state+symbol) to (next_state)
     */
    public void parseTransition(String s) {
        this.exactTrans.add(s);
        String g = s.replace("(", "");
        g = g.replace(")", "");
        String[] t = g.split("\\s");

        if (t.length != 3) { // must be a triple
            System.out.println("Invalid Transition");
        }

        String key = t[0] + t[1];
        this.transitions.put(key, t[2]);
    }

    public void parseAlphabet(String s) {
        this.alphabet = new ArrayList<String>(Arrays.asList(s.split("\\s")));
    }

    /**
     * Final states held in list. Instead of number identifiers, simply check if
     * list contains the state when checking for acceptance.
     */
    public void parseFinalStates(String s) {
        String[] tokens = s.split("\\s");
        for (String str : tokens) {
            this.fStates.add(str);
        }
    }

    /**
     * Parse number of states as per the directions, but useless for this
     * implementation.
     */
    public void parseNumberOfStates(String s) {
        try {
            this.states = Integer.parseInt(s);
        }
        catch (NumberFormatException nfe) {
            System.out.printf("Invalid # of States");
        }
    }

    public void printSummary(int n) {
        System.out.println("\nFinite State Automaton #" + n);
        System.out.println("1. Number Of States: " + this.states);
        System.out.print("2. Final States: ");
        for (String a : this.fStates) {
            System.out.print(a + " ");
        }
        System.out.println();
        System.out.print("3. Alphabet: ");
        for (String a : this.alphabet) {
            System.out.print(a + " ");
        }
        System.out.println();
        System.out.println("4. Transitions: \n");
        for (int i = 0; i < this.exactTrans.size(); ++i) {
            System.out.printf("%s  ", this.exactTrans.get(i));
            if ((i + 1) % 10 == 0) {
                System.out.println();
            }
        }
        System.out.println("\n\n5. Strings:\n ");

    }

    /**
     * Check if given string is in the language. Begin at default initial state
     * (0), loop through characters in string to test against alphabet and
     * transitions
     */
    private boolean isInLanguage(String s) {

        if (s.length() == 0) {
            return emptyCase();
        }

        String state = "0"; // initial state always 0
        int count = 0;
        boolean exit = false;
        String symbol = Character.toString(s.charAt(count));

        while (!exit) {
            if (s.length() != 1) {
                symbol = Character.toString(s.charAt(count));
            }
            else {
                count = 0;
            }
            if (count == s.length() - 1) { // Last symbol
                String tranKey = state + symbol;
                if (this.transitions.containsKey(tranKey)) {
                    state = this.transitions.get(tranKey);
                    if (this.fStates.contains(state)) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else {
                    return false;
                }
            }
            count++;
            if (this.alphabet.contains(symbol)) {
                String tranKey = state + symbol;
                if (this.transitions.containsKey(tranKey)) {
                    state = this.transitions.get(tranKey);
                }
                else { // no transition; trap
                    return false;
                }
            }
            else { // symbol not in language
                return false;
            }
        }
        return false;
    }
}
