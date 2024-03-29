package ch.zhaw.i11b.pwork.sem2.clTools;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * read stdin and format stdout output
 */
public class IO{
	
	/**
	 * Print request and get answer
	 */
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
	
	/**
	 * action wise output:
	 */

	public static void mcWelcome(){
		IO.mcNotify("Welcome to MultiChannel !");
		IO.mcInfo("OOP project of:");
		IO.mcInfo("Felix Marthaler, Marcel Brügger, Cristoffel Gehring");
		IO.mcInfo("Membaz of da I11b crew.");
	}
	public static void mcBye(){
		IO.mcNotify("Good Bye !");
	}
	public static void mcAccpt(String out){
		mcPrint(" ->"+ out+"\n");
		mcBr();
		
	}
	public static void mcPrompt(String out){
		mcPrint("? "+ out+"\n");
		
	}
	public static void mcInfo(String out){
		mcPrint("# "+ out+"\n");
	
	}
	public static void mcRespond(String out){
		mcPrint("   "+ out+"\n");
		
	}
	public static void mcNotify(String out){
		mcPrint("! "+ out +" !\n");

	}
	public static void mcInput(){
		mcPrint("  > ");
	}
	public static void mcBr(){
		mcPrint("\n");
	}
	public static void mcPrint(String out){
		System.out.printf("    %s", out);
	}
}
