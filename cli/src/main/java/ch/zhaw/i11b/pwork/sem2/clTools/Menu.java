package ch.zhaw.i11b.pwork.sem2.clTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import ch.zhaw.i11b.pwork.sem2.beans.Message;
import ch.zhaw.i11b.pwork.sem2.beans.Messages;
import ch.zhaw.i11b.pwork.sem2.test.*;

/**
 * menu tools, show menu, process input
 * @author boffel
 */
public class Menu {
	/**
	 * welcome menu options
	 */
	protected static final Map<String, String> nullO = new HashMap<String, String>(){
		{
		put("n", "[n] create a new message");
		put("v", "[v] view server status");
		put("q", "[q] quit");
		}
	};

	/**
	 * main menu options:
	 */
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

	/**
	 * welcome menu options
	 */
	protected static final Map<String, String> statusO = new HashMap<String, String>(){
		{
		put("f", "[f] view finished messages");
		put("e", "[e] view failed messages");
		put("o", "[o] view opened messages");
		put("c", "[c] view canceled messages");
		put("l", "[l] load server status");
		put("s", "[s] save server status");
		put("q", "[q] quit");
		}
	};
	
	
	/**
	 * start menu when no instance of Message class is available
	 */
	protected static boolean nullMenu(){
		// get input:
		for(String in = ioMenu("null");; in = ioMenu("null")){
			
			if(in.matches("n")){
				return true;
			}
			else if(in.matches("v")){
				IO.mcAccpt(nullO.get(in));
				statusMenu();
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
	
	/**
	 * work with Message class and send message when all is fine
	 */
	protected static void mainMenu(Message mess){
		// get input:
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
				McConnection mcC = McConnection.instance();
				if(mcC.testServer()){
					if(mcC.sendMess(mess)){
						break;
					}
				} 
			}
			else if(in.matches("n")){
				IO.mcAccpt(mainO.get(in));
				mess = MCLtools.newMess();
			}
			else if(in.matches("x")){
				IO.mcAccpt(mainO.get(in));
				//MCLtools.initMess(mess);
				break;
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
	
	/**
	 *  status menu view finished, failed, canceled or open messages 
	 */
	protected static void statusMenu(){
		// get input:
		McConnection McC = McConnection.instance();
		if(!(McC.testServer())){
			return;
		}
		for(String in = ioMenu("status");; in = ioMenu("status")){
			
			if(in.matches("f")){
				IO.mcAccpt(statusO.get(in));
				List<Message> ml = McC.getFinished();
				if(ml.isEmpty()){
					IO.mcRespond("No finished messages");
				}
				else{
					for(Message m : ml){
						IO.mcBr();
						IO.mcInfo("Message id:"+m.id);
						MCLtools.prntMcMess(m);
					}
				}
			}
			else if(in.matches("e")){
				IO.mcAccpt(statusO.get(in));
				List<Message> ml = McC.getFailed();
				if(ml.isEmpty()){
					IO.mcRespond("No failed messages");
				}
				else{
					for(Message m : ml){
						IO.mcBr();
						IO.mcInfo("Message id:"+m.id);
						MCLtools.prntMcMess(m);
					}
				}
			}
			else if(in.matches("o")){	
				IO.mcAccpt(statusO.get(in));
				List<Message> ml = McC.getOpened();
				if(ml.isEmpty()){
					IO.mcRespond("No open messages");
				}
				else{
					for(Message m : ml){
						IO.mcBr();
						IO.mcInfo("Message id:"+m.id);
						MCLtools.prntMcMess(m);
					}
				}
			}
			else if(in.matches("c")){	
				IO.mcAccpt(statusO.get(in));
				List<Message> ml = McC.getCancelled();
				if(ml.isEmpty()){
					IO.mcRespond("No cancelled messages");
				}
				else{
					for(Message m : ml){
						IO.mcBr();
						IO.mcInfo("Message id:"+m.id);
						MCLtools.prntMcMess(m);
					}
				}
			}
			else if(in.matches("s")){
				String path = IO.getStdin("enter relative/absolute path to file to save:");
				File file = new File(path);
				if (file.exists() | file.getParentFile().isDirectory()) {
					FileOutputStream fos = null;
					ObjectOutputStream oos = null;
					try {
						fos = new FileOutputStream(file);
						oos = new ObjectOutputStream(fos);
						oos.writeObject(McC.getMessages());
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							oos.close();
							fos.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					IO.mcRespond("Server state saved in: "+file.getAbsolutePath());
				}
				else {
					IO.mcNotify("Invalid file: "+file.getAbsolutePath());
				}
			}
			else if(in.matches("l")){
				String path = IO.getStdin("enter relative/absolute path to file to load:");
				File file = new File(path);
				if (file.isFile()){
					FileInputStream fis = null;
					ObjectInputStream ois = null;
					try {
						fis = new FileInputStream(file);
						ois = new ObjectInputStream(fis);
						McC.setMessages((Messages)ois.readObject());
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							ois.close();
							fis.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					IO.mcRespond("Server status loaded from file: "+file.getAbsolutePath());
				}
				else {
					IO.mcNotify("The File doesn't exist.. Error!");
				}
			}
			else if(in.matches("q")){
				IO.mcInfo("quit");
				break;
			}
			else{
				IO.mcNotify(in +" is not a valid option.");
			}
		}
	}

	/**
	 * print null- or main menu
	 */
	public static String ioMenu(String menu){
		
		if(menu.matches("main")){
			IO.mcBr();
			IO.mcInfo("Message Menu");
			for(String str : mainO.values()){
				IO.mcRespond(str);
			}
			return IO.getStdin("");
		}
		
		else if(menu.matches("null")){
			IO.mcBr();
			IO.mcInfo("Start Menu");
			for(String str : nullO.values()){
				IO.mcRespond(str);
			}
			return IO.getStdin("");
		}
		
		else if(menu.matches("status")){
			IO.mcBr();
			IO.mcInfo("Status");
			for(String str : statusO.values()){
				IO.mcRespond(str);
			}
			return IO.getStdin("");
		}
		else return "";
	}

}
