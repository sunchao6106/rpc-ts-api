package com.sunchao.rpc.api;

import org.apache.http.annotation.ThreadSafe;

/**
 * 
 * The service' export and atomic detected by the client.
 * 
 * @author <a href="mailto:sunchao6106@163.com">sunchao</a>
 *
 * @param <T>
 */
@ThreadSafe
public interface Exporter<T> {

	/**
	 * Get invoker.
	 * 
	 * @return invoker.
	 */
	Invoker<T> getInvoker();
	
	/**
	 * the invoker down.
	 */
	void unexport();
}
