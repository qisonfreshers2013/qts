package com.qts.common.json;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.SerializationConfig;

import com.qts.service.annotations.Default;
import com.qts.service.annotations.Optional;
import com.qts.service.annotations.SerializationDescriptor;

public class SerializationConfigFactory {

	public static void populateSerializationDescriptors(SerializationConfig config, Class qtsObject, Class descriptor) {
		config.addMixInAnnotations(qtsObject, descriptor);
		Method[] methods = descriptor.getMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(SerializationDescriptor.class)) {
				SerializationDescriptor childDescriptor = method.getAnnotation(SerializationDescriptor.class);
				Class returntype = method.getReturnType();
				//TypeVariable<GenericDeclaration>[] x = returntype.getTypeParameters();
				if (method.getReturnType().isAssignableFrom(List.class)) {
					Type genericReturnType = method.getGenericReturnType();
					if (genericReturnType instanceof ParameterizedType) {
						ParameterizedType type = (ParameterizedType) genericReturnType;
						Type[] typeArguments = type.getActualTypeArguments();
						for (Type typeArgument : typeArguments) {
							returntype = (Class) typeArgument;
						}
					}
				} else if (method.getReturnType().isAssignableFrom(Map.class)) {
					returntype = method.getReturnType().getTypeParameters().getClass();
				}
				populateSerializationDescriptors(config, returntype, childDescriptor.value());
			}
		}
	}

	public static void getJSONSchema(Class descriptor, StringBuilder schema, StringBuilder jsonStructure) {
		if (descriptor.getSimpleName().equals("SimpleStringDescriptor")) {
			schema.append("\"String\"");
			jsonStructure.append("\"String\"");
			return;
		}

		if (descriptor.getSimpleName().equals("BlankWebserviceRequestDescriptor")) {
			return;
		}

		schema.append("{ \"properties\":{");
		jsonStructure.append("{");

		// 1. get the methods
		Method[] methods = descriptor.getMethods();

		int i = 1;
		boolean isLastProperty = false;
		// 2. for each method write the JSON schema output
		for (Method method : methods) {
			if (i == methods.length) {
				isLastProperty = true;
			}
			i++;
			String propertyName = method.getName();
			String returnType = method.getReturnType().getSimpleName();
			// this gets the class type if the return type is collection
			// gets the object type inside the collection
			Class returnTypeClass = method.getReturnType();

			boolean optional = false;
			String defaultValue = null;

			// identify property name
			if (propertyName.startsWith("get")) {
				propertyName = propertyName.substring(3);
			} else if (propertyName.startsWith("is")) {
				propertyName = propertyName.substring(2);
			}
			propertyName = propertyName.replaceFirst(propertyName.substring(0, 1), propertyName.substring(0, 1).toLowerCase());

			// identify return type
			if (method.getReturnType().isAssignableFrom(List.class) || method.getReturnType().isAssignableFrom(Map.class)) {
				Type genericReturnType = method.getGenericReturnType();
				if (genericReturnType instanceof ParameterizedType) {
					ParameterizedType type = (ParameterizedType) genericReturnType;
					Type[] typeArguments = type.getActualTypeArguments();
					for (Type typeArgument : typeArguments) {
						returnType = ((Class) typeArgument).getName();
						returnTypeClass = ((Class) typeArgument);
					}
				}
			}

			if (method.isAnnotationPresent(Optional.class)) {
				optional = true;
			}

			if (method.isAnnotationPresent(Default.class)) {
				Default defaultAnnotation = method.getAnnotation(Default.class);
				defaultValue = defaultAnnotation.value();
			}

			// add info to the schema
			jsonStructure.append("\"" + propertyName + "\":");

			if (method.isAnnotationPresent(SerializationDescriptor.class)) {
				SerializationDescriptor childDescriptor = method.getAnnotation(SerializationDescriptor.class);
				boolean isSuperClass = childDescriptor.superClass();
				String type = "Object";
				if (isSuperClass) {
					type = "One of the concrete object in the following structure";
				}
				if (List.class.isAssignableFrom(method.getReturnType())) {
					StringBuilder childSchema = new StringBuilder();
					StringBuilder childObjectStructureBuilder = new StringBuilder();
					getJSONSchema(childDescriptor.value(), childSchema, childObjectStructureBuilder);
					jsonStructure.append("[" + childObjectStructureBuilder.toString() + "]");
					schema.append("\"" + propertyName + "\": { ");
					if (optional) {
						schema.append("\"optional\": \"true\" , ");
					}
					if (defaultValue != null) {
						schema.append("\"default\":\"" + defaultValue + "\" , ");
					}

					schema.append("\"type\": \"List of " + type + "\" ");
					schema.append(", " + childSchema.toString().substring(1, childSchema.toString().length() - 1) + "}");
					if (!isLastProperty) {
						jsonStructure.append(",");
						schema.append(",");
					}
				} else {
					StringBuilder childSchema = new StringBuilder();
					getJSONSchema(childDescriptor.value(), childSchema, jsonStructure);
					schema.append("\"" + propertyName + "\": { ");
					if (optional) {
						schema.append("\"optional\": \"true\" , ");
					}
					if (defaultValue != null) {
						schema.append("\"default\":\"" + defaultValue + "\" , ");
					}
					schema.append("\"type\": \"" + type + "\" ");
					schema.append(", " + childSchema.toString().substring(1, childSchema.toString().length() - 1) + "}");
					if (!isLastProperty) {
						jsonStructure.append(",");
						schema.append(",");
					}

				}
			} else {
				if (method.getReturnType().isAssignableFrom(List.class)) {
					jsonStructure.append("[" + getTestDataBasedOnReturnType(returnTypeClass) + "]");
					schema.append("\"" + propertyName + "\": { ");
					if (optional) {
						schema.append("\"optional\": \"true\" , ");
					}
					if (defaultValue != null) {
						schema.append("\"default\":\"" + defaultValue + "\" , ");
					}
					schema.append("\"type\": \"List of " + returnType + "\"");
					schema.append("}");
					if (!isLastProperty) {
						jsonStructure.append(",");
						schema.append(",");
					}
				} else if (method.getReturnType().isAssignableFrom(Map.class)) {

				} else {
					schema.append("\"" + propertyName + "\": { ");
					if (optional) {
						schema.append("\"optional\": \"true\" , ");
					}
					if (defaultValue != null) {
						schema.append("\"default\":\"" + defaultValue + "\" , ");
					}
					schema.append("\"type\": \"" + returnType + "\"");
					schema.append("}");

					jsonStructure.append(getTestDataBasedOnReturnType(returnTypeClass));
					if (!isLastProperty) {
						jsonStructure.append(",");
						schema.append(",");
					}
				}
			}
		}
		jsonStructure.append("}");
		schema.append("}}");

	}

	public static Object getTestDataBasedOnReturnType(Class returnType) {
		if (returnType.isAssignableFrom(String.class)) {
			return "\"Test String\"";
		} else if (returnType.isAssignableFrom(Boolean.class)) {
			return "\"true\"";
		} else if (returnType.getName().equals("boolean")) {
			return "\"true\"";
		}
		return 1;
	}

}
