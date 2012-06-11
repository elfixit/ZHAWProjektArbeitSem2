package ch.zhaw.i11b.pwork.sem2.server.messagehandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.zhaw.i11b.pwork.sem2.beans.Message;

/**
 * @author oups
 *
 */
public class PrintMessageHandler extends AbstractMessageHandler {

	private static final Logger logger = LoggerFactory.getLogger(PrintMessageHandler.class);
	/**
	 * @param target
	 * @param message
	 */
	public PrintMessageHandler(String target, Message message) {
		super(target, message);
		logger.debug("PrintMessage({}) for target {} created", message.id, target);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.i11b.pwork.sem2.server.messagehandlers.AbstractMessageHandler#send()
	 */
	public boolean send() {
		logger.warn("Print to {} with message:\n {}", this.target, this.message.message);
		return true;
	}

}
