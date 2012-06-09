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
	
	/**
	 * @param target
	 * @param message
	 */
	public AbstractMessageHandler(String target, Message message) {
		this.target = target;
		this.message = message;
	}
	
	/* (non-Javadoc)
	 * @see ch.zhaw.i11b.pwork.sem2.server.messagehandlers.IMessageHandler#send()
	 */
	abstract public boolean send();

}
