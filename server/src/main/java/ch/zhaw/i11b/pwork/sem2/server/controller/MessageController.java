package ch.zhaw.i11b.pwork.sem2.server.controller;

import ch.zhaw.i11b.pwork.sem2.server.Main;
import ch.zhaw.i11b.pwork.sem2.server.tasks.MessageTask;
import ch.zhaw.i11b.pwork.sem2.server.tasks.ReminderTask;
import ch.zhaw.i11b.pwork.sem2.beans.Message;
import ch.zhaw.i11b.pwork.sem2.beans.Messages;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  oups
 */
public class MessageController {
	//Singleton impl
	/**
	 * @uml.property  name="_instance"
	 * @uml.associationEnd  
	 */
	static protected MessageController _instance = null;
	
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	static public MessageController Instance() {
		if (_instance == null) {
			_instance = new MessageController();
			logger.debug("created Message Controller Singleton object");
		}
		return _instance;
	}
	
	/**
	 * 
	 */
	protected MessageController() {
		// add a empty message to cancled for debug purpos
		this.messages.cancled.add(new Message());
	}
	
	
	/**
	 * @uml.property  name="messages"
	 * @uml.associationEnd  
	 */
	protected Messages messages = new Messages();
	protected HashMap<String, TimerTask> openTasks = new HashMap<String, TimerTask>();
	protected HashMap<String, Timer> timers = new HashMap<String, Timer>();
	//public Interface
	/**
	 * @param msg
	 */
	public synchronized void addMessage(Message msg) {
		logger.debug("Adding new Message with Message:\n{}", msg.message);
		msg.id = UUID.randomUUID().toString();
		logger.debug("created uuid vor message: {}", msg.id);
		if (msg.sendtime == null) {
			msg.sendtime = new Date();
			logger.debug("set sendtime to now.");
		}
		Timer timer = new Timer();
		ReminderTask reminderTask = null;
		if (msg.reminder) {
			logger.debug("create reminder Message");
			reminderTask = new ReminderTask(msg);
			timer.schedule(reminderTask, reminderTask.getTime());
			this.openTasks.put("reminder_"+msg.id, reminderTask);
			logger.debug("added reminder to MessageQueue");
		} 
		MessageTask task = new MessageTask(msg);
		timer.schedule(task, msg.sendtime);
		this.messages.open.add(msg);
		this.openTasks.put(msg.id, task);
		this.timers.put(msg.id, timer);
		logger.debug("Message added");
	}
	
	/**
	 * @param msg
	 */
	protected synchronized void clearMessage(Message msg) {
		logger.debug("clear Message with uuid: {}", msg.id);
		Timer timer = this.timers.get(msg.id);
		timer.cancel();
		timer.purge();
		this.timers.remove(msg.id);
		this.openTasks.remove(msg.id);
		if (msg.reminder) {
			this.openTasks.remove("reminder_"+msg.id);
		}
		this.messages.open.remove(msg);
		logger.debug("message removed.");
	}
	
	/**
	 * @param msg
	 */
	public synchronized void finishMessage(Message msg) {
		this.clearMessage(msg);
		this.messages.finished.add(msg);
		logger.debug("message(id:{}) finished", msg.id);
	}

	/**
	 * @param msg
	 */
	public synchronized void errorMessage(Message msg) {
		this.clearMessage(msg);
		this.messages.errors.add(msg);
		logger.debug("message(id:{}) error", msg.id);
	}
	
	/**
	 * @param msg
	 */
	public synchronized void cancleMessage(Message msg) {
		this.clearMessage(msg);
		this.messages.cancled.add(msg);
		logger.debug("message(id:{}) cancled", msg.id);
	}
	
	/**
	 * @return
	 * @uml.property  name="messages"
	 */
	public synchronized Messages getMessages() {
		logger.debug("return all messages..");
		return this.messages;
	}
}
