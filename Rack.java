import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Rack {
	final static Charset ENCODING = StandardCharsets.UTF_8;

	public static void main(String[] arg) {

		String rack, anagramPattern;
		int i, c, length;

		ArrayList<String> sub = new ArrayList<>();
		ArrayList<String> possibleWords;

		Scanner in = new Scanner(System.in);

		System.out.println("Enter a string to print it's all substrings");
		rack = in.nextLine();

		length = rack.length();

		sub = checkForEmptyTile(rack);

		sub = sort(sub);// anagrams of all subsets

		System.out.println("AFter sort:" + sub);// anagrams of all subsets

		Map<String, String> anagram = readFile("C:\\sowpods.txt");

		possibleWords = checkformatch(anagram, sub);

		System.out.println(possibleWords);

		anagramPattern = getBestWord(possibleWords);

		System.out.println(anagram.get(anagramPattern));

		System.out.println("Best word with blank" + getBestWord(findWordWithBlank(anagram, length, sub)));

	}

	private static List<String> findWordWithBlank(Map<String, String> anagram, Integer rackLength,
			ArrayList<String> sub) {
		List<String> result = new ArrayList<>();
		for (String s : anagram.keySet()) {
			if (s.length() == rackLength + 1) {
				for (String word : sub)
					if (s.contains(word))
						result.add(s);
			}
		}
		return result;

	}

	private static ArrayList<String> checkForEmptyTile(String rack) {

		ArrayList<String> sub = new ArrayList<>();
		String filteredRack;

		if (rack.contains("#")) {

			filteredRack = rack.replace("#", "");

			for (Character i = 'A'; i <= 'Z'; ++i) {
				System.out.println(filteredRack + i);
				sub.addAll(subString(filteredRack + i, rack.length()));
			}
		} else
			sub.addAll(subString(rack, rack.length()));

		return sub;
	}

	private static String getBestWord(List<String> wordList) {

		int MAX_SCORE = 0;
		int wordScore = 0;
		String bestWord = "";

		for (String s : wordList) {

			for (int i = 0; i < s.length(); ++i) {
				wordScore += scrabbleScore(s.charAt(i));
			}

			// Check if highest score
			if (wordScore > MAX_SCORE) {

				MAX_SCORE = wordScore;
				bestWord = s;

			}

			wordScore = 0;

		}

		return bestWord;

	}

	private static int scrabbleScore(Character letter) {
		int score = 0;
		char EnglishScoreTable[] = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10 };
		if (letter >= 'a' && letter <= 'z') {
			score += EnglishScoreTable[letter - 'a'];
		}
		return score;
	}

	private static ArrayList<String> checkformatch(Map<String, String> anagram, ArrayList<String> sub) {
		// TODO Auto-generated method stub
		ArrayList<String> possibleWords = new ArrayList<>();
		for (String s : sub) {
			if (anagram.containsKey(s)) {
				possibleWords.add(s);
			}
		}
		return possibleWords;
	}

	private static ArrayList<String> subString(String rack, int length) {
		ArrayList<String> sub = new ArrayList<String>();
		// TODO Auto-generated method stub
		for (int c = 0; c < length; c++) {
			for (int i = 1; i <= length - c; i++) {
				sub.add(rack.substring(c, c + i));
			}
		}
		return sub;
	}

	public static ArrayList<String> sort(ArrayList<String> sub) {
		ArrayList<String> sortedlist = new ArrayList<String>();
		for (String s : sub) {
			char[] c = s.toLowerCase().toCharArray();
			Arrays.sort(c);
			sortedlist.add(String.valueOf(c));
		}
		return sortedlist;
	}

	public static Map<String, String> readFile(String path) {
		Map<String, String> words_count = new HashMap<String, String>();

		try {
			FileInputStream fstream = new FileInputStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;
			while ((strLine = br.readLine()) != null) {

				String sorted = sortLetters(strLine);

				if (words_count.keySet().contains(sorted)) {

					words_count.put(sorted, words_count.get(sorted) + "  " + strLine);
				} else
					words_count.put(sorted, strLine);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return words_count;

	}

	private static String sortLetters(String strLine) {
		// TODO Auto-generated method stub
		char[] chars = strLine.toCharArray();
		Arrays.sort(chars);
		String sorted = new String(chars).toLowerCase();
		return sorted;
	}

	// use the list of anagram lists to compare the substrings
}
