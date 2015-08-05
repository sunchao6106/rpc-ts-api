package com.sunchao.rpc.api;
/**
 * RPC invocation result.
 * 
 * @author <a href="mailto:sunchao6106@163.com">sunchao</a>
 *
 */
public class RpcResult implements Result {

	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}

	/**
	 * @param throwable the throwable to set
	 */
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	private Object result;
	
	private Throwable throwable;

	/**
	 * @return the throwable
	 */
	public Throwable getThrowable() {
		return throwable;
	}

	public Object getValue() {
		return result;
	}

	public boolean hasException() {
		return this.throwable != null;
	}

	public boolean hasCompleted() {
		return (!((result == null) && (throwable == null)));
	}

	public Object backward() throws Throwable {
		if (throwable != null)
			throw  throwable;
		return result;
	}
	
	public RpcResult(Object result) {
		this.result = result;
	}
	
	public RpcResult(Throwable t) {
		this.throwable = t;
	}
}
