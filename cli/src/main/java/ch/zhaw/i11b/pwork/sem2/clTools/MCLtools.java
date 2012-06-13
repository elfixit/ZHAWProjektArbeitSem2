package ch.zhaw.i11b.pwork.sem2.clTools;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import ch.zhaw.i11b.pwork.sem2.beans.*;

/**
 * Run MC Client
 */
public class MCLtools {
	
	/**
	 * Start MultiChannel client:
	 */
	public static void go(){
		IO.mcWelcome();
		while(cycle());
		IO.mcBye();
	}
	
	/**
	 * life cycle of MultiChannel:
	 */
	public static boolean cycle(){
		if(Menu.nullMenu()){
			Message mess = MCLtools.newMess();
			Menu.mainMenu(mess);
			return true;
		}
		else return false;
		
	}
	
	/**
	 * Create new MultiChannel mess:
	 */
	public static Message newMess(){
		Message nm = new Message();
		initMess(nm);
		setFrom(nm);
		setContent(nm);
		addTarget(nm);
		setDate(nm);
		return nm;
	}
	
	/**
	 * Initialize empty message
	 */
	protected static void initMess(Message m){
		m.from = "";
		m.message = "";
		m.targets = new ArrayList<Target>();
		m.sendtime = new Date();
		m.reminder = false;
	}
	
	/**
	 * Set sender:
	 */
	protected static void setFrom(Message m){
		
		String in = readEmail();
		if(!(in.isEmpty())){
			m.from = in;
		} else{
			IO.mcInfo("No recipient added.");
		}
	}
	
	/**
	 * Set content
	 */
	protected static void setContent(Message m){
		m.message = readMess();
	}
	
	/**
	 * Add target
	 */
	protected static boolean addTarget(Message m){
		String in = IO.getStdin("To you want to add SMS [s], MMS [m], email [e] or printer [p]?");
		if(in.matches("e")){
			in = readEmail();
			if(!(in.isEmpty())){
				m.targets.add(new Target("Email", in));
				return true;
			} else return false;
		}
		else if(in.matches("s")){
			in = readNr();
			if(!(in.isEmpty())){	
				m.targets.add(new Target("SMS", in));
				return true;
			} else return false;
		
		}
		else if(in.matches("m")){
			in = readNr();
			if(!(in.isEmpty())){	
				m.targets.add(new Target("MMS", in));
				return true;
			} else return false;
		
		}
		else if(in.matches("p")){
			in = readPrntName();
			if(!(in.isEmpty())){	
				m.targets.add(new Target("Print", in));
				return true;
			} else return false;
		}
		else{
			IO.mcInfo("No Target added.");
			return false;
		}
	}
	
	/**
	 * set Date 
	 */
	protected static void setDate(Message m){
		Date newDate = readDate();
		if(newDate == null){
			IO.mcInfo("No date set.");
		}else{
			m.sendtime = newDate;
		}
	}
	
	/**
	 * Toggle reminder 
	 */
	protected static void toggleRem(Message m){
		if(m.reminder){
			m.reminder = false;
		}
		else{
			Calendar remTime = Calendar.getInstance(Locale.GERMAN);
			remTime.setTime(m.sendtime);
			remTime.add(Calendar.HOUR, -1);
			if(remTime.after(Calendar.getInstance(Locale.GERMAN))){
				m.reminder = true;
				IO.mcInfo("Reminder set to:\t"+remTime.getTime().toString());
			}
			else{
				IO.mcInfo("You can only set a reminder if the send date is set to at least one hour after the current time.");
				IO.mcRespond("Reminder time is:\t"+remTime.getTime().toString());
				m.reminder = false;
			}
		}
	}	

	/**
	 * Get email from cmd line 
	 */
	private static String readEmail(){
		String in = IO.getStdin("Enter your email adress:");
		if(Valid.emailValid(in)){
			return in;
		} else{
			IO.mcInfo("This is not a valid email adress.");
			return "";
		}
	}

	/**
	 * Get phone nr from cmd line 
	 */
	private static String readNr(){
		
		String countryCode = Valid.ccValid(IO.getStdin("Enter country code. eg \"0041\" for Switzerland:"));
		if(!(countryCode.isEmpty())){
			IO.mcInfo("Your country is: "+ countryCode);
			String nr = Valid.phoneValid(IO.getStdin("Enter your phone # without country prefix:"),countryCode);
			if(!(nr.isEmpty())) {
				return nr;
			} else{
				IO.mcInfo("Not a valid number.");
			}
		} else{
			IO.mcInfo("Not a valid country code.");
		}
		
		return "";
	}
	
	private static String readPrntName(){
		String pn = IO.getStdin("Enter the name of the printer:");
		if(Valid.alphaNumValid(pn)){
			return pn;
		}
		else {
			IO.mcInfo("This is not a alphanumeric name. Not Printer added.");
			IO.mcBr();
			return "";
		}
	}
	
	/**
	 * Get date from cmd line 
	 */
	@SuppressWarnings("finally")
	private static Date readDate(){
		Date newDate = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy HH:mm");
		String date = IO.getStdin("Enter date (dd.MM.yy HH:mm):");
		try{
			newDate = sdf.parse(date);
			if(newDate.before(new Date())){				
				IO.mcNotify("This date has already passed. The message will be sent immetiatly.");
			}
		} catch(ParseException e){
		IO.mcInfo("Not a valid date.");
			newDate = null;
		} finally{
			return newDate;
		}
		
	}
	
	/**
	 * Get content from cmd line 
	 */
	private static String readMess(){
		String mess = IO.getStdin("Enter your Message and hit [Enter] ");
		return mess; 
	}

	/**
	 * print sender to  stdout
	 */
	private static void prntFrom(Message m){
		if(m.from.isEmpty()){
			IO.mcNotify("Recipient empty");
		} else{
			IO.mcRespond("From: "+ m.from);
		}
	}
	
	/**
	 * print content to  stdout
	 */
	private static void prntMess(Message m){
		if(m.message.isEmpty()){
			IO.mcNotify("Message empty");
		} else{
			IO.mcRespond("Message: "+ m.message);
		}
	}
	
	/**
	 * print content to  stdout
	 */
	private static void prntTargets(Message m){
		if(m.targets.isEmpty()){
			IO.mcNotify("Targets: empty.");
		}
		else{
			IO.mcInfo("Targets:");
			prntTargetOfType(m.targets, "Email");
			prntTargetOfType(m.targets, "SMS");
			prntTargetOfType(m.targets, "MMS");
			prntTargetOfType(m.targets, "Print");
		}
	}

	/**
	 * print targets to  stdout
	 */
	private static void prntTargetOfType(List<Target> targets, String type){
		String items = "";
		for(Target t : targets){
			
			if(t.type.matches(type)){
				items = items.concat(t.location+" ");			
			}
		}
		if(!(items.isEmpty())){
			items = type.concat(": "+items);
			IO.mcRespond(items);
		}
	}
	
	/**
	 * print date to  stdout
	 */
	private static void prntDate(Message m){
		if(m.sendtime == null){
			IO.mcNotify("Date: empty.");
		}
		else{
			IO.mcRespond("Date: "+ m.sendtime);
		}
	}
	
	/**
	 * print reminder to  stdout
	 */
	private static void prntRem(Message m){
		IO.mcRespond("Reminder: "+ m.reminder);
	}
	
	/**
	 * print all message details to  stdout
	 */
	public static void prntMcMess(Message m){
		IO.mcInfo("Message details:");
		prntFrom(m);
		prntDate(m);
		prntRem(m);
		prntMess(m);
		prntTargets(m);
	}
}
