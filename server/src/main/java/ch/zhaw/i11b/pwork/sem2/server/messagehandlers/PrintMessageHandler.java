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
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.i11b.pwork.sem2.server.messagehandlers.AbstractMessageHandler#send()
	 */
	public boolean send() {
		// TODO Auto-generated method stub
		return true;
	}

}
