package ch.zhaw.i11b.pwork.sem2.clTools;

import java.util.HashMap;
import java.util.Map;
import ch.zhaw.i11b.pwork.sem2.beans.Message;
import ch.zhaw.i11b.pwork.sem2.test.*;


public class Menu {
	// welcome menu options
	protected static final Map<String, String> nullO = new HashMap<String, String>(){
		{
		put("n", "[n] create a new message");
		put("v", "[v] view server status");
		put("q", "[q] quit");
		}
	};

	// main menu options:
	protected static final Map<String, String> mainO = new HashMap<String, String>(){
		{
		put("e", "[e] edit sender");
		put("a", "[a] add addressees");
		put("t", "[c] modify content");
		put("d", "[d] modify date");
		put("r", "[r] toggle reminder");
		put("p", "[p] print message details");
		put("s", "[s] send message");
		put("x", "[x] discard message");
		put("q", "[q] quit message menu");
		put("t", "[t] set test values");
		}
	};
	
	
	protected static boolean nullMenu(){
		IO.mcBr();
		
		// get input:
		for(String in = ioMenu("null");; in = ioMenu("null")){
			
			if(in.matches("n")){
				return true;
			}
			else if(in.matches("v")){
				IO.mcAccpt(nullO.get(in));
			}
			else if(in.matches("q")){	
				IO.mcAccpt(nullO.get(in));
				return false;
			}
			else{
				IO.mcNotify(in +" is not a valid option.");
			}
		}
	}

	
	protected static void mainMenu(Message mess){
		

//		// get input:
		for(String in = ioMenu("main");; in = ioMenu("main")){
			if(in.matches("e")){
				IO.mcAccpt(mainO.get(in));
				MCLtools.setFrom(mess);
			}
			else if(in.matches("a")){
				IO.mcAccpt(mainO.get(in));
				MCLtools.addTarget(mess);
			}
			else if(in.matches("c")){
				IO.mcAccpt(mainO.get(in));
				MCLtools.setContent(mess);
			}
			else if(in.matches("d")){
				IO.mcAccpt(mainO.get(in));
				MCLtools.setDate(mess);
			}
			else if(in.matches("r")){
				IO.mcAccpt(mainO.get(in));
				MCLtools.toggleRem(mess);
			}
			else if(in.matches("p")){
				IO.mcAccpt(mainO.get(in));
				MCLtools.prntMcMess(mess);
			}
			else if(in.matches("s")){
				IO.mcAccpt(mainO.get(in));
				McConnection.instance().sendMess(mess);
			}
			else if(in.matches("n")){
				IO.mcAccpt(mainO.get(in));
				mess = MCLtools.newMess();
			}
			else if(in.matches("x")){
				IO.mcAccpt(mainO.get(in));
				MCLtools.initMess(mess);
			}
			else if(in.matches("q")){
				IO.mcInfo("quit");
				break;
			}
			else if(in.matches("t")){
				IO.mcAccpt(mainO.get(in));
				MCLtest.addTestValues(mess);
			}
			else{
				IO.mcNotify(in +" is not a valid option.");
			}
		}
	}
	// print menu:
	public static String ioMenu(String menu){
		
		
		if(menu.matches("main")){
			IO.mcBr();
			IO.mcInfo("Main Menu");
			for(String str : mainO.values()){
				IO.mcRespond(str);
			}
			return IO.getStdin("");
		}
		
		else if(menu.matches("null")){
			IO.mcBr();
			IO.mcInfo("Menu");
			for(String str : nullO.values()){
				IO.mcRespond(str);
			}
			return IO.getStdin("");
		}
		else return "";
	}

}
