package com.sunchao.rpc.api;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.sunchao.rpc.base.serializer.support.varint.Ignore;
/**
 * RPC Invocation.
 * 
 * @author <a href="mailto:sunchao6106@163.com">sunchao</a>
 *
 */
public class RpcInvocation implements Invocation {

	/**
	 * @return the attachments
	 */
	public Map<String, String> getAttachments() {
		return attachments;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public void addAttachments(Map<String, String> attachments) {
		if (attachments == null) 
			return;
		if (this.attachments == null) {
		    this.attachments = new HashMap<String, String>(attachments);
		}
		this.attachments.putAll(attachments);
	}

	/**
	 * @return the invoker
	 */
	public Invoker<?> getInvoker() {
		return invoker;
	}

	/**
	 * @param invoker the invoker to set
	 */
	public void setInvoker(Invoker<?> invoker) {
		this.invoker = invoker;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @param parameterTypes the parameterTypes to set
	 */
	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes == null ? new Class<?>[0] : parameterTypes;
	}

	/**
	 * @param arguments the arguments to set
	 */
	public void setArguments(Object[] arguments) {
		this.arguments = arguments == null ? new Object[0] : arguments;
	}
	
	public void setAttachMethod(String key, String value) {
		if (attachments == null) 
			attachments = new HashMap<String, String>();
		attachments.put(key, value);
	}
	
	public void setAttachmentIfAbsent(String key, String value) {
		if (attachments == null) 
			attachments = new HashMap<String, String>();
		if (!attachments.containsKey(key))
			attachments.put(key, value);
	}
	
	public void addAttachmentsIfAbsent(Map<String, String> attachments) {
		if (attachments == null)
			return;
		for (Map.Entry<String, String> entry : attachments.entrySet()) {
			setAttachmentIfAbsent(entry.getKey(), entry.getValue());
		}
	}
	
	public String getAttachment(String key) {
		if (attachments == null) 
			return null;
		return attachments.get(key);
	}

	private String methodName;
	
	private Class<?>[] parameterTypes;
	
	private Object[] arguments;
	
	private Map<String, String> attachments;
	
	/**
	 * The annotation must not remove.
	 */
    @Ignore
	private Invoker<?> invoker;
    
    public RpcInvocation() {
    	
    }
    
    public RpcInvocation(Invocation invocation, Invoker<?> invoke) {
    	
    }
    
    public RpcInvocation(Invocation invocation) {
    	this(invocation.getMethodName(), invocation.getParameterTypes(), invocation.getArguments(), 
    			invocation.getAttachmenmts(), invocation.getInvoker());
    }
    
    public RpcInvocation(Method method, Object[] arguments) {
    	this(method.getName(), method.getParameterTypes(), arguments, null, null);
    }
    
    public RpcInvocation(Method method, Object[] arguments, Map<String, String> attachments) {
    	this(method.getName(), method.getParameterTypes(), arguments, attachments, null);
    }
    
    public RpcInvocation(String methodName, Class<?>[] parameterTypes, Object[] arguments, 
    		Map<String, String> attachments, Invoker<?> invoke) {
    	this.methodName = methodName;
    	this.parameterTypes = parameterTypes == null ? new Class<?>[0] : parameterTypes;
    	this.arguments = arguments == null ? new Object[0] : arguments;
    	this.attachments = attachments == null ? new HashMap<String, String>() : attachments;
    	this.invoker = invoke;
    }
    
	public String getMethodName() {
		return methodName;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public Map<String, String> getAttachmenmts() {
		return attachments;
	}


	public String getAttachment(String key, String defaultValue) {
		if (attachments == null)
			return defaultValue;
		String value = attachments.get(key);
		if (value == null || value.length() == 0)
			return defaultValue;
		return value;
	}
	
	public String toString() {
		return "RpcInvocation [methodName = " + methodName + ", parameterTypes = " + 
	             Arrays.toString(parameterTypes) + ", arguments = " + Arrays.toString(arguments)
	             + ", attachment = " + attachments + "]";
	}

}
