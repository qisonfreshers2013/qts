package com.qts.common;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qts.common.cache.CacheHandler;
import com.qts.common.cache.CacheManager;
import com.qts.common.cache.CacheRegionType;
import com.qts.model.UserSessionToken;

/**
 * Servlet implementation class InitializationServlet
 */
public class InitializationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Initialized");
		init(config, true);
	}

	protected void init(ServletConfig config, boolean initializeAllServices)
			throws ServletException {
		if (config != null) {
			super.init(config);
		}
		if (initializeAllServices) {
			// Initialize all Services one by one
			initializeServices();
		}
	}

	protected void initializeServices() throws ServletException {
		try {
			CacheHandler.getInstance().initService();
			UserSessionToken userSessionToken = new UserSessionToken();
			userSessionToken.setUserEmail("test@test.com");
			userSessionToken.setUserId(1);
			String sessionToken = "hlhdsfjlhl2134";
			userSessionToken.setUserSessionId(sessionToken);
			CacheManager.getInstance()
					.getCache(CacheRegionType.USER_SESSION_CACHE)
					.put(sessionToken, userSessionToken);
			
			Object value = CacheManager.getInstance()
			.getCache(CacheRegionType.USER_SESSION_CACHE)
			.getValue(sessionToken);			
			
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
