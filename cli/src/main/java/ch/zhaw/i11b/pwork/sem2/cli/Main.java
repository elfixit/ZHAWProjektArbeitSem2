package ch.zhaw.i11b.pwork.sem2.cli;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.core.MediaType;

import ch.zhaw.i11b.pwork.sem2.beans.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		msg.from = "felix@mafix.cc";
		msg.message = "World Domination now.. or never..";
		msg.targets.add(new Target("Email", "irgendwas@marfix.net"));
		msg.targets.add(new Target("Email", "suckers@zhaw.ch"));
		
		Client client = new Client();
		WebResource r = client.resource("http://localhost:9998/messages");
		
		r.accept(MediaType.APPLICATION_JSON);
		r.put(msg);
	}

}
