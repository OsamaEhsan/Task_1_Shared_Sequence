package com.github.antlrjavaparser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


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
    public static HashSet<String> backtrackAll(int[][] C, ArrayList<String> s1, ArrayList<String> s2, int i, int j){
    	HashSet<String> set = new HashSet<String>();
    	for(int l=0;l<i;l++)
    	{
    		for(int k=0;k<j;k++)
    		{
    			if(C[l][k]==1)
	    		{
	    			String temp="";
	    			temp+=s1.get(l - 1);
	    			int tempL=l,tempK=k;
	    			while(tempL<i && tempK<j)
	    			{
	    				if(C[++tempL][++tempK]==0)
	    					break;
	    				else
	    					temp+=s1.get(tempL-1);
	    			}
	    			set.add(temp);
	    		}
    		}
    	}
    	return set;
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
        
        Set<String> setString = backtrackAll (sample, listA, listB, m, n);
        System.out.println(setString);
       

    }
}
