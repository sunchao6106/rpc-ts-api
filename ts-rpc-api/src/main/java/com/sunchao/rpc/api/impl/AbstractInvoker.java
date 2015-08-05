package com.sunchao.rpc.api.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;

import com.sunchao.rpc.api.Invocation;
import com.sunchao.rpc.api.Invoker;
import com.sunchao.rpc.api.Result;
import com.sunchao.rpc.api.RpcInvocation;
import com.sunchao.rpc.api.RpcResult;
import com.sunchao.rpc.base.exception.RPCApplicationException;
import com.sunchao.rpc.base.exception.RPCException;
import com.sunchao.rpc.common.ClientConfig;
import com.sunchao.rpc.common.logger.Logger;
import com.sunchao.rpc.common.logger.LoggerFactory;
import com.sunchao.rpc.common.utils.NetUtil;

public abstract class AbstractInvoker<T> implements Invoker<T> {
	
	/**
	 * @return the config
	 */
	public ClientConfig getConfig() {
		return config;
	}

	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractInvoker.class);
	
	//private final T proxy;
	
	private final Class<T> type;
	
	/**
	 * The {@code Volatile} to denote the service's flag visible.
	 */
	private volatile boolean available = true;
	
	/**
	 * @return the destoryed
	 */
	public boolean isDestoryed() {
		return destoryed;
	}
	
/*	*//**
	 * the destory flag to set.
	 *//*
	public void destory() {
		
	}*/

	/**
	 * @return the available
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}

	private volatile boolean destoryed = true;
	
	private final ClientConfig config;
	
	private final Map<String, String> attachment;
	
	public AbstractInvoker(Class<T> type, ClientConfig config) {
		this(type, null, config);
	}
	
	public AbstractInvoker(Class<T> type, Map<String, String> attachment, ClientConfig config) {
		if (type == null) 
			throw new IllegalArgumentException("service type cannot be null.");
		if (config == null)
			throw new IllegalArgumentException("client remote config cannot be null.");
		//this.proxy = proxy;
		this.type = type;
		this.attachment = attachment == null ? null : Collections.unmodifiableMap(attachment);
		this.config = config;
	}
	
	/**
	 * @return the interface
	 */
	public Class<T> getInterface() {
		return type;
	}
			
    public String toString() {
    	return getInterface() + " -> " + config.toString() + " " + this.attachment.toString();
    }
    
    public void destroy() {
    	if (isDestoryed()) 
			return;
		destoryed = true;
		setAvailable(false);
    }
    
    public Result invoke(Invocation invocation) throws RPCException {
    	if (destoryed) {
    		throw new RPCException("Rpc invoker for service " + this + " on consumer " + NetUtil.getLocalHost() +
    				" is Destoryed, can not be invoke any more.");
    	}
    	RpcInvocation inv = (RpcInvocation) invocation;
    	inv.setInvoker(this);
    	if (attachment != null && attachment.size() > 0) {
    		inv.addAttachmentsIfAbsent(attachment);
    	}
    	try {
			return doInvoker(inv);
		} catch (InvocationTargetException e) {
			//InvocationTargetException ex = (InvocationTargetException) e;
			Throwable t = e.getTargetException();
			if (t == null) 
				return new RpcResult(e);
			else {
				if (t instanceof RPCException) {
					((RPCException) t).setType(RPCApplicationException.UNKNOWN_METHOD);
				}
				return new RpcResult(t);
			}
		} catch (RPCException e) {
			throw e;
		} catch (Throwable e) {
			return new RpcResult(e);
		}
    }
    
    public abstract Result doInvoker(Invocation invocation) throws Throwable;
}
