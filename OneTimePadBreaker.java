/********************
This creates a series of files, where each file contains a list of all the possible
deciphered outputs for a given character position in all given sentences. The character
position is denoted by the digit in the filename, each line in a deciphered ouput corresponds 
to each sentence, and each output is separated by a line of underscores.
********************/

import java.util.*;
import java.io.*;

public class OneTimePadBreaker {
    public static void main(String[] args) {
        HashMap<Character, Integer> hexVal = new HashMap<Character, Integer>();
        hexVal.put('0', 0); // Create a hexidecimal => integer conversion table
        hexVal.put('1', 1);
        hexVal.put('2', 2);
        hexVal.put('3', 3);
        hexVal.put('4', 4);
        hexVal.put('5', 5);
        hexVal.put('6', 6);
        hexVal.put('7', 7);
        hexVal.put('8', 8);
        hexVal.put('9', 9);
        hexVal.put('A', 10);
        hexVal.put('B', 11);
        hexVal.put('C', 12);
        hexVal.put('D', 13);
        hexVal.put('E', 14);
        hexVal.put('F', 15);

		int[][] cipherColumns = new int[26][7]; // Letter/Key x Sentence
        // Paste each ciphertext sentence below:
		String[] cipherTexts = {"80001E05E3A4D7144A72F5294510C3D09EA6FAFD38A6F3745187",
								"801F1856AEA4CA415F37F56C4E03C284DCA7F0F06DA7F9744287",
								"87001848E3A4D7061E7EF5294314D1958EE3FEFF74A5EB7F5287",
								"9B020305EDA2CC0F4A65FF294402878799AFF3B379A2F97B5287",
								"87121257EBB9CA415F65E3294314D1958EE3EFE171BCFD6E5387",
								"95570240EDBFDC151E73E96A581CC29E88E3F6E038B9F97F5887",
								"921E0356FAEDD607587EE56C5F51CE83DCADF0E738B9F5795D87"};
        // Converts hex ASCII values into decimal, and groups them by letter/key
		for (int t = 0; t < cipherTexts.length; t++) { // LIMIT = 7
			for (int c = 0; c < cipherTexts[t].length(); c += 2) { // LIMIT = 52
				int hex = 16 * hexVal.get(cipherTexts[t].charAt(c));
				hex += hexVal.get(cipherTexts[t].charAt(c+1));
				cipherColumns[c/2][t] = hex;
			}
		}

		try {
			BufferedWriter writer;
			
			for (int i = 0; i < cipherColumns.length; i++) {
				String filename = String.format("OneTimePad_PlainText%d.txt", i);
				writer = new BufferedWriter(new FileWriter(filename));

				String column; // This represents one character from each sentence
				boolean containsInvalidChars; // Filters in letters, numbers, and punctuation
				for (int k = 0; k < 256; k++) { // k = key applied to each letter in each sentence
					containsInvalidChars = false;
					column = "";
					for (int cipherChar : cipherColumns[i]) {
						if (((cipherChar ^ k) < 32) || ((cipherChar ^ k) > 127)) {
							containsInvalidChars = true;
							break;
						}
						column += (char) (cipherChar ^ k) + "\n";
					} // Notepad doesn't display the newline character correctly, so use Notepad++
					if (containsInvalidChars) {
						continue;
					}
                    writer.write("KEY: " + k);
                    writer.newLine();
					writer.write(column); // This writes the output of each key for all sentences
                    writer.write("_______________");
					writer.newLine();
				}
				writer.close();
			}
		}
		catch (Exception e) {
			System.out.println("Error with BufferedWriter!");
		}
    }
}