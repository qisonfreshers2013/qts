package com.qts.handler;

/**
 * @author Kavinder
 */
public class UserExtHandler extends AbstractHandler {
	
	private static UserExtHandler INSTANCE = null;

	private UserExtHandler() {
	}

	public static UserExtHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserExtHandler();
		}
		return INSTANCE;
	}

	public String getTestResult() {
		String testResult = "HANDLER TEST PASS";
		return testResult;
	}

	public boolean testWithInput(String text) {
		if(text.equals("true")){
			return true;
		} else{
			return false;
		}		
	}

}
