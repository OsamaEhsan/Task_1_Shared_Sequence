package com.github.antlrjavaparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;


public class SharedSequence {

    static int[][] computeSharedSequenceMatrix(ArrayList<String> listA, ArrayList<String> listB, int m, int n)
    {
        int[][] matrix = new int[m + 1][n + 1];
        int length = 0;
        int row = 0, col = 0;
 
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0)
                    matrix[i][j] = 0;
 
                else if (listA.get(i - 1).equals(listB.get(j - 1))) {
                	matrix[i][j] = matrix[i - 1][j - 1] + 1;
                    if (length < matrix[i][j] ) {
                        length = matrix[i][j];
                        row = i;
                        col = j;
                    }
                } else
                	matrix[i][j] = 0;
            }
        }
 
        if (length == 0) {
            System.out.println("No Common Substring");
            return null;
        }

        ArrayList<String> resultStr = new ArrayList<String>();

        while (matrix[row][col] != 0) {
            resultStr.add(listA.get(row - 1)); 
            --length;
            row--;
            col--;
        }
        return matrix;
    }
    
    private static HashMap<ArrayList<String>,Integer> backtrackAllNew(HashMap<List<String>,Integer> map1,HashMap<List<String>,Integer> map2,int[][] C, ArrayList<String> s1, ArrayList<String> s2, int i, int j){

    	HashMap<ArrayList<String>,Integer> set = new HashMap<ArrayList<String>,Integer>();
    	for(int l=0;l<i;l++)
    	{
    		for(int k=0;k<j;k++)
    		{
    			if(C[l][k]==1)
    			{
    				ArrayList<String> temp= new ArrayList<String>();
    				temp.add(s1.get(l - 1));
    				int tempL=l,tempK=k;
    				while(tempL<i && tempK<j)
    				{
    					if(C[++tempL][++tempK]==0)
    						break;
    					else
    						temp.add(s1.get(tempL-1));
    				}
    				Integer count=0;
    				Integer val1=map1.get(temp);
    				if(val1!=null)
    					count+=val1;
    				Integer val2=map2.get(temp);
    				if(val2!=null)
    					count+=val2;
    				set.put(temp,count);
    			}
    		}
    	}
    	return set;
    }
    
    public static void findOccurences(Map<List<String>, Integer> map,ArrayList<String> str, int length) {
        int limit = str.size() - length + 1;
        for (int i = 0; i < limit; i++) {
            Integer counter = map.get(str.subList(i, i + length));
            if (counter == null) {
                counter = 0;
            }
            map.put( str.subList(i, i + length), ++counter);
        }
    }

    public static HashMap<ArrayList<String>,Integer> mainRun (ArrayList<String> listA, ArrayList<String> listB) 
    {

    	HashMap<List<String>, Integer> mapp1=new HashMap<List<String>, Integer>();
    	for(int i=2;i<listA.size();i++)
    	{
    		findOccurences(mapp1,listA,i);
    	}
    	System.out.println("found occurrences of file 1...");
    	int m = listA.size();
    	int n = listB.size();

    	HashMap<List<String>, Integer> mapp2=new HashMap<List<String>, Integer>();
    	for(int i=2;i<listB.size();i++)
    	{
    		findOccurences(mapp2,listB,i);
    	}
    	System.out.println("found occurrences of file 2...");
    	int[][] sample  = computeSharedSequenceMatrix(listA, listB, m, n);
    	System.out.println("Computed Matrix...");
    	HashMap<ArrayList<String>,Integer> MatchedString = backtrackAllNew(mapp1,mapp2,sample, listA, listB, m, n);
    	System.out.println("Backtracked Matrix...");
    	/*for (Map.Entry<ArrayList<String>, Integer> entry : MatchedString.entrySet()) {

    		System.out.println(entry.getKey()+":"+entry.getValue());
    	}*/
    	System.out.println("-----------------------");
    	return MatchedString;
    }
}
