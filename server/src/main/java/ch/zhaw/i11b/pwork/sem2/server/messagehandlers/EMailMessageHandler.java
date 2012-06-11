package ch.zhaw.i11b.pwork.sem2.server.messagehandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.zhaw.i11b.pwork.sem2.beans.Message;

/**
 * @author oups
 *
 */
public class EMailMessageHandler extends AbstractMessageHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(EMailMessageHandler.class);
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
		logger.debug("Send EMail to {} with subject:\nMessage from MultiChannel\n message:\n {}", this.target, this.message.message);
		return true;
	}

}
