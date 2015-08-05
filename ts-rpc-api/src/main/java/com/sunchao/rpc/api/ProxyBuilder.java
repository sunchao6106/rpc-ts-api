package com.sunchao.rpc.api;

import com.sunchao.rpc.base.exception.RPCApplicationException;
import com.sunchao.rpc.common.ClientConfig;
import com.sunchao.rpc.common.extension.Component;
import com.sunchao.rpc.common.extension.HotSwap;

/**
 * The Proxy factory.
 * 
 * @author <a href="mailto:sunchao6106@163.com">sunchao</a>
 *
 */
@Component("javassist")
public interface ProxyBuilder {
	
	/**
	 * Create the proxy stub.
	 * 
	 * @param invoker
	 * @return
	 * @throws RPCApplicationException
	 */
	@HotSwap()
	<T> T getProxy(Invoker<T> invoker) throws RPCApplicationException;
	
	/**
	 * Create the invoke.
	 * 
	 * @param proxy the proxy.
	 * @param type  the interface type.
	 * @param config the client remote information.
	 * @return
	 * @throws RPCApplicationException
	 */
	@HotSwap()
	<T> Invoker<T> getInvoker(T proxy, Class<T> type, ClientConfig config) throws RPCApplicationException;

}
