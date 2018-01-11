package com.github.antlrjavaparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


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
 
                else if (listA.get(i - 1) == listB.get(j - 1)) {
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
    
    private static HashMap<String,Integer> backtrackAllNew(HashMap<String,Integer> map1,HashMap<String,Integer> map2,int[][] C, ArrayList<String> s1, ArrayList<String> s2, int i, int j){

    	HashMap<String,Integer> set = new HashMap<String,Integer>();
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
    
    public static void findOccurences(Map<ArrayList<String>, Integer> map,ArrayList<String> str, int length) {
        int limit = str.size() - length + 1;
        for (int i = 0; i < limit; i++) {
        	ArrayList<String> sub = new ArrayList<String>(str.subList(i, i + length));
            Integer counter = map.get(sub);
            if (counter == null) {
                counter = 0;
            }
            map.put(sub, ++counter);
        }
      
    }

    public static void main (String[] args) 
    {
    	ArrayList<String> listA = new ArrayList<String>();
    	listA.add("for");
    	listA.add("(");
    	listA.add("int");
    	listA.add("i");
    	listA.add("=");
    	listA.add("0");
    	listA.add("for");
    	listA.add("(");
    	listA.add("int");
    	listA.add("i");
    	listA.add("=");
    	listA.add("0");
    	listA.add(";");
    	listA.add("i");
    	listA.add(">");
    	listA.add("10");

    	Map<ArrayList<String>, Integer> mapp1=new HashMap<ArrayList<String>, Integer>();
    	for(int i=2;i<listA.size();i++)
    	{
    		findOccurences(mapp1,listA,i);
    	}
    	HashMap <String, Integer> map1=new HashMap<String, Integer>();
    	for (Map.Entry<ArrayList<String>, Integer> entry : mapp1.entrySet()) {
    		ArrayList<String> list = entry.getKey();
    		String temp="";
    		for(int j=0;j<list.size();j++)
    			temp+=list.get(j);
    		map1.put(temp, entry.getValue());
    	}

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
    	listB.add("=");
    	listB.add("0");
    	listB.add(";");
    	int m = listA.size();
    	int n = listB.size();

    	Map<ArrayList<String>, Integer> mapp2=new HashMap<ArrayList<String>, Integer>();
    	for(int i=2;i<listB.size();i++)
    	{
    		findOccurences(mapp2,listB,i);
    	}
    	HashMap <String, Integer> map2=new HashMap<String, Integer>();
    	for (Map.Entry<ArrayList<String>, Integer> entry : mapp2.entrySet()) {

    		ArrayList<String> list = entry.getKey();
    		String temp="";
    		for(int j=0;j<list.size();j++)
    			temp+=list.get(j);

    		map2.put(temp, entry.getValue());
    	}
    	int[][] sample  = computeSharedSequenceMatrix(listA, listB, m, n);
    	HashMap<String,Integer> MatchedString = backtrackAllNew(map1,map2,sample, listA, listB, m, n);
    	for (Map.Entry<String, Integer> entry : MatchedString.entrySet()) {

    		System.out.println(entry.getKey()+":"+entry.getValue());
    	}
    	System.out.println("-----------------------");

    }
}
