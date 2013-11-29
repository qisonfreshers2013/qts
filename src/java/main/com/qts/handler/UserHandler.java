package com.qts.handler;

/**
 * @author Kavinder
 */
public class UserHandler extends AbstractHandler {
	
	private static UserHandler INSTANCE = null;

	private UserHandler() {
	}

	public static UserHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserHandler();
		}
		return INSTANCE;
	}

	public String getTestResult() {
		String testResult = "HANDLER TEST PASS";
		return testResult;
	}

	public boolean testWithInput(String text) {
		if(text == "true"){
			return true;
		} else{
			return false;
		}		
	}

}
