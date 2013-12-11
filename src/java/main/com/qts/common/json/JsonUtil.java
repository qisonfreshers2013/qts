package com.qts.common.json;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import com.qts.service.common.ServiceResponse;

public class JsonUtil {

	private static ObjectMapper objMapper = new ObjectMapper();
	static {
		objMapper = new ObjectMapper();
		objMapper.getDeserializationConfig().set(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objMapper.getSerializationConfig().set(org.codehaus.jackson.map.SerializationConfig.Feature.USE_ANNOTATIONS, true);
		objMapper.getSerializationConfig().set(org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
	}

	public static String getSimpleCompletionObject(final boolean completionStatus) {
		class CompletionObject {
			@SuppressWarnings("unused")
			@JsonProperty
			private boolean completed;

			public CompletionObject(boolean completionStatus) {
				completed = completionStatus;
			}
		}
		return getBasicJsonView(new CompletionObject(completionStatus));
	}

	public static String getBasicJsonView(Object beanInstance) {
		try {
			ServiceResponse response = ServiceResponse.getSuccessResponse(beanInstance);
			return objMapper.writeValueAsString(response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public static String getJsonBasedOnDescriptor(Object beanInstance, Class descriptor) {
		try {
			ObjectMapper objMapper = new ObjectMapper();
			objMapper.getDeserializationConfig().set(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objMapper.getSerializationConfig().set(org.codehaus.jackson.map.SerializationConfig.Feature.USE_ANNOTATIONS, true);
			objMapper.getSerializationConfig().set(org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
			
			if (descriptor != null && beanInstance != null) {
				SerializationConfigFactory.populateSerializationDescriptors(objMapper.getSerializationConfig(),
						beanInstance.getClass(), descriptor);
			}
			ServiceResponse response = ServiceResponse.getSuccessResponse(beanInstance);
			return objMapper.writeValueAsString(response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public static String getJsonBasedOnDescriptorWithoutServiceResponseWrapper(Object beanInstance, Class descriptor) {
		try {
			ObjectMapper objMapper = new ObjectMapper();
			objMapper.getDeserializationConfig().set(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objMapper.getSerializationConfig().set(org.codehaus.jackson.map.SerializationConfig.Feature.USE_ANNOTATIONS, true);
			objMapper.getSerializationConfig().set(org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

			if (descriptor != null && beanInstance != null) {
				SerializationConfigFactory.populateSerializationDescriptors(objMapper.getSerializationConfig(),
						beanInstance.getClass(), descriptor);
			}
			return objMapper.writeValueAsString(beanInstance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public static String getJsonForListBasedOnDescriptor(Object beanInstance, Class objectInList, Class descriptor) {
		try {
			ObjectMapper objMapper = new ObjectMapper();
			objMapper.getDeserializationConfig().set(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objMapper.getSerializationConfig().set(org.codehaus.jackson.map.SerializationConfig.Feature.USE_ANNOTATIONS, true);
			objMapper.getSerializationConfig().set(org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

			if (descriptor != null && beanInstance != null) {
				SerializationConfigFactory.populateSerializationDescriptors(objMapper.getSerializationConfig(), objectInList,
						descriptor);
			}
			ServiceResponse response = ServiceResponse.getSuccessResponse(beanInstance);
			return objMapper.writeValueAsString(response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object getObject(Object source, Class targetType) {
		try {
			return objMapper.convertValue(source, targetType);
		} catch (Exception ex) {
			//System.out.println("nullpointer exception");
			throw new RuntimeException(ex);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object getObjectFromString(String source, Class targetType) {
		try {
			return objMapper.readValue(source, targetType);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
}