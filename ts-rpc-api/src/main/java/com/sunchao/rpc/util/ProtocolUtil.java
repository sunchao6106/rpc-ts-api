package com.sunchao.rpc.util;

import com.sunchao.rpc.common.ClientConfig;
import com.sunchao.rpc.common.Constants;

/**
 * Protocol Utility.
 * @author <a href="mailto:sunchao6106@163.com">sunchao</a>
 *
 */
public final class ProtocolUtil {

	public static String serviceKey(ClientConfig config) {
		return serviceKey(config.getHost(), config.getPort(), config.getParameter(Constants.SERVICE_NAME));
	}
	
	public static String serviceKey(String host, int port, String parameter) {
		StringBuilder sb = new StringBuilder();
		sb.append(host);
		sb.append(':');
		sb.append(port);
		sb.append('/');
		sb.append(parameter);
		return sb.toString();
	}

	//public static String serviceKey()
	private ProtocolUtil() {
		
	}
}
