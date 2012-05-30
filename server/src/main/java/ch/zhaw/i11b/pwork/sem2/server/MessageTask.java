package ch.zhaw.i11b.pwork.sem2.server;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import ch.zhaw.i11b.pwork.sem2.server.controller.MessageController;
import ch.zhaw.i11b.pwork.sem2.server.messagehandlers.IMessageHandler;
import ch.zhaw.i11b.pwork.sem2.beans.Message;
import ch.zhaw.i11b.pwork.sem2.beans.Target;


public class MessageTask extends TimerTask {
	protected List<IMessageHandler> messageHandlers = new ArrayList<IMessageHandler>();
	protected Message msg = null;
	protected boolean is_reminder = false;
	
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
	public MessageTask(Message msg) {
		this(msg, false);
	}
	
	public Message getMessage() {
		return this.msg;
	}
	
	public boolean isReminder() {
		return this.is_reminder;
	}
	
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
