package com.sunchao.rpc.api.impl;

import java.lang.reflect.InvocationTargetException;

import com.sunchao.rpc.api.Invocation;
import com.sunchao.rpc.api.Invoker;
import com.sunchao.rpc.api.Result;
import com.sunchao.rpc.api.RpcResult;
import com.sunchao.rpc.base.exception.RPCException;
import com.sunchao.rpc.common.ClientConfig;

public abstract class AbstractProxyInvoker<T> implements Invoker<T> {
	
	/**
	 * @return the config
	 */
	public ClientConfig getConfig() {
		return config;
	}

	private final T proxy;
	
	private final Class<T> type;
	
	private final ClientConfig config;
	
	public AbstractProxyInvoker(T proxy, Class<T> type, ClientConfig config) {
		if (proxy == null)
			throw new IllegalArgumentException("proxy cannot be null.");
		if (type == null)
			throw new IllegalArgumentException("interface cannot be null.");
		if (!type.isInstance(proxy))
			throw new IllegalArgumentException(proxy.getClass().getName() + " not implement interface: " + type);
		this.proxy = proxy;
		this.type = type;
		this.config = config;
	}
		
	public Class<T> getInterface() {
		return type;
	}
	
	/* (non-Javadoc)
	 * @see com.sunchao.rpc.api.Invoker#isAvailable()
	 */
	public boolean isAvailable() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.sunchao.rpc.api.Invoker#destory()
	 */
	public void destroy() {
		//no-op
	}

	public Result invoke(Invocation invocation) throws RPCException {
		try {
			return new RpcResult(doInvoke(proxy, invocation.getMethodName(), 
					invocation.getParameterTypes(), invocation.getArguments()));
		} catch (InvocationTargetException e) {
			return new RpcResult(e.getTargetException());
		} catch (Throwable t) {
			throw new RPCException("Fail to invoke remote proxy method " + 
		            invocation.getMethodName() + " ,cause: " + t.getMessage(), t);
		}
	}
	
	protected abstract Object doInvoke(T proxy, String methodName, Class<?>[] parameterTypes, 
			Object[] arguments) throws Throwable;

}
