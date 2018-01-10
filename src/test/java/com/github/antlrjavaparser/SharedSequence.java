package com.github.antlrjavaparser;

import java.util.ArrayList;

public class SharedSequence {

    static int[][] computeSharedSequenceMatrix(ArrayList<String> listA, ArrayList<String> listB, int m, int n)
    {
        int[][] LCSmatrix= new int[m + 1][n + 1];

        int length = 0;
 
        int row = 0, col = 0;
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
            	
                if (i == 0 || j == 0)
                    LCSmatrix[i][j] = 0;
 
                else if (listA.get(i - 1).equals(listB.get(j - 1)) && LCSmatrix[i-1][j-1] < (j - i)) {
                	
                    LCSmatrix[i][j] = LCSmatrix[i - 1][j - 1] + 1;
                    if (length < LCSmatrix[i][j] ) {
                        length = LCSmatrix[i][j];
                        row = i;
                        col = j;
                    }
                } else {
                	LCSmatrix[i][j] = 0;
                }
            }
        }
        if (length == 0) {
            System.out.println("No Common Substring");
            return null;
        }

        ArrayList<String> resultList = new ArrayList<String>();

        while (LCSmatrix[row][col] != 0) {
            resultList.add(listA.get(row - 1)); 
            --length;
            row--;
            col--;
        }

        for (int i = resultList.size()-1; i >= 0 ; i--) {
		}
        return LCSmatrix;
    }

    public static void main (String[] args) 
    {
    	ArrayList<String> listA = new ArrayList<String>();
    	listA.add("and");
    	listA.add(">");
    	listA.add("10");
    	listA.add("for");
    	listA.add("(");
    	listA.add("int");
    	listA.add("i");
    	listA.add("=");
    	listA.add("0");
    	listA.add(";");
    	listA.add("j");
    	
    	ArrayList<String> listB = new ArrayList<String>();
        
    	
    	listB.add("for");
    	listB.add("(");
    	listB.add("string");
    	listB.add("j");
    	listB.add("=");
    	listB.add("0");
    	listB.add(";");
    	listB.add("j");
    	listB.add(">");
    	listB.add("10");
        
        int m = listA.size();
        int n = listB.size();
        int[][] sample  = computeSharedSequenceMatrix(listA, listB, m, n);
        
    }
}
