package com.github.antlrjavaparser;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.Token;

import com.opencsv.CSVWriter;

public class Controller {

	ArrayList<SharedCount> sharedCount = new ArrayList<SharedCount>();

	/** This function given the lexical tokens of a java file
	 * @param filename
	 * @return
	 */
	public ArrayList<String> getLexicalTokens(String filename) {
		InputStream in = Main.class.getClassLoader().getResourceAsStream(filename);

		if (in == null) {
			System.err.println("Unable to find test file.");
			return null;
		}
		Java7Lexer lex = null;

		try {
			lex = new Java7Lexer(new ANTLRInputStream(in));
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<String> lexicalTokens = new ArrayList<String>();
		Token token = null;

		while ((token = lex.nextToken()) != null) {

			if (token.getType() == Token.EOF || token.getText() == "\n") {
				break;
			}

			if (token.getChannel() == Token.HIDDEN_CHANNEL) {
				continue;
			}
			lexicalTokens.add(token.getText());
		}
		lex.reset();
		return lexicalTokens;
	}
	
	/**  This function updates the central count of the sequences after the computation between two files is complete
	 * @param setString
	 */
	public void UpdateCount(HashMap<ArrayList<String>,Integer> computedMapWithCounts) {
		boolean check = true;
		for (Map.Entry<ArrayList<String>, Integer> entry : computedMapWithCounts.entrySet()) {
			//String sampleString = String.join(" ", entry.getKey() );
			if(entry.getKey().size() > 1) {
				for (int i = 0; i < sharedCount.size(); i++) {
					if(sharedCount.get(i).equals(entry.getKey())) {
						sharedCount.get(i).setCount(sharedCount.get(i).getCount() + entry.getValue());
						check = false;
					}
				}
				if(check) {
					SharedCount sc = new SharedCount(entry.getKey(), entry.getValue());
					sharedCount.add(sc);
				}
			}	
		}
	}
	
	/** This function calculates the score by the given formula
	 * @param sequence
	 * @param count
	 * @return
	 */
	public double CalculateScore(ArrayList<String> sequence, int count) {
		return (Math.log(sequence.size())/Math.log(2)) * (Math.log(count)/Math.log(2));
	}
	
	/** This function writes the data to CSV file according to the given format
	 * @param path
	 */
	public void WritingDataToCSV(String path) {
		try 
		{    
			Writer writer = Files.newBufferedWriter(Paths.get(path));

			CSVWriter csvWriter = new CSVWriter(writer,
					CSVWriter.DEFAULT_SEPARATOR,
					CSVWriter.NO_QUOTE_CHARACTER,
					CSVWriter.DEFAULT_ESCAPE_CHARACTER,
					CSVWriter.DEFAULT_LINE_END);

			String[] headerRecord = {"Score", "Tokens", "Count", "Source Code"};
			csvWriter.writeNext(headerRecord);
			for (SharedCount shared : sharedCount) {
				if(shared.getCount() > 1 ) {
					double score = CalculateScore(shared.getSequence(), shared.getCount());
					csvWriter.writeNext(new String[]{Double.toString(score), String.valueOf(shared.getSequence().size()), String.valueOf(shared.count), "\"" +String.join(" ", shared.getSequence()) });
				}		
			}
			csvWriter.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	
	
}
