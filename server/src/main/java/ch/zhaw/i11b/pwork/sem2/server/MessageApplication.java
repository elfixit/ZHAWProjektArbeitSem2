package ch.zhaw.i11b.pwork.sem2.server;

import javax.ws.rs.ApplicationPath;

import com.sun.jersey.api.core.PackagesResourceConfig;

@ApplicationPath("resources")
public class MessageApplication extends PackagesResourceConfig {
	public MessageApplication() {
		super("ch.zhaw.i11b.pwork.sem2");
	}

}
