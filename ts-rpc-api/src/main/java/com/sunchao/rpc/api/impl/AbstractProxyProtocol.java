package com.sunchao.rpc.api.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.sunchao.rpc.api.Exporter;
import com.sunchao.rpc.api.Invocation;
import com.sunchao.rpc.api.Invoker;
import com.sunchao.rpc.api.ProxyBuilder;
import com.sunchao.rpc.api.Result;
import com.sunchao.rpc.base.exception.RPCApplicationException;
import com.sunchao.rpc.base.exception.RPCException;
import com.sunchao.rpc.common.ClientConfig;

public abstract class AbstractProxyProtocol extends AbstractProtocol {

	private final List<Class<?>> rpcExceptions = new CopyOnWriteArrayList<Class<?>>();
	
	private ProxyBuilder proxyBuilder;
	
	public AbstractProxyProtocol() {
		
	}
	
	public AbstractProxyProtocol(Class<?>... exceptions) {
		for (Class<?> exception : exceptions) 
			addRpcException(exception);
	}
	
	public void addRpcException(Class<?> exception) {
		this.rpcExceptions.add(exception);
	}
	
	/**
	 * Set the proxy builder
	 * 
	 * @param proxyBuilder the value need to set.
	 */
	public void setProxyBuilder(ProxyBuilder proxyBuilder) {
		this.proxyBuilder = proxyBuilder;
	}
	
	/**
	 * Get the proxy builder. 
	 *
	 * @return
	 */
	public ProxyBuilder getProxyBuilder() {
		return proxyBuilder;
	}
	

	/**
	 * export service.
	 * the service-side export the service invoke.
	 */
	@SuppressWarnings("unchecked")
	public <T> Exporter<T> export(final Invoker<T> invoker) throws RPCException {
		final String registerFlag = servicekey(invoker.getConfig());
		Exporter<T> exporter = (Exporter<T>) exporterMap.get(registerFlag);
		if (exporter != null)
			return exporter;
		final Runnable runnable = doExport(proxyBuilder.getProxy(invoker), invoker.getInterface(), invoker.getConfig());
		exporter = new AbstractExporter<T> (invoker) {
			/* (non-Javadoc)
			 * @see com.sunchao.rpc.api.impl.AbstractExporter#unexport()
			 */
			@Override
			public void unexport() {
				super.unexport();
				exporterMap.remove(registerFlag);
				if (runnable != null) {
					try {
					    runnable.run();
					} catch (Throwable t) {
						LOGGER.warn(t.getMessage(), t);
					}
				}
			}
			
		};	
		exporterMap.put(registerFlag, exporter);
		return exporter;
	}

	/**
	 * client-side reference the service.
	 */
	public <T> Invoker<T> refer(final Class<T> type,final ClientConfig config)
			throws RPCException {
		final Invoker<T> target = proxyBuilder.getInvoker(doRefer(type, config), type, config);
		Invoker<T> invoker = new AbstractInvoker<T>(type, config) {
			
			@Override
			public Result doInvoker(Invocation invocation) throws Throwable {
				try {
					Result result = target.invoke(invocation);
					Throwable t = result.getThrowable();
					if (t != null) {
						for (Class<?> rpcException : rpcExceptions) {
							if (rpcException.isAssignableFrom(t.getClass())) {
								throw getRPCException(type, config, invocation, t);
							}
						}
					}
					return result;
				} catch (RPCException e) {
					//if (e.getType() == RPCApplicationException.UNKNOWN)
					throw e;
				} catch(Throwable t) {
					throw getRPCException(type, config, invocation, t);
				}
			}	
		};
		invokers.add(invoker);
		return invoker;
	}
	
	protected RPCException getRPCException(Class<?> type, ClientConfig config, Invocation invocation, Throwable e) {
		RPCException exception = new RPCException("Faild to invoke remote service: " + type + ", method: "
				+  invocation.getMethodName() + ", cause: " + e.getMessage(), e);
		exception.setType(RPCApplicationException.UNKNOWN);
		return exception;
	}
	
	/**
	 * 
	 * @param type  
	 * @param config
	 * @return
	 * @throws RPCException
	 */
	protected abstract <T> T doRefer(Class<T> type, ClientConfig config) throws RPCException;

	/**
	 * 
	 * @param impl the client proxy generated class instance.
	 * @param type  the interface type.
	 * @param config  the configuration.
	 * @return
	 * @throws RPCException
	 */
	protected abstract <T> Runnable doExport(T impl, Class<T> type, ClientConfig config) throws RPCException;
}
