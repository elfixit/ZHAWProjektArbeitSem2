package ch.zhaw.i11b.pwork.sem2.beans;

//import java.util.Formatter;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

/**
 * The Target is used to define one target of a Message 
 * 
 * @author oups
 *
 */
@XmlRootElement
public class Target {
	@XmlElement(required = true)
	public String type = "Mail";
	@XmlElement(required = true)
	public String location = "test@test.com";

	public Target() {} // JAXB needs this
	public Target(String typeIn, String locationIn) {
		this.type = typeIn;
		this.location = locationIn;
	}
}
