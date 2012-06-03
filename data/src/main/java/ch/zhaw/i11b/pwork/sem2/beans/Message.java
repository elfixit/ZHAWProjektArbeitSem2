package ch.zhaw.i11b.pwork.sem2.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {
	@XmlElement(required=false)
	public String id = null;
	public String from = "test@test.com";
	public String message = "Hallo World!";
	public List<Target> targets = new ArrayList<Target>();
	public Date sendtime = null;
	public boolean reminder = false;

	public Message() {} // JAXB needs this
}
