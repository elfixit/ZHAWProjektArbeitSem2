package ch.zhaw.i11b.pwork.sem2.server;

import javax.ws.rs.ApplicationPath;

import com.sun.jersey.api.core.PackagesResourceConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application for (JSR-311)javax.ws.rs
 * makes sure that JAXBContextResolver and MessagingService gets scanned.
 * @author elfixit
 *
 */
@ApplicationPath("resources")
public class MessageApplication extends PackagesResourceConfig {
	private Logger log;
	
	/**
	 * The constructor defines the JSR-311 search path.. 
	 */
	public MessageApplication() {
		super("ch.zhaw.i11b.pwork.sem2");
		log = LoggerFactory.getLogger(MessageApplication.class);
		log.info("creating Messaging application");
	}

}
