package ch.zhaw.i11b.pwork.sem2.server;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import ch.zhaw.i11b.pwork.sem2.server.controller.MessageController;
import ch.zhaw.i11b.pwork.sem2.server.messagehandlers.IMessageHandler;
import ch.zhaw.i11b.pwork.sem2.beans.Message;
import ch.zhaw.i11b.pwork.sem2.beans.Target;


/**
 * @author  oups
 */
public class MessageTask extends TimerTask {
	protected List<IMessageHandler> messageHandlers = new ArrayList<IMessageHandler>();
	/**
	 * @uml.property  name="msg"
	 * @uml.associationEnd  
	 */
	protected Message msg = null;
	protected boolean is_reminder = false;
	
	/**
	 * @param msg
	 * @param reminder
	 */
	public MessageTask(Message msg, boolean reminder) {
		this.msg = msg;
		this.is_reminder = reminder;
		MessageHandlerFactory factory = MessageHandlerFactory.instance();
		if (reminder) {
			
		} else {
			for (Target target : msg.targets) {
				this.messageHandlers.add(factory.getMessageHandler(target, msg));
			}
		}
	}
	
	/**
	 * @param msg
	 */
	public MessageTask(Message msg) {
		this(msg, false);
	}
	
	/**
	 * @return
	 */
	public Message getMessage() {
		return this.msg;
	}
	
	/**
	 * @return
	 */
	public boolean isReminder() {
		return this.is_reminder;
	}
	
	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		MessageController controller = null;
		try {
			controller = MessageController.Instance();
			controller.finishMessage(this.msg);
		} catch (Throwable e) {
			if (controller != null)  {
				controller.errorMessage(this.msg);
			}
		}


	}

}
