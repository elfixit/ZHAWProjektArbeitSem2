package ch.zhaw.i11b.pwork.sem2.server.controller;

import ch.zhaw.i11b.pwork.sem2.server.tasks.MessageTask;
import ch.zhaw.i11b.pwork.sem2.server.tasks.ReminderTask;
import ch.zhaw.i11b.pwork.sem2.beans.Message;
import ch.zhaw.i11b.pwork.sem2.beans.Messages;

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
	
	/**
	 * Singleton Impl.. static Instance funktion
	 * @return
	 */
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
		// add a empty message to canceled for debug purpos
		this.messages.canceled.add(new Message());
	}
	
	
	/**
	 * stores the messages(state) of the controller
	 * @uml.property  name="messages"
	 * @uml.associationEnd  
	 */
	protected Messages messages = new Messages();
	/**
	 * Describes the open tasks.. based on Message id and TimerTasks..
	 */
	protected HashMap<String, TimerTask> openTasks = new HashMap<String, TimerTask>();
	/**
	 * Describes the open timers, Timers(a set of TimerTasks(MessageTask, ReminderTask))
	 */
	protected HashMap<String, Timer> timers = new HashMap<String, Timer>();
	
	//public Interface
	/**
	 * Adds a message to the "queue" is the most complex method on the controller..
	 * if you understand this you understand the app.. kisses
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
	 * Kills the Timer of the message.. defined over Message.id
	 * and removes them from the reference.. 
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
	 * Cancels a message
	 * 
	 * @param msg
	 */
	public synchronized void cancleMessage(Message msg) {
		this.clearMessage(msg);
		this.messages.canceled.add(msg);
		logger.debug("message(id:{}) cancled", msg.id);
	}
	
	/**
	 * returns the Messages object of the Controller, kinde of the state of the controller
	 * @return
	 * @uml.property  name="messages"
	 */
	public synchronized Messages getMessages() {
		logger.debug("return all messages..");
		return this.messages;
	}
	
	/**
	 * update Messages.. integrates:
	 * 
	 *   * All open messages to the queue
	 *   * adds finished,cancled,errors to the list
	 * @return
	 * @uml.property 
	 */
	public synchronized boolean loadMessages(Messages msgs) {
		for (Message msg: msgs.open) {
			this.addMessage(msg);
		}
		for (Message msg: msgs.finished) {
			this.messages.finished.add(msg);
		}
		for (Message msg: msgs.canceled) {
			this.messages.canceled.add(msg);
		}
		for (Message msg: msgs.errors) {
			this.messages.errors.add(msg);
		}
		return true;
	}
}
