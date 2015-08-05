package com.sunchao.rpc.api.proxy.impl;

import com.sunchao.rpc.api.Invoker;
import com.sunchao.rpc.api.impl.AbstractProxyInvoker;
import com.sunchao.rpc.api.proxy.AbstractProxyBuilder;
import com.sunchao.rpc.api.proxy.InvokerInvocationHandler;
import com.sunchao.rpc.base.codec.Proxy;
import com.sunchao.rpc.base.codec.Wrapper;
import com.sunchao.rpc.base.exception.RPCApplicationException;
import com.sunchao.rpc.common.ClientConfig;

public class JavassistProxyBuilder extends AbstractProxyBuilder {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProxy(Invoker<T> invoker, Class<?>[] types)
			throws RPCApplicationException {
		return (T) Proxy.getProxy(types).newInstance(new InvokerInvocationHandler(invoker));
	}

	
	public <T> Invoker<T> getInvoker(T proxy, Class<T> type, ClientConfig config)
			throws RPCApplicationException {
		final Wrapper wrapper = Wrapper.getWrapper(proxy.getClass().getName().indexOf('$') < 0 ? proxy.getClass() : type);
		return new AbstractProxyInvoker<T>(proxy, type, config) {
			@Override
			protected Object doInvoke(T proxy, String methodName,
					Class<?>[] parameterTypes, Object[] arguments)
					throws Throwable {
				return wrapper.invokeMethod(proxy, methodName, parameterTypes, arguments);
			}
		};	
	}
}
