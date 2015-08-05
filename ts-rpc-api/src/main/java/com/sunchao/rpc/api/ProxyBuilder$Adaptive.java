package com.sunchao.rpc.api;


import com.sunchao.rpc.common.extension.HotSwapLoader;

import com.sunchao.rpc.base.exception.*;
@Deprecated
public class ProxyBuilder$Adaptive implements com.sunchao.rpc.api.ProxyBuilder {
public com.sunchao.rpc.api.Invoker getInvoker(java.lang.Object arg0, java.lang.Class arg1, com.sunchao.rpc.common.ClientConfig arg2) throws com.sunchao.rpc.base.exception.RPCApplicationException {
if (arg2 == null) throw new IllegalArgumentException("the client remote configuration cannot null");
com.sunchao.rpc.common.ClientConfig config = arg2;
String implName = config.getParameterOrDefault("23", "javassist");
if(implName == null) throw new IllegalStateException("Fail to get extension(com.sunchao.rpc.api.ProxyBuilder) name from config(" + config.toString() + ") use keys([23])");
com.sunchao.rpc.api.ProxyBuilder extension = (com.sunchao.rpc.api.ProxyBuilder)HotSwapLoader.getExtensionLoader(com.sunchao.rpc.api.ProxyBuilder.class).getExtension(implName);
return extension.getInvoker(arg0, arg1, arg2);
}
public java.lang.Object getProxy(com.sunchao.rpc.api.Invoker arg0) throws com.sunchao.rpc.base.exception.RPCApplicationException {
if (arg0 == null) throw new IllegalArgumentException("com.sunchao.rpc.api.Invoker argument == null");
if (arg0.getConfig() == null) throw new IllegalArgumentException("com.sunchao.rpc.api.Invoker argument getConfig() == null");
com.sunchao.rpc.common.ClientConfig config = arg0.getConfig();
String implName = config.getParameterOrDefault("12", "javassist");
if(implName == null) throw new IllegalStateException("Fail to get extension(com.sunchao.rpc.api.ProxyBuilder) name from config(" + config.toString() + ") use keys([12])");
com.sunchao.rpc.api.ProxyBuilder extension = (com.sunchao.rpc.api.ProxyBuilder)HotSwapLoader.getExtensionLoader(com.sunchao.rpc.api.ProxyBuilder.class).getExtension(implName);
return extension.getProxy(arg0);
}

public static void main(String args[]) {
	try {
		Class.forName(ProxyBuilder$Adaptive.class.getName());
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}