package ch.zhaw.i11b.pwork.sem2.clTools;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IO{
	
	// get standard input:
	public static String getStdin(String prompt) {

		String nr = "foo";
		if(!(prompt.isEmpty())){ 
			mcPrompt(prompt);
		}
		
		mcInput();
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    nr = bufferRead.readLine();
		}
		catch(IOException e)
		{
			mcNotify("Error reading your input!");
		} 
		return nr;
	}
	
	// action wise output:
	public static void mcWelcome(){
		IO.mcNotify("Welcome to MultiChannel !");
	}
	public static void mcBye(){
		IO.mcNotify("Good Bye !");
	}
	public static void mcAccpt(String out){
		mcPrint(" "+ out+"\n");
		mcBr();
		
	}
	public static void mcPrompt(String out){
		mcPrint("? "+ out+"\n");
		
	}
	public static void mcInfo(String out){
		mcPrint("# "+ out+"\n");
	
	}
	public static void mcRespond(String out){
		mcPrint("  "+ out+"\n");
		
	}
	public static void mcNotify(String out){
		mcPrint("! "+ out +" !\n");

	}
	public static void mcInput(){
		mcPrint("  >");
	}
	public static void mcBr(){
		mcPrint("\n");
	}
	public static void mcPrint(String out){
		System.out.printf("    %s", out);
	}
}
