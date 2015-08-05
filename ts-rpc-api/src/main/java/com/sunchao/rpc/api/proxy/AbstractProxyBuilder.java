package com.sunchao.rpc.api.proxy;

import com.sunchao.rpc.api.Invoker;
import com.sunchao.rpc.api.ProxyBuilder;
import com.sunchao.rpc.base.exception.RPCApplicationException;
import com.sunchao.rpc.service.EchoService;
/**
 * The Base class of proxyBuilder.
 * 
 * @author <a href="mailto:sunchao6106@163.com">sunchao</a>
 *
 */
public abstract class AbstractProxyBuilder implements ProxyBuilder {

	/* (non-Javadoc)
	 * @see com.sunchao.rpc.api.ProxyBuilder#getProxy(com.sunchao.rpc.api.Invoker)
	 */
	public <T> T getProxy(Invoker<T> invoker) throws RPCApplicationException {
		Class<?>[] interfaces = null;
		interfaces = new Class<?>[] {invoker.getInterface(), EchoService.class};
		return getProxy(invoker, interfaces);
	}
	
	/* (non-Javadoc)
	 * @see com.sunchao.rpc.api.ProxyBuilder#getInvoker(java.lang.Object, java.lang.Class)
	 */
	public abstract <T> T getProxy(Invoker<T> invoker, Class<?>[] types) 
			throws RPCApplicationException;

	
	
}
