package ch.zhaw.i11b.pwork.sem2.server.messagehandlers;

import ch.zhaw.i11b.pwork.sem2.beans.Message;

public abstract class AbstractMessageHandler implements IMessageHandler {

	protected String target = "";
	protected Message message;
	
	public AbstractMessageHandler(String target, Message message) {
		this.target = target;
		this.message = message;
	}
	
	abstract public boolean send();

}
