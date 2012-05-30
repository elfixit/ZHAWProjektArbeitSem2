package ch.zhaw.i11b.pwork.sem2.server.messagehandlers;

import ch.zhaw.i11b.pwork.sem2.beans.Message;

public class EMailMessageHandler extends AbstractMessageHandler {
	
	public EMailMessageHandler(String target, Message message) {
		super(target, message);
	}

	public boolean send() {
		// TODO Auto-generated method stub
		return true;
	}

}
