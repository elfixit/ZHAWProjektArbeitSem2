package ch.zhaw.i11b.pwork.sem2.server.messagehandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.zhaw.i11b.pwork.sem2.beans.Message;
import ch.zhaw.i11b.pwork.sem2.server.controller.MessageController;

/**
 * 
 * @author oups
 *
 */
public class SMSMessageHandler extends AbstractMessageHandler {

	private static final Logger logger = LoggerFactory.getLogger(SMSMessageHandler.class);
	/**
	 * 
	 * @param target
	 * @param message
	 */
	public SMSMessageHandler(String target, Message message) {
		super(target, message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return boolean
	 */
	public boolean send() {
		
		return true;
	}

}
