package com.sunchao.rpc.api;

import org.apache.http.annotation.ThreadSafe;

import com.sunchao.rpc.base.exception.RPCException;
import com.sunchao.rpc.common.ClientConfig;

/**
 * The RPC Invoker.
 * 
 * @author <a href="mailto:sunchao6106@163.com">sunchao</a>
 *
 * @param <T>
 */
@ThreadSafe
public interface Invoker<T> {

	/**
	 * Get the user-defined configuration.
	 * 
	 * @return
	 */
	ClientConfig getConfig();
	
	/**
	 * Get the service interface.
	 * 
	 * @return
	 */
	Class<T> getInterface();
	
	/**
	 * invoke function.
	 * 
	 * @param invocation
	 * @return
	 * @throws RPCException
	 * @throws Throwable 
	 */
	Result invoke(Invocation invocation) throws RPCException;
	
	/**
	 * the client-side to detect the service whether or nor available.
	 * 
	 * @return
	 */
	boolean isAvailable();
	
	/**
	 * the server-side kill down the service.
	 */
	void destroy();
}
