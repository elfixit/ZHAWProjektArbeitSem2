package ch.zhaw.i11b.pwork.sem2.beans;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement
public class Messages {
	@XmlElement(required=true)
	public List<Message> finished = new ArrayList<Message>();
	@XmlElement(required=true)
	public List<Message> open = new ArrayList<Message>();
	@XmlElement(required=true)
	public List<Message> errors = new ArrayList<Message>();
	@XmlElement(required=true)
	public List<Message> cancled = new ArrayList<Message>();
	
	public Messages() {} // JAXB needs this
}