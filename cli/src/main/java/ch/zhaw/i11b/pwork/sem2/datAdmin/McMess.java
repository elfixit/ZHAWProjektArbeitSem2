package ch.zhaw.i11b.pwork.sem2.datAdmin;
import java.util.ArrayList;
import java.util.Date;

public class McMess{
	private String from = "";
	private ArrayList<String> to;
	private String message;
	private Date date;
	private Boolean remind;

	public void setFrom(String newFrom){
		this.from = newFrom;
	}
	public String getFrom(){
		return this.from;
	}

	public void setTo(ArrayList<String> newTo){
		this.to = newTo;
	}
	public ArrayList<String> getTo(){
		return this.to;
	}

	public void setMcMess(String newMessage){
		this.message = newMessage;
	}
	public String getMcMess(){
		return this.message;
	}

	public void setDate(Date newDate){
		this.date = newDate;
	}
	public Date getDate(){
		return this.date;
	}

	public void setRemind(Boolean newRemind){
		this.remind = newRemind;
	}
	public Boolean getRemind(){
		return this.remind;
	}
}
	
