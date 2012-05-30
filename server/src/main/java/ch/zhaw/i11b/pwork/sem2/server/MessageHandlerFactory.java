package ch.zhaw.i11b.pwork.sem2.server;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import ch.zhaw.i11b.pwork.sem2.server.messagehandlers.AbstractMessageHandler;
import ch.zhaw.i11b.pwork.sem2.server.messagehandlers.IMessageHandler;
import ch.zhaw.i11b.pwork.sem2.beans.Message;
import ch.zhaw.i11b.pwork.sem2.beans.Target;



/**
 * @author  oups
 */
public class MessageHandlerFactory {

	/**
	 * @uml.property  name="_instance"
	 * @uml.associationEnd  
	 */
	static private MessageHandlerFactory _instance = null;
	
	static public MessageHandlerFactory instance() {
		if (_instance == null) {
			_instance = new MessageHandlerFactory();
		}
		return _instance;
	}
	
	protected MessageHandlerFactory() {}
	
	protected HashMap<String, Class<AbstractMessageHandler>> types = new HashMap<String, Class<AbstractMessageHandler>>();
	
	
	public synchronized Class<AbstractMessageHandler> getClassForType(String type) {
		return this.types.get(type);
	}
	
	public synchronized IMessageHandler getMessageHandler(Target target, Message msg) {
		try {
			Class<AbstractMessageHandler> cls = this.getClassForType(target.type);
			Class[] parameterTypes = {String.class, Message.class};
			Constructor<AbstractMessageHandler> ct = cls.getConstructor(parameterTypes);
			return (IMessageHandler) ct.newInstance(target.location, msg);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public synchronized void registerMessageHandler(String type, Class cls) {
		this.types.put(type, cls);
	}
}
