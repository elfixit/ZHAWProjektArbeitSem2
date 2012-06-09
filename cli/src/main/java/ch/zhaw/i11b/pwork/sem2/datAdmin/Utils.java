package ch.zhaw.i11b.pwork.sem2.datAdmin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {
	public static String getStdin(String prompt) {
		String nr = "foo";
		
		// TODO Auto-generated method stub
		mcPrint(prompt);	 
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    nr = bufferRead.readLine();
		}
		catch(IOException e)
		{
			mcPrint("Error reading your input!");
		} 
		return nr;
	}
	
	public static void mcPrint(String out){
		System.out.printf("$ %s\n", out);
	}
}
