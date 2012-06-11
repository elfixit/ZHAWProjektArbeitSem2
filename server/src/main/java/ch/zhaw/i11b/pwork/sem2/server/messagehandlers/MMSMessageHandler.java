package ch.zhaw.i11b.pwork.sem2.server.messagehandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.zhaw.i11b.pwork.sem2.beans.Message;

/**
 * @author oups
 *
 */
public class MMSMessageHandler extends AbstractMessageHandler {

	private static final Logger logger = LoggerFactory.getLogger(MMSMessageHandler.class);
	/**
	 * @param target
	 * @param message
	 */
	public MMSMessageHandler(String target, Message message) {
		super(target, message);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.i11b.pwork.sem2.server.messagehandlers.AbstractMessageHandler#send()
	 */
	public boolean send() {
		logger.debug("Send MMS to {} with message:\n {}", this.target, this.message.message);
		return true;
	}

}
