package com.sunchao.rpc.service;

public interface GenericService {

	/**
	 * Generic invoke
	 * 
	 * @param method  method name.
	 * @param parameterTypes 
	 * @param args
	 * @return
	 * @throws GenericException
	 */
	Object $invoke(String method, String[] parameterTypes, Object[] args) throws GenericException;
}
