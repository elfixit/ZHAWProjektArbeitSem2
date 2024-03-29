package ch.zhaw.i11b.pwork.sem2.server.messagehandlers.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import ch.zhaw.i11b.pwork.sem2.server.messagehandlers.AbstractMessageHandler;
import ch.zhaw.i11b.pwork.sem2.server.messagehandlers.IMessageHandler;
import ch.zhaw.i11b.pwork.sem2.beans.Message;
import ch.zhaw.i11b.pwork.sem2.beans.Target;





/**
 * @author elfixit
 *
 */
public class MessageHandlerFactory {

	/**
	 * the singleton instance.. 
	 * @uml.property  name="_instance"
	 * @uml.associationEnd  
	 */
	static private MessageHandlerFactory _instance = null;
	
	/**
	 * The static Singleton Method.. to get the singleton..
	 * @return
	 */
	static public MessageHandlerFactory instance() {
		if (_instance == null) {
			_instance = new MessageHandlerFactory();
		}
		return _instance;
	}
	
	protected MessageHandlerFactory() {}
	
	protected HashMap<String, Class<AbstractMessageHandler>> types = new HashMap<String, Class<AbstractMessageHandler>>();
	
	
	/**
	 * @param type
	 * @return
	 */
	public synchronized Class<AbstractMessageHandler> getClassForType(String type) {
		return this.types.get(type);
	}
	
	/**
	 * @param target
	 * @param msg
	 * @return
	 */
	public synchronized AbstractMessageHandler getMessageHandler(Target target, Message msg) {
		try {
			Class<AbstractMessageHandler> cls = this.getClassForType(target.type);
			Class[] parameterTypes = {String.class, Message.class};
			Constructor<AbstractMessageHandler> ct = cls.getConstructor(parameterTypes);
			return (AbstractMessageHandler) ct.newInstance(target.location, msg);
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
	
	/**
	 * @param type
	 * @param cls
	 */
	public synchronized void registerMessageHandler(String type, Class cls) {
		this.types.put(type, cls);
	}
}
