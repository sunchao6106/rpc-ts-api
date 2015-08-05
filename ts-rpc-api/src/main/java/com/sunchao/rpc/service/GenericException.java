package com.sunchao.rpc.service;

public class GenericException extends RuntimeException {

	/**
	 * @return the exceptionClass
	 */
	public String getExceptionClass() {
		return exceptionClass;
	}

	/**
	 * @param exceptionClass the exceptionClass to set
	 */
	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
	}

	/**
	 * @return the exceptionMessage
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	/**
	 * @param exceptionMessage the exceptionMessage to set
	 */
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6406359488362488828L;
	
	private String exceptionClass;
	
	private String exceptionMessage;
	
	public GenericException() {
		
	}
	
	public GenericException(String exceptionClass, String exceptionMessage) {
		super(exceptionMessage);
		this.exceptionClass = exceptionClass;
		this.exceptionMessage = exceptionMessage;
	}
	
	public GenericException(Throwable cause) {
		super(cause.getMessage());
		this.exceptionClass = cause.getClass().getName();
		this.exceptionMessage = cause.getMessage();
	}

}
