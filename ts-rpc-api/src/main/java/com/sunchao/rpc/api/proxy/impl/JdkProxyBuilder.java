package com.sunchao.rpc.api.proxy.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.sunchao.rpc.api.Invoker;
import com.sunchao.rpc.api.impl.AbstractProxyInvoker;
import com.sunchao.rpc.api.proxy.AbstractProxyBuilder;
import com.sunchao.rpc.api.proxy.InvokerInvocationHandler;
import com.sunchao.rpc.base.exception.RPCApplicationException;
import com.sunchao.rpc.common.ClientConfig;

public class JdkProxyBuilder extends AbstractProxyBuilder {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProxy(Invoker<T> invoker, Class<?>[] types)
			throws RPCApplicationException {
		return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), types, new InvokerInvocationHandler(invoker));
	}

	public <T> Invoker<T> getInvoker(T proxy, Class<T> type, ClientConfig config)
			throws RPCApplicationException {
		return new AbstractProxyInvoker<T>(proxy, type, config) {
			@Override
			protected Object doInvoke(T proxy, String methodName,
					                  Class<?>[] parameterTypes,
					                  Object[] arguments) throws Throwable {
				Method method = proxy.getClass().getMethod(methodName, parameterTypes);
				return method.invoke(proxy, arguments);
				
			}				
		};
	}
}
