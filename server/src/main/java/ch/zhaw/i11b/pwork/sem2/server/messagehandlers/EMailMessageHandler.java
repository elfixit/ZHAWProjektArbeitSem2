package ch.zhaw.i11b.pwork.sem2.server.messagehandlers;

import ch.zhaw.i11b.pwork.sem2.beans.Message;

/**
 * @author oups
 *
 */
public class EMailMessageHandler extends AbstractMessageHandler {
	
	/**
	 * @param target
	 * @param message
	 */
	public EMailMessageHandler(String target, Message message) {
		super(target, message);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.i11b.pwork.sem2.server.messagehandlers.AbstractMessageHandler#send()
	 */
	public boolean send() {
		// TODO Auto-generated method stub
		return true;
	}

}
