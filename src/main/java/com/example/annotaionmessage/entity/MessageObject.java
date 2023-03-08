package com.example.annotaionmessage.entity;

import com.example.annotaionmessage.annotation.MethodParam;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class MessageObject implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String[] parentPath;
	private String requestMethod;
	private String description;
	private String[] requestPath;
	private String returnType;
	private List<MethodParams> params;
	
	public MessageObject(){};

	public MessageObject(String[] parentPath,
						 String requestMethod,
						 String description,
						 String[] requestPath,
						 String returnType,
						 List<MethodParams> params) {
		this.parentPath = parentPath;
		this.requestMethod = requestMethod;
		this.description = description;
		this.requestPath = requestPath;
		this.returnType = returnType;
		this.params = params;
	}

	public String[] getParentPath(){
		return parentPath;
	}

	public MessageObject setParentPath(String[] parentPath){
		this.parentPath = parentPath;
		return this;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public MessageObject setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public MessageObject setDescription(String description) {
		this.description = description;
		return this;
	}

	public String[] getRequestPath() {
		return requestPath;
	}

	public MessageObject setRequestPath(String[] requestPath) {
		this.requestPath = requestPath;
		return this;
	}

	public String getReturnType() {
		return returnType;
	}

	public MessageObject setReturnType(String returnType) {
		this.returnType = returnType;
		return this;
	}

	public List<MethodParams> getParams() {
		return params;
	}


	public MessageObject setParams(List<MethodParams> params) {
		this.params = params;
		return this;
	}

	@Override
	public String toString() {
		return "MessageObject{" +
				"parentPath=" + Arrays.toString(parentPath) +
				", requestMethod='" + requestMethod + '\'' +
				", description='" + description + '\'' +
				", requestPath=" + Arrays.toString(requestPath) +
				", returnType='" + returnType + '\'' +
				", params=" + params +
				'}';
	}

}
