/**
 * 
 */
package ch.zhaw.i11b.pwork.sem2.server.tasks;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import ch.zhaw.i11b.pwork.sem2.beans.Message;

/**
 * @author oups
 *
 */
public class ReminderTask extends TimerTask {

	private Message originalMsg = null;
	private Date sendTime = null;
	
	public ReminderTask(Message msg) {
		this.originalMsg = msg;
		Calendar cal = Calendar.getInstance();
		cal.setTime(msg.sendtime);
		cal.set(Calendar.HOUR, -1);		
		this.sendTime = cal.getTime();
	}
	
	public Date getTime() {
		return this.sendTime;
	}
	
	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
