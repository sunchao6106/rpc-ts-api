package com.sunchao.rpc.api.impl;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.sunchao.rpc.api.Exporter;
import com.sunchao.rpc.api.Invoker;
import com.sunchao.rpc.api.Protocol;
import com.sunchao.rpc.base.exception.RPCException;
import com.sunchao.rpc.common.ClientConfig;
import com.sunchao.rpc.common.logger.Logger;
import com.sunchao.rpc.common.logger.LoggerFactory;
import com.sunchao.rpc.common.utils.ConcurrentHashSet;
import com.sunchao.rpc.util.ProtocolUtil;

/**
 * Base implementation of protocol
 * 
 * @author <a href="mailto:sunchao6106@163.com">sunchao</a>
 *
 */
public abstract class AbstractProtocol implements Protocol {

	protected final Logger LOGGER = LoggerFactory.getLogger(AbstractProtocol.class);
	//service-side record the services.
	protected final Map<String, Exporter<?>> exporterMap = new ConcurrentHashMap<String, Exporter<?>>();
	
	//client-side reference the sub-service.
	protected final Set<Invoker<?>> invokers = new ConcurrentHashSet<Invoker<?>>();
	
	protected static final String servicekey(ClientConfig config) {
		return ProtocolUtil.serviceKey(config);
	}
	
	protected static final String serviceKey(String host, int port, String serviceName) {
		return ProtocolUtil.serviceKey(host, port, serviceName);
	}
	
	
	public void destroy() {
		//client-side reference destroy.
		for (Invoker<?> invoker : invokers) {
			if (invoker != null) {
				invokers.remove(invoker);
				try {
					if (LOGGER.isInfoEnabled()) {
						LOGGER.info("Destory reference: " + invoker.getConfig());
					}
					invoker.destroy();
				} catch (Throwable t) {
					LOGGER.warn(t.getMessage(), t);
				}
			}
		}
		//server-side service destroy.
		for (String key : new ArrayList<String>(exporterMap.keySet())) {
			Exporter<?> exporter = exporterMap.remove(key);
			if (exporter != null) {
				try {
					if (LOGGER.isInfoEnabled()) {
						LOGGER.info("unexport service: " + exporter.getInvoker().getConfig());
					}
					exporter.unexport();
				} catch (Throwable t) {
					LOGGER.warn(t.getMessage(), t);
				}
			}
		}
	}

}
