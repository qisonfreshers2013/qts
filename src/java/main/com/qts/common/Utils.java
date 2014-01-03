package com.qts.common;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Session;

import com.qts.exception.EncryptionException;
import com.qts.exception.BusinessException;
import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.InvalidTimeEntryDataException;
import com.qts.service.common.ServiceRequestContextHolder;

/**
 * @author RAMMOHAN
 */
public final class Utils {

    public static final String EMAIL_PATTERN = "^[_A-Za-z]+[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String NAME_PATTERN = "^[A-Za-z0-9\\s]*$";
    public static final String USER_NAME_PATTERN = "^[A-Za-z\\s]*$";
    public static final String DESIGNATION = "^([A-Z]+)$";
    public static final String GENDER = "^(?:male|Male|female|Female)$";//----//^M(ale)?$|^F(emale)?$
    public static final String EMPLOYEE_ID_PATTERN = "^[A-Za-z0-9]*$";
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int ARTICLE_BRIEF_DESCRIPTION_LENGTH = 190;
    public static final int BRIEF_DESCRIPTION_START_INDEX = 0;
	public static final String LOCATION_PATTERN = "^[A-Za-z\\s]*$";
	public static final String DATE_PATTTERN="^(0[1-9]|1[012])([-/])(0[1-9]|[12][0-9]|3[01])\\2([23]0)\\d\\d$";
    public static void validateEmail(String email)
            throws BusinessException {
        if (email == null || email.isEmpty() || email.trim().isEmpty()) {
            throw new BusinessException(ExceptionCodes.EMAIL_CANNOT_BE_EMPTY,
                    ExceptionMessages.EMAIL_CANNOT_BE_EMPTY);
        }
        boolean isEmailPatternValid = Pattern.compile(Utils.EMAIL_PATTERN)
                .matcher(email).matches();
        if (!isEmailPatternValid) {
            throw new BusinessException(ExceptionCodes.INVALID_EMAIL_PATTERN,
                    ExceptionMessages.INVALID_EMAIL_PATTERN);
        }
    }

    public static void validatePassword(String password)
            throws BusinessException {
        if (password == null || password.isEmpty() || password.trim().isEmpty()) {
            throw new BusinessException(
                    ExceptionCodes.PASSWORD_CANNOT_BE_EMPTY,
                    ExceptionMessages.PASSWORD_CANNOT_BE_EMPTY);
        }
        boolean isPasswordStrengthValid = password.trim().length() >= Utils.MIN_PASSWORD_LENGTH;
        if (!isPasswordStrengthValid) {
            throw new BusinessException(ExceptionCodes.WEAK_PASSWORD,
                    ExceptionMessages.WEAK_PASSWORD);
        }
    }

    public static void validateName(String name) throws BusinessException {
        boolean isNameValid = Pattern.compile(Utils.NAME_PATTERN).matcher(name)
                .matches();
        if (!isNameValid) {
            throw new BusinessException(ExceptionCodes.INVALID_NAME,
                    ExceptionMessages.INVALID_NAME);
        }
    }

    public static String encrypt(String password) throws EncryptionException {
        String encryptedPassword = EncryptionFactory.getEncryption(true).encrypt(
                password);
        return encryptedPassword;
    }

    public static String HTML2StringConverter(String HTMLString,
                                              boolean removeHTML, int charLength) {
        if (removeHTML) {
            String javaString = HTMLString.replaceAll("\\<.*?\\>", "");
            javaString = javaString.replaceAll("\r", "");
            javaString = javaString.replaceAll("\n", "");
            javaString = javaString.replaceAll("\'", "");
            javaString = javaString.replaceAll("\"", "");
            javaString = javaString.replaceAll("<br/>", "");
            javaString = javaString.replaceAll("&quot;", "");
            javaString = javaString.replaceAll("&#39;", "");
            if (charLength == 0) {
                return javaString;
            } else if (javaString.length() > charLength) {
                String javaSubString = javaString.substring(BRIEF_DESCRIPTION_START_INDEX, charLength);
                return javaSubString;
            } else {
                return javaString;
            }
        } else {
            return HTMLString;
        }
    }

    public static String removeLineTerminators(String inputStr) {
        String patternStr = "(?m)$^|[\\r\\n]+\\z";
        patternStr = "[\\s]";
        String replaceStr = " ";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.replaceAll(replaceStr);
    }

      public static long getUserId() {
        long userId = 0;
        try {
            userId = ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId();
        } catch (Throwable throwable) {

        }
        return userId;
    }

    /*public static long getAffinityId() {
        long affinityId = 0;
        try {
            affinityId = ServiceRequestContextHolder.getContext().getAffinityId();
        } catch (Throwable throwable) {
        }
        return affinityId;
    }*/

   /* public static long getRoleId() {
        return ServiceRequestContextHolder.getContext().getUserSessionToken().getRoleId();
    }
*/
    public static String getStringFromList(List list) {
        if (null != list)
            return list.toString().replaceFirst("\\[", "").replace("]", "");
        return null;
    }

    public static double getRound2TwoDigits(double val) {
        return Math.round(val * 10.0) / 10.0;
    }

