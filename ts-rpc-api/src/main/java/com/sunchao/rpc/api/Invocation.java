package com.sunchao.rpc.api;

import java.util.Map;

/**
 * The wrapper of need information of rpc call.
 * 
 * @author <a href="mailto:sunchao6106@163.com">sunchao</a>
 *
 */
public interface Invocation {

	/**
	 * Get method name.
	 * 
	 * @return
	 */
	String getMethodName();
	
	/**
	 * Get parameter types.
	 * 
	 * @return
	 */
	Class<?>[] getParameterTypes();
	
	/**
	 * Get arguments
	 * 
	 * @return
	 */
	Object[]  getArguments();
	
	/**
	 * Get the additional informations.
	 * @return
	 */
	Map<String, String> getAttachmenmts();
	
	/**
	 * Get the information by the specified string key.
	 * 
	 * @param key the specified key.
	 * @return
	 */
	String getAttachment(String key);
	
	/**
	 * Get the information by the specified string key.
	 * When the value exists , just return the default
	 * value.
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	String getAttachment(String key, String defaultValue);
	
	/**
	 * Get the current invoker.
	 * 
	 * @return
	 */
	Invoker<?> getInvoker();
}
