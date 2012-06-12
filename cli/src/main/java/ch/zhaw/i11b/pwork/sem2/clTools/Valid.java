package ch.zhaw.i11b.pwork.sem2.clTools;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;
import com.google.i18n.phonenumbers.*;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

/**
 * Validation of email address and phone number
 * @author boffel
 */
public class Valid {
	
	public static boolean emailValid(String email){
	
		boolean result = true;
		try {
			InternetAddress emailAdd = new InternetAddress(email);
			emailAdd.validate();
		} catch(AddressException ex){
			result = false;
		}
		return result;
	}
	
	public static String ccValid(String cc){
		if(intValid(cc)){
			PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
			cc = phoneUtil.getRegionCodeForCountryCode(Integer.parseInt(cc));
			
			return cc;
		}
		else{
			return "";
		}
	}
	
	public static boolean alphaNumValid(String pn){
		boolean b = true;
		for(int i=0; i<pn.length(); i++){
			if(!Character.isLetterOrDigit(pn.charAt(i))){
				b = false;
				break;
			}
		}
		return b;
	}
	public static String phoneValid(String phoneNumber, String countryCode){
		
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		try {
			
			PhoneNumber nr = phoneUtil.parse(phoneNumber, countryCode);
			return phoneUtil.format(nr, PhoneNumberFormat.INTERNATIONAL);
		} catch (NumberParseException e) {
			
			//System.err.println("NumberParseException was thrown: " + e.toString());
			return "";
		}
	}
	
	public static boolean intValid(String strint){
		
		try{	
			Integer.parseInt(strint.trim());
			return true;
			
		} catch(NumberFormatException e){
			System.out.println("# Could not parse "+ strint);
			return false;
		}
	}
}