    public static double roundToNearestHalf(double value) {
        double avgRating = value;
        double roundedRating = 0.0;
        long nearestAvg = (long) avgRating;
        roundedRating = avgRating - nearestAvg;
        if (roundedRating <= 0.25) {
            roundedRating = 0.0;
        } else if (roundedRating > 0.25 && roundedRating <= 0.50
                || roundedRating > 0.50 && roundedRating <= 0.75) {
            roundedRating = 0.50;
        } else if (roundedRating > 0.75 && roundedRating <= 1.0) {
            roundedRating = 1.0;
        }
        return nearestAvg + roundedRating;
    }

    public static boolean isWindows() {
        final String os = System.getProperty("os.name");
        return (os.contains("Win"));
    }

    public static boolean isMac() {
        return (System.getProperty("os.name").contains("mac"));
    }

    public static boolean isUnix() {
        String OS = System.getProperty("os.name");
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }

    public static boolean isSolaris() {
        return (System.getProperty("os.name").contains("sunos"));
    }

    public static void assignNull(Object...objects ){
    	for(Object object:objects){
    		object=null;
    	}
    }
    
    public static Session getDBSession(){
    	return ServiceRequestContextHolder.getContext().getSession();
    		
    	}
    	
    	//parsing string to date object of format MM-DD-YYYY or MM/DD/YYYY  and getting date in Milliseconds
    	
	public static long parseDateToLong(String date) throws Exception {

		try {

			Date dateObj=DateUtils.parseDate(date,"MM/dd/yyyy","MM-dd-yyyy");
                      return dateObj.getTime();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		
	}
	
	//method to convert timeinMilliseconds to Timestamp object and then getting it as string of format DDMMYYYY 
	
	public static String getDateInString(long timeinMilliSeconds) {
		try {
			Timestamp date = new Timestamp(timeinMilliSeconds);
			String dateInString = "";
			dateInString = dateInString
					.concat(date.toString().substring(8, 10))
					.concat(date.toString().substring(5, 7))
					.concat(date.toString().substring(0, 4));
			return dateInString;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}
	
	//General validations For DATE STRING
	
	public static boolean generalValidationsForDate(String date)
			throws InvalidTimeEntryDataException {

		if (date.length() != 10)
			throw new InvalidTimeEntryDataException(
					ExceptionCodes.DATE_LENGTH_MISMATCH,
					ExceptionMessages.DATE_LENGTH_MISMATCH);
		else if (!(date.matches(Utils.DATE_PATTTERN)))
			throw new InvalidTimeEntryDataException(
					ExceptionCodes.DATE_FORMAT_EXCEPTION,
					ExceptionMessages.INVALID_ARGUMENTS_FOR_DATE);
		else if (Integer.parseInt(date.substring(0, 2)) == 1
				|| Integer.parseInt(date.substring(0, 2)) == 3
				|| Integer.parseInt(date.substring(0, 2)) == 5
				|| Integer.parseInt(date.substring(0, 2)) == 7
				|| Integer.parseInt(date.substring(0, 2)) == 8
				|| Integer.parseInt(date.substring(0, 2)) == 10
				|| Integer.parseInt(date.substring(0, 2)) == 12){
			if (Integer.parseInt(date.substring(3, 5)) > 31)
				throw new InvalidTimeEntryDataException(
						ExceptionCodes.DATE_FORMAT_EXCEPTION,
						ExceptionMessages.DATE_EXCEPTION);
			}
		 else if (Integer.parseInt(date.substring(0, 2)) == 2) {
			if (new GregorianCalendar().isLeapYear(Calendar.getInstance().get(
					Calendar.YEAR))
					&& Integer.parseInt(date.substring(3, 5)) > 29) {
				throw new InvalidTimeEntryDataException(
						ExceptionCodes.DATE_FORMAT_EXCEPTION,
						ExceptionMessages.DATE_EXCEPTION);
			} else {
				if (Integer.parseInt(date.substring(3, 5)) > 28) {
					throw new InvalidTimeEntryDataException(
							ExceptionCodes.DATE_FORMAT_EXCEPTION,
							ExceptionMessages.DATE_EXCEPTION);
				}
			}
		 }
		 else if (Integer.parseInt(date.substring(0, 2)) == 4
					|| Integer.parseInt(date.substring(0, 2)) == 6
					|| Integer.parseInt(date.substring(0, 2)) == 9
					|| Integer.parseInt(date.substring(0, 2)) == 11){ 
			 if(Integer.parseInt(date.substring(3, 5)) > 30) {
			
				throw new InvalidTimeEntryDataException(
						ExceptionCodes.DATE_FORMAT_EXCEPTION,
						ExceptionMessages.DATE_EXCEPTION);
			}
			}
		if(Integer.parseInt(date.substring(3, 5))>Calendar.getInstance().get(Calendar.DATE) && Integer.parseInt(date.substring(0,2))==(Calendar.getInstance().get(Calendar.MONTH)+1)){
			throw new InvalidTimeEntryDataException(ExceptionCodes.DATE_FORMAT_EXCEPTION,ExceptionMessages.SEARCH_FUTURE_SHEETS);
		}
		 if(Integer.parseInt(date.substring(6))>Calendar.getInstance().get(Calendar.YEAR)){
			throw new InvalidTimeEntryDataException(ExceptionCodes.DATE_FORMAT_EXCEPTION,ExceptionMessages.SEARCH_FUTURE_SHEETS);
		}
		return true;
	}

}
