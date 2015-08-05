package com.sunchao.rpc.api.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.sunchao.rpc.api.Invoker;
import com.sunchao.rpc.api.RpcInvocation;
/**
 * Invoker Handler.
 * 
 * @author <a href="mailto:sunchao6106@163.com">sunchao</a>
 *
 */
public class InvokerInvocationHandler implements InvocationHandler {

	private final Invoker<?> invoker;
	
	public InvokerInvocationHandler(Invoker<?> invoker) {
		this.invoker = invoker;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		String methodName = method.getName();
		Class<?>[] parameterTypes = method.getParameterTypes();
		if (method.getDeclaringClass() == Object.class) {
			return method.invoke(invoker, args);
		}
		if ("toString".equals(methodName) && parameterTypes.length == 0)
			return invoker.toString();
		if ("hashCode".equals(methodName) && parameterTypes.length == 0)
			return invoker.hashCode();
		if ("equals".equals(methodName) && parameterTypes.length == 1)
			return invoker.equals(args[0]);
		return invoker.invoke(new RpcInvocation(method, args)).backward();
	}

}
