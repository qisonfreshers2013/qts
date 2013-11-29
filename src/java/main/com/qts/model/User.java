package com.qts.model;

import com.qts.common.Utils;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;

import java.util.List;
import java.util.Map;

/**
 * @author RAMMOHAN
 * 
 */
@JsonAutoDetect(JsonMethod.SETTER)
public class User extends AbstractObject {

	public static final String LABEL_USER_ID = "userId";
	public static final String LABEL_EMAIL = "email";
	public static final String LABEL_FIRST_NAME = "firstName";
	public static final String LABEL_LAST_NAME = "lastName";
	public static final String LABEL_USERS = "users";
	public static final String LABEL_NAME = "name";
	public static final String LABEL_PRIMARY_AFFINITY_ID = "primaryAffinityId";
	public static final String LABEL_FACEBOOK_ID = "userSourceId";
	public static final String LABEL_ROLE_ID = "roleId";
	public static final String LABEL_GENDER = "gender";
	public static final String LABEL_ADDRESS1 = "address1";
	public static final String LABEL_ADDRESS2 = "address2";
	public static final String LABEL_CITY = "city";
	public static final String LABEL_STATE = "state";
	public static final String LABEL_QUALIFICATION_YEAR = "qualification_year";
	public static final String LABEL_SPECIALIZATION = "specialization";
	public static final String LABEL_IS_ANONYMOUS = "isAnonymous";
	public static final String LABEL_DOB = "dob";
	public static final String LABEL_AFFINITIES = "affinities";
	public static final String LABEL_COUNTRY = "country";
	public static final String LABEL_GOOGLE_ID = "userSourceId";
	public static final String LABEL_USER_SOURCE = "userSource";

	public static final String USER_SOURCE_REGULAR = "regular";
	public static final String USER_SOURCE_FACEBOOK = "facebook";
	public static final String USER_SOURCE_GOOGLE = "google";	
	
	public static final String AUTH_TYPE_REGULAR = "REGULAR";	
	public static final String AUTH_TYPE_FACEBOOK = "FACEBOOK";
	
	public static final int AUTH_STATUS_EXISTING = 0;
	public static final int AUTH_STATUS_NEW = 1;	
	public static final int AUTH_STATUS_NONE = 2;
	
	public static final int USER_ACCEPTED_TERMS = 1;
	public static final int USER_NOT_ACCEPTED_TERMS = 0;
	
	public static final int FACEBOOK_PUBLISH_ALLOWED = 1;
	public static final int FACEBOOK_PUBLISH_NOT_ALLOWED = 0;
	
	public static final int  ANONYMOUS = 1;
	public static final int  ONYMOUS = 0;
	
	public static final String LABEL_GENDER_MALE = "Male";
	public static final String LABEL_GENDER_FEMALE = "Female";
	
	public static final String LABEL_ANONYMOUS = "ANONYMOUS";
	public static final String  LABEL_ONYMOUS = "ONYMOUS";
	
	private String userId;
	private long primaryAffinityId;
	private long qtsId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String gender;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String zip;
	private long dob;
    private long roleId;
    private long fileId;
    private Map<String, String> userSource;
    private String qualification_year;
    private String specialization;
    private List<String> tags;
    private int isAnonymous;
    private List<Long> affinities;

	public User() {
		super();
	}
	
	public User(User user) {
		super(user);
		this.primaryAffinityId = user.primaryAffinityId;
		this.qtsId = user.qtsId;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.email = user.email;
		this.password = user.password;
		this.gender = user.gender;
		this.address1 = user.address1;
		this.address2 = user.address2;
		this.city = user.city;
		this.state = user.state;
		this.country = user.country;
		this.zip = user.zip;
		this.dob = user.dob;
		this.roleId = user.roleId;
		this.fileId = user.fileId;
		this.userSource = user.userSource;
		this.qualification_year = user.qualification_year;
		this.specialization = user.specialization;
		this.tags = user.tags;
		this.isAnonymous = user.isAnonymous;
		this.affinities = user.affinities;
	}

    public long getQtsId() {
        return qtsId;
    }

