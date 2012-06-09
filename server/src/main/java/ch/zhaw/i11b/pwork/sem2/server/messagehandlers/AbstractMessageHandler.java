package ch.zhaw.i11b.pwork.sem2.server.messagehandlers;

import ch.zhaw.i11b.pwork.sem2.beans.Message;

/**
 * @author  oups
 */
public abstract class AbstractMessageHandler implements IMessageHandler {

	protected String target = "";
	/**
	 * @uml.property  name="message"
	 * @uml.associationEnd  
	 */
	protected Message message;
	
	public AbstractMessageHandler(String target, Message message) {
		this.target = target;
		this.message = message;
	}
	
	abstract public boolean send();

}
