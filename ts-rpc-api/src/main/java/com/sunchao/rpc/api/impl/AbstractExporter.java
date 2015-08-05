package com.sunchao.rpc.api.impl;

import com.sunchao.rpc.api.Exporter;
import com.sunchao.rpc.api.Invoker;
import com.sunchao.rpc.common.logger.Logger;
import com.sunchao.rpc.common.logger.LoggerFactory;

/**
 * Base implementation of Exporter.
 * 
 * @author <a href="mailto:sunchao6106@163.com">sunchao</a>
 *
 * @param <T> service type
 */
public abstract class AbstractExporter<T> implements Exporter<T> {
	
	protected final Logger LOGGER = LoggerFactory.getLogger(AbstractExporter.class);
	
	private final Invoker<T> invoker;
	
	private volatile boolean unexported = false;
	
	public AbstractExporter(Invoker<T> invoker) {
		if (invoker == null)
			throw new IllegalStateException("service invoker cannot be null.");
		if (invoker.getInterface() == null)
			throw new IllegalStateException("service type cannot be null.");
		if (invoker.getConfig() == null)
			throw new IllegalStateException("service common address information cannot be null.");
		this.invoker = invoker;
	}

	public Invoker<T> getInvoker() {
		return invoker;
	}

	public void unexport() {
		if (unexported) //already cancel.
			return;
		unexported = true;
		getInvoker().destroy();
	}
	
	public String toString() {
		return getInvoker().toString();
	}

}
