import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SnowmanGameModel {
	private int length;
	private int guesses;
	private String guessedWord = "";
	private int noDuplicateLetters =0;
	private ArrayList<String> dictionary = new ArrayList<String>();
	private ArrayList<Character> guessedLetters = new ArrayList<Character>();

	/**
	 * Initilizes the game model. Sets the length of the word being guessed, and
	 * the number of guesses allowed Creates the game dictionary
	 */
	public SnowmanGameModel() {
		boolean remain = false;
		while (!remain) {
			StdOut.println("Please enter the length of the word you are guessing");
			length = StdIn.readInt();
			if (length > 0 && length < 100) {
				remain = true;
				for (int i = 0; i < length; i++) {
					guessedWord += "_";
				}
			}
		}
		while (remain) {
			StdOut.println("Please enter the number of guesses you want");
			guesses = StdIn.readInt();
			if (guesses > 0 && length < 27) {
				remain = false;
			}
		}
		initializeGameDictionary();
		run();
	}

	// for (String word : dictionary) {
	// StdOut.println(word);
	// }
	public void run() {
		while (guesses > 0) {
			if (guessedWord.equals(dictionary.get(0))) {
				StdOut.println(guessedWord);
				StdOut.println("YOU WIN!");
				break;
			}
			StdOut.println("Guessed Letters: "+guessedLetters.toString());
			StdOut.println("Please enter your guess! You have " + guesses + " guesses left.");
			StdOut.println(guessedWord);
			turn();
			if (guessedLetters.size() > noDuplicateLetters) {
				updateGameDict();
				guesses--;
				noDuplicateLetters++;
			}
			else{
				StdOut.println("Please enter a new letter");
			}
		}
		if (guesses == 0) {
			int k = (int)(Math.random()*dictionary.size());
			StdOut.println("YOU LOSE! The word was: " + dictionary.get(k));
		}
	}

	public void turn() {
		if (!StdIn.isEmpty() && StdIn.readChar() != '\r') {
			char letter = StdIn.readChar();
			for (char l: guessedLetters){
				if(l==letter){
					return;
				}
			}
			guessedLetters.add(letter);
		}
	}

	public void updateGameDict() {
		char newLetter = guessedLetters.get(guessedLetters.size() - 1);
		String pseudoHashCode = "";
		Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

		for (String word : dictionary) {
			for (char letter : word.toCharArray()) {
				if (letter == newLetter) {
					pseudoHashCode += "1";
				} else {
					pseudoHashCode += "0";
				}
			}
			if (map.containsKey(pseudoHashCode)) {
				ArrayList<String> temp = map.get(pseudoHashCode);
				temp.add(word);
				map.put(pseudoHashCode, temp);
			} else {
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(word);
				map.put(pseudoHashCode, temp);
			}
			pseudoHashCode = "";
		}
		int longestEntry = 0;
		String longestEntryKey = "";
		for (Map.Entry<String,ArrayList<String>> m : map.entrySet()) {
			if (m.getValue().size() > longestEntry) {
				longestEntry = m.getValue().size();
				longestEntryKey = m.getKey();
			}
		}
		dictionary.clear();
		for (String word : map.get(longestEntryKey)) {
			dictionary.add(word);
			StdOut.println(word);
		}

		for (int i = 0; i < longestEntryKey.toCharArray().length; i++) {
			char binaryNum = longestEntryKey.toCharArray()[i];
			if (binaryNum == '1') {
				guessedWord = guessedWord.substring(0, i) + newLetter
						+ guessedWord.substring(i + 1);
			}
		}

	}

	public void initializeGameDictionary() {
		try (BufferedReader br = new BufferedReader(new FileReader(
				"enable1.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.length() == length) {
					dictionary.add(line);
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage()); // handle exception
		}
	}

	public int getLength() {
		return length;
	}

	public int getGuesses() {
		return guesses;
	}

	public static void main(String[] args) {
		SnowmanGameModel game = new SnowmanGameModel();
	}

}
