package com.sunchao.rpc.api;
/**
 * RPC invoke result
 * 
 * @author <a href="mailto:sunchao6106@163.com">sunchao</a>
 *
 */
public interface Result {
	/**
	 * Get the invoke result.
	 * 
	 * @return
	 */
    Object getValue();
    
    /**
     * Get the invoke exception
     * 
     * @return
     */
    Throwable getThrowable();
    
    /**
     * Judge the invoke whether has exception.
     * 
     * @return
     */
    boolean hasException();
    
    /**
     * Judge the invoke whether or not completed.
     * Contain the normal result or exception.
     * 
     * @return
     */
    boolean hasCompleted();
    
    /**
     * <code>
     * if (hasThrowable()) {
     * throw getThrowable();
     * }
     * return getValue()
     * </code>
     * 
     * @return
     * @throws Throwable
     */
    Object backward() throws Throwable;
}
