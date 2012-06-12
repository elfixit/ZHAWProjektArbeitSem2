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
	
	private String subject = "";
	/**
	 * @param target
	 * @param message
	 */
	public EMailMessageHandler(String target, Message message) {
		this(target, message, "Message from MultiChannel");
	}
	
	public EMailMessageHandler(String target, Message message, String subject) {
		super(target, message);
		this.subject = subject;
		logger.debug("EMailMessage({}) for target {} created", message.id, target);		
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.i11b.pwork.sem2.server.messagehandlers.AbstractMessageHandler#send()
	 */
	public boolean send() {
		Object[] params = {target, this.subject, this.message.message};
		logger.warn("Send EMail to {} with subject:\n{}\n message:\n {}", params);
		return true;
	}

}
