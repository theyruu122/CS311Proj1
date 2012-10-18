import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class fsa {
	
	public static void main(String[] args) throws IOException {
		FileInputStream fs = new FileInputStream("src/x.txt");
		DataInputStream di = new DataInputStream(fs);
		BufferedReader br = new BufferedReader(new InputStreamReader(di));

		fsaLanguageParser f = new fsaLanguageParser();
		
		String s;
		
		int count = 1;
		int fsa = 1;
		
		boolean first = true;
		
		while((s = br.readLine()) != null) {
			
			if(s.equals("||||||||||")) {
				count = 1; // reset 
				f = new fsaLanguageParser();
				fsa++;
				first = true;
				continue;
			}
			else if(count == 1) {
				f.parseNumberOfStates(s);
			}
			else if(count == 2) {
				f.parseFinalStates(s);
			}
			else if(count == 3) {
				f.parseAlphabet(s);
			}
			else if(s.length() != 0 && s.charAt(0) == '(') {
				f.parseTransition(s);
			}

			else {
				if(first) {
					f.printSummary(fsa);
					first = false;
				}
				f.parseSample(s);
			}
			count++;
		}
	}

	
}
