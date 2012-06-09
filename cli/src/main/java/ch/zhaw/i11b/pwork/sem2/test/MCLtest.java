package ch.zhaw.i11b.pwork.sem2.test;

import ch.zhaw.i11b.pwork.sem2.clTools.*;
import ch.zhaw.i11b.pwork.sem2.beans.Message;
import ch.zhaw.i11b.pwork.sem2.beans.Target;


public class MCLtest {

	/**
	 * Run McClient
	 */
	public static void main(final String[] args) {
		MCLtools.go();
	}
	
	/**
	 * Add test targets
	 */
	public static void addTestValues(Message m){
		m.targets.add(new Target("SMS", "+41 593 571 12 02"));
	      m.targets.add(new Target("SMS", "+0 079 571 45 02"));
	      m.targets.add(new Target("SMS", "+977 123 571 12 02"));
	      m.targets.add(new Target("SMS", "+21 571 12 02"));
	      m.targets.add(new Target("SMS", "+34 571 12 02"));     
	      m.targets.add(new Target("MMS", "+33 571 12 02"));
	      m.targets.add(new Target("MMS", "+768 571 45 02"));
	      m.targets.add(new Target("MMS", "+12 571 12 02"));
	      m.targets.add(new Target("MMS", "+32 571 12 02"));
	      m.targets.add(new Target("MMS", "+23 571 12 02"));
	      m.targets.add(new Target("Email", "a@b.com"));
	      m.targets.add(new Target("Email", "hj@asdf.ch"));
	      m.targets.add(new Target("Email", "jhsdfasdfa@asrjh.om"));
	     m.targets.add(new Target("Email", "uiowerjko@sdf.fr"));
	     m.targets.add(new Target("Email", "irjg83@dfsa.br"));
	}

}
