/*
 * Copyright (C) 2015 Julio Vilmar Gesser and Mike DeHaan
 *
 * This file is part of antlr-java-parser.
 *
 * antlr-java-parser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * antlr-java-parser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with antlr-java-parser.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.github.antlrjavaparser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.Token;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Main {

	static ArrayList<SharedCount> sharedCount = new ArrayList<SharedCount>();
	
    public static void main(String args[]) throws Exception {
    	
    	File folder = new File("C:\\Users\\oehsan\\Documents\\code\\Task_1_Shared_Sequence\\src\\test\\resources");
    	File[] listOfFiles = folder.listFiles();
    	ArrayList<String> listA = new ArrayList<String>();
        ArrayList<String> listB = new ArrayList<String>();
    	for (int i = 0; i < listOfFiles.length-1; i++) {
    		for (int j = i+1; j < listOfFiles.length; j++) {
    			listA = getLexicalTokens(listOfFiles[i].getName());
    	        listB = getLexicalTokens(listOfFiles[j].getName());
    	        System.out.println(listOfFiles[i].getName() + " , " + listOfFiles[j].getName());
    	        SharedSequence sharedSequence = new SharedSequence();
    	        HashMap<ArrayList<String>,Integer> matchedString = sharedSequence.mainRun(listA, listB);
    	        UpdateCount(matchedString);
			}
    	}
    	    
    	//ArrayList<String> listA = getLexicalTokens("ComplexTest.java");
        //ArrayList<String> listB = getLexicalTokens("ComplexTest2.java");

        
        WritingDataToCSV();
       
    }
    
    public static void UpdateCount(HashMap<ArrayList<String>,Integer> setString) {
    	boolean check = true;
    	for (Map.Entry<ArrayList<String>, Integer> entry : setString.entrySet()) {
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
    public static double CalculateScore(ArrayList<String> sequence, int count) {
		return (Math.log(sequence.size())/Math.log(2)) * (Math.log(count)/Math.log(2));
	}
    public static void WritingDataToCSV() {
    	try 
    	{    
    		Writer writer = Files.newBufferedWriter(Paths.get("C:\\Users\\oehsan\\Documents\\data.csv"));

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
    public static ArrayList<String> getLexicalTokens(String filename) {
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

}
