package com.yash.training.tcvm.CustomException;

public class InvalidInputException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidInputException(String msg){
		super(msg);
	}
}