    public void setQtsId(long qtsId) {
        this.qtsId = qtsId;
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the primaryAffinityId
	 */
	public long getPrimaryAffinityId() {
		return primaryAffinityId;
	}

	/**
	 * @param primaryAffinityId
	 *            the primaryAffinityId to set
	 */
	public void setPrimaryAffinityId(long primaryAffinityId) {
		this.primaryAffinityId = primaryAffinityId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1
	 *            the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2
	 *            the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip
	 *            the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the dob
	 */
	public long getDob() {
		return dob;
	}

	/**
	 * @param dob
	 *            the dob to set
	 */
	public void setDob(long dob) {
		this.dob = dob;
	}
	
	/**
	 * @return the role
	 */
	public long getRoleId() {
		return roleId;
	}

	/**
	 *
	 */
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
		
	/**
	 * @return the fileId
	 */
	public long getFileId() {
		return fileId;
	}

	/**
	 * @param fileId
	 *            the fileId to set
	 */
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	
	
	public Map<String, String> getUserSource() {
		return userSource;
	}

	public void setUserSource(Map<String, String> userSource) {
		this.userSource = userSource;
	}

	
	/**
	 * @return the full name of user
	 */
	public String getFullName() {
		return firstName + " " + lastName;
	}

	/**
	 * @return the qualification_year
	 */
	public String getQualification_year() {
		return qualification_year;
	}

	/**
	 * @param qualification_year
	 *            the qualification_year to set
	 */
	public void setQualification_year(String qualification_year) {
		this.qualification_year = qualification_year;
	}

	/**
	 * @return the specialization
	 */
	public String getSpecialization() {
		return specialization;
	}

	/**
	 * @param specialization
	 *            the specialization to set
	 */
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	/**
	 * @return the tags
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	/**
	 * @return the isAnonymous
	 */
	public int getIsAnonymous() {
		return isAnonymous;
	}

	/**
	 * @param isAnonymous the isAnonymous to set
	 */
	public void setIsAnonymous(int isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	//To Return FirstName when User is Anonymous 
	public String getFirstNameForUI() {
		if (this.getIsAnonymous() == User.ANONYMOUS) {
			// Yes User is Anonymous
			// Check session is Null or Not
			if (Utils.getUserId() != 0) {
				// User Logged In
				if (Utils.getUserId() == this.getId()) {
					return this.getFirstName();
				} else {
					this.setFirstName("Anonymous");
					return this.getFirstName();
				}
			} else {
				// Session is Null
				this.setFirstName("Anonymous");
				return this.getFirstName();
			}
		} else {
			// No User is Not Anonymous
			return this.getFirstName();
		}
	}

	//To Return LastName when User is Anonymous
	public String getLastNameForUI() {
		if (this.getIsAnonymous() == User.ANONYMOUS) {
			// Yes User is Anonymous
			// Check session is Null or Not
			if (Utils.getUserId() != 0) {
				// User Logged In
				if (Utils.getUserId() == this.getId()) {
					return this.getLastName();
				} else {
					this.setLastName("");
					return this.getLastName();
				}
			} else {
				// Session is Null
				this.setLastName("");
				return this.getLastName();
			}
		} else {
			// No User is Not Anonymous
			return this.getLastName();
		}
	}

	//To Return Email when User is Anonymous
	public String getEmailForUI() {
		if (this.getIsAnonymous() == User.ANONYMOUS) {
			// Yes User is Anonymous
			//Check session is Null or Not
			if (Utils.getUserId() != 0) {
				// User Logged In
				if (Utils.getUserId() == this.getId()) {
					return this.getEmail();
				} else {
					this.setEmail("Anonymous");
					return this.getEmail();
				}
			} else {
				// Session is Null
				this.setEmail("Anonymous");
				return this.getEmail();
			}
		} else {
			// No User is Not Anonymous
			return this.getEmail();
		}
	}

	
	//To Return FullName when User is Anonymous
	public String getFullNameForUI() {
		if (this.getIsAnonymous() == User.ANONYMOUS) {
			// Yes User is Anonymous
			//Check session is Null or Not
			if (Utils.getUserId() != 0) {
				// User Logged In
				if (Utils.getUserId() == this.getId()) {
					return this.getFullName();
				} else {
					this.setFirstName("Anonymous");
					this.setLastName("");
					return this.getFullName().trim();
				}
			} else {
				// Session is Null
				this.setFirstName("Anonymous");
				this.setLastName("");
				return this.getFullName().trim();
			}
		} else {
			// No User is Not Anonymous
			return this.getFullName();
		}
	}

	/**
	 * @return the affinities
	 */
	public List<Long> getAffinities() {
		return affinities;
	}

	/**
	 * @param affinities the affinities to set
	 */
	public void setAffinities(List<Long> affinities) {
		this.affinities = affinities;
	}

    /*
    * (non-Javadoc)
    *
    * @see com.qts.model.BaseObject#getObjectType()
    */
	@Override
	public int getObjectType() {
		return ObjectTypes.USER;
	}


}
