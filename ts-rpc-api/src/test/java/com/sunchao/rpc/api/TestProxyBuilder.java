package com.sunchao.rpc.api;

import com.sunchao.rpc.common.extension.HotSwapLoader;

public class TestProxyBuilder {
	
	private static ProxyBuilder builder = HotSwapLoader.getExtensionLoader(ProxyBuilder.class).getAdaptiveExtension();
	
	public static void main(String args[]) {
		System.out.println(builder.getClass().getName());
	}

}
