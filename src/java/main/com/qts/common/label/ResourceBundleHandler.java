package com.qts.common.label;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.qts.service.common.ServiceRequestContextHolder;

/**
 * @author RAMMOHAN
 * 
 */
public class ResourceBundleHandler {

	private static ResourceBundleHandler INSTANCE;

	private static final String LABELS_BUNDLE = "com.qts.common.label.LabelsBundle";

	private ResourceBundleHandler() {
	}

	public static ResourceBundleHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ResourceBundleHandler();
		}
		return INSTANCE;
	}

	public Map<String, String> getLabels() throws UnsupportedEncodingException {
		Locale locale = getLocale();
		ResourceBundle rb = ResourceBundle.getBundle(LABELS_BUNDLE, locale);
		Enumeration<String> keys = rb.getKeys();
		Map<String, String> labels = new HashMap<String, String>();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String value = rb.getString(key);
			//to support other languages charecters
			String val = new String(value.getBytes("ISO-8859-1"), "UTF-8");
			labels.put(key, val);
		}
		return labels;
	}

	private Locale getLocale() {
		int affinityId = (int) ServiceRequestContextHolder.getContext()
				.getAffinityId();
		switch (affinityId) {
		case 1:
			return new Locale("en", "US");
		case 2:
			return new Locale("te", "IN");
		default:
			return new Locale("en", "US");
		}
	}

}
