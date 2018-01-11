
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

    public static void main(String args[]) throws Exception {
    	
    	Controller controller = new Controller();
    	
    	File folder = new File("C:\\Users\\oehsan\\Documents\\code\\Task_1_Shared_Sequence\\src\\test\\resources");
    	File[] listOfFiles = folder.listFiles();
    	
    	ArrayList<String> listA = new ArrayList<String>();
        ArrayList<String> listB = new ArrayList<String>();
    	
        for (int i = 0; i < listOfFiles.length-1; i++) {
    		for (int j = i+1; j < listOfFiles.length; j++) {
    			
    			listA = controller.getLexicalTokens(listOfFiles[i].getName());
    	        listB = controller.getLexicalTokens(listOfFiles[j].getName());
    	       
    	        System.out.println(listOfFiles[i].getName() + " , " + listOfFiles[j].getName());
    	        
    	        SharedSequence sharedSequence = new SharedSequence();
    	        HashMap<ArrayList<String>,Integer> matchedString = sharedSequence.Simulate(listA, listB);
    	        controller.UpdateCount(matchedString);
			}
    	}
        controller.WritingDataToCSV("C:\\Users\\oehsan\\Documents\\data.csv");
       
    }
}
