package ch.zhaw.i11b.pwork.sem2.server.resources;

import com.sun.jersey.spi.resource.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import ch.zhaw.i11b.pwork.sem2.server.controller.MessageController;
import ch.zhaw.i11b.pwork.sem2.beans.Message;
import ch.zhaw.i11b.pwork.sem2.beans.Messages;

//The Java class will be hosted at the URI path "/messages"
/**
 * @author  oups
 */
@Singleton
@Path("/messages")
public class MessagingService {

	/**
	 * @uml.property  name="messages"
	 * @uml.associationEnd  
	 */
	protected MessageController controller = MessageController.Instance();

	// The Java method will process HTTP GET requests
	/**
	 * @return
	 * @uml.property  name="messages"
	 */
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized Messages getMessages() {
	    // Return some cliched textual content
		return this.controller.getMessages();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized Message sendMessage(Message msg) {
		this.controller.addMessage(msg);
		return msg;
	}
	
	/**
	 * update Messages.. integrates:
	 * 
	 *   * All open messages to the queue
	 *   * adds finished,cancled,errors to the list
	 * @param msgs
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized Messages updateMessages(Messages msgs) {
		this.controller.loadMessages(msgs);
		return this.controller.getMessages();
	}
	
}