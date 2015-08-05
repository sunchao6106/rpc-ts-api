package com.sunchao.rpc.api;

import org.apache.http.annotation.ThreadSafe;

import com.sunchao.rpc.base.exception.RPCException;
import com.sunchao.rpc.common.ClientConfig;
import com.sunchao.rpc.common.extension.Component;
import com.sunchao.rpc.common.extension.HotSwap;

/**
 * Protocol.
 * 
 * @author <a href="mailto:sunchao6106@163.com">sunchao</a>
 *
 */
@ThreadSafe 
@Component("xxx")
public interface Protocol {

	/**
	 * Get the default service port.
	 * 
	 * @return the port
	 */
	int getDefaultPort();
	
	/**
	 * Publish the remote service.
	 * 
	 * @param invoker the server-side, the service executive
	 * @return the reference of the service, which used to cancel the service, used by the publisher of service.
	 * @throws RPCException
	 */
	@HotSwap
	<T> Exporter<T> export(Invoker<T> invoker) throws RPCException;
	
	/**
	 * the client-size, the service consumer to get the reference of remote service.
	 * 
	 * @param type  the service type.
	 * @param config  the service remote address and some common configuration.
	 * @return  the sub-local of the remote service.
	 * @throws RPCException
	 */
	@HotSwap
	<T> Invoker<T> refer(Class<T> type, ClientConfig config) throws RPCException;
}
