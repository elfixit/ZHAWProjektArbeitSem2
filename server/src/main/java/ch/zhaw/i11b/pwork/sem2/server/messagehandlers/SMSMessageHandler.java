package ch.zhaw.i11b.pwork.sem2.server.messagehandlers;

import java.util.ArrayList;
import java.util.List;

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
	
	private List<String> msgs = new ArrayList<String>();
	/**
	 * 
	 * @param target
	 * @param message
	 */
	public SMSMessageHandler(String target, Message message) {
		super(target, message);
		if (this.message.message.length() > 160) {
			int parts = (int)this.message.message.length() / 160;
			for(int i=0; i < parts; i++) {
				this.msgs.add(this.message.message.substring(i*160, i*160+160));
			}
		} else {
			this.msgs.add(this.message.message);
		}
		Object[] params = {message.id, target, this.msgs.size()};
		logger.debug("SMSMessage({}) for target {} created and splited in {} parts", params);
	}

	/**
	 * @return boolean
	 */
	public boolean send() {
		for (String msg: this.msgs) {
			logger.warn("Send SMS to {} with message:\n {}", this.target, msg);
		}
		return true;
	}

}
