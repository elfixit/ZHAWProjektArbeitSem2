/**
 * 
 */
package ch.zhaw.i11b.pwork.sem2.server.tasks;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.zhaw.i11b.pwork.sem2.beans.Message;
import ch.zhaw.i11b.pwork.sem2.beans.Target;
import ch.zhaw.i11b.pwork.sem2.server.messagehandlers.EMailMessageHandler;

/**
 * @author oups
 *
 */
public class ReminderTask extends TimerTask {

	private static final Logger logger = LoggerFactory.getLogger(EMailMessageHandler.class);
	
	private Message originalMsg = null;
	private Message reminderMsg = null;
	private Date sendTime = null;
	
	public ReminderTask(Message msg) {
		this.originalMsg = msg;
		Calendar cal = Calendar.getInstance();
		cal.setTime(msg.sendtime);
		cal.set(Calendar.HOUR, -1);		
		this.sendTime = cal.getTime();
		this.reminderMsg = new Message();
		this.reminderMsg.message = "This Mail is a reminder Mail\n" +
				"your Message:\n" + this.originalMsg.message + 
				"\nwill be send @ " + this.originalMsg.sendtime;
	}
	
	public Date getTime() {
		return this.sendTime;
	}
	
	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		logger.warn("Send reminder for msg: {}", this.originalMsg.id);
		EMailMessageHandler handler = new EMailMessageHandler(this.originalMsg.from, this.reminderMsg, "Reminder Messages from MultiChannel");
		handler.send();
		logger.debug("reminder msg send!");
	}

}
