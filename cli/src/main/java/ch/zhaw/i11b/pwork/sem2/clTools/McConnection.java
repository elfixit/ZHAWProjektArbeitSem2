package ch.zhaw.i11b.pwork.sem2.clTools;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import ch.zhaw.i11b.pwork.sem2.beans.Message;
import ch.zhaw.i11b.pwork.sem2.beans.Messages;
import ch.zhaw.i11b.pwork.sem2.beans.config.JAXBContextResolver;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;


/**
 * Establish connection and send message
 */
public class McConnection {

	private WebResource r;
	private Messages ms;
	
	/**
	 * Singleton instance..
	 */
	static private McConnection _instance = null;
	
	/**
	 * Singleton Impl
	 * @return McConnection
	 */
	static public McConnection instance() {
		if (_instance == null) {
			_instance = new McConnection();
		}
		return _instance;
	}
	
	protected McConnection() { 
		ClientConfig cc = new DefaultClientConfig();
		// use the following jaxb context resolver
    	cc.getClasses().add(JAXBContextResolver.class);
    	Client c = Client.create(cc);
    	r = c.resource(UriBuilder.fromUri("http://localhost/").port(9998).build());
	}
	
	/**
	 * If all required message details are filled send message
	 */
	protected boolean sendMess(Message m){
		if(m.from.isEmpty()){
			IO.mcNotify("Cannot send message, sender missing");
			return false;
		}
		else if(m.message.isEmpty()){
			IO.mcNotify("Cannot send message, content missing");
			return false;
		}
		else if(m.targets.isEmpty()){
			IO.mcNotify("Cannot send message, target missing");
			return false;
		}
		else{
			boolean succ;
			if(m.sendtime.before(new Date())){
				IO.mcNotify("You set a date in the past, message will be sent immediatly");
			}
			try{
				m = r.path("messages").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).put(Message.class, m);
				IO.mcNotify("Message sent, id = "+ m.id);
				succ = true;
			
			} catch (Exception e){
				IO.mcNotify("Message not sent");
				succ = false;
			}
			return succ;
			
		}
	}
	
	public Boolean testServer(){
		try{
			r.path("messages").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).get(Message.class);
			return true;
			
		}catch (Exception e){
			IO.mcNotify("Could not connect to server.");
			// e.printStackTrace();
			return false;
		}
	}

	protected List<Message> getFailed(){
			ms = r.path("messages").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).get(Messages.class);
			return ms.errors;
	}

	protected List<Message> getCancelled(){
			ms = r.path("messages").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).get(Messages.class);
			return ms.canceled;
	}

	protected List<Message> getFinished(){
			ms = r.path("messages").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).get(Messages.class);
			return ms.finished;
	}

	protected List<Message> getOpened(){
			ms = r.path("messages").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).get(Messages.class);
			return ms.open;
	}
	
	protected Messages setMessages(Messages msgs) {
		ms = r.path("messages").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(Messages.class, msgs);
		return ms;
	}
	
}
