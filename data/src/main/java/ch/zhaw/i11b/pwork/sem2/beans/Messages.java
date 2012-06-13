package ch.zhaw.i11b.pwork.sem2.beans;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

/**
 * Kinde of the state of the Server 
 */
@XmlRootElement
public class Messages implements Serializable {
	@XmlElement(required=true)
	public List<Message> finished = new ArrayList<Message>();
	@XmlElement(required=true)
	public List<Message> open = new ArrayList<Message>();
	@XmlElement(required=true)
	public List<Message> errors = new ArrayList<Message>();
	@XmlElement(required=true)
	public List<Message> canceled = new ArrayList<Message>();
	
	public Messages() {} // JAXB needs this
}
