package ch.zhaw.i11b.pwork.sem2.server.messagehandlers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.zhaw.i11b.pwork.sem2.beans.Message;

/**
 * 
 * @author oups
 *
 */
public class SMSMessageHandler extends AbstractMessageHandler {

	private static final Logger logger = LoggerFactory.getLogger(SMSMessageHandler.class);
	
	private List<String> msgs = new ArrayList<String>();
	
	/**
	 * Splits Message in to message parts.. and files up the string list msgs
	 * @param target
	 * @param message
	 */
	public SMSMessageHandler(String target, Message message) {
		super(target, message);
		int length = this.message.message.length();
		if (length > 160) {
			int parts = (int)(this.message.message.length() / 160) + 1;
			logger.debug("spliting messages in {} parts because length({}) > 160", parts, this.message.message.length());
			for(int i=0; i < parts; i++) {
				int start = i*160;
				int end = start+160;
				if (end > length) {
					end = length;
				}
				this.msgs.add(this.message.message.substring(start, end));
			}
		} else {
			this.msgs.add(this.message.message);
		}
		Object[] params = {message.id, target, this.msgs.size(), this.message.message.length()};
		logger.debug("SMSMessage({}) for target {} created and splited in {} parts original size: {}", params);
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
