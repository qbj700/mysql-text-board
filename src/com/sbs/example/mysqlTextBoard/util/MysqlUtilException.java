package com.sbs.example.mysqlTextBoard.util;

import java.io.PrintStream;
import java.io.PrintWriter;

@SuppressWarnings("serial")
public class MysqlUtilException extends RuntimeException {
	private Exception originException;

	public MysqlUtilException(Exception e) {
		this.originException = e;
	}

	@Override
	public String getMessage() {
		return originException.getMessage();
	}

	@Override
	public String getLocalizedMessage() {
		return originException.getLocalizedMessage();
	}

	@Override
	public synchronized Throwable getCause() {
		return originException.getCause();
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return originException.getStackTrace();
	}

	@Override
	public String toString() {
		return originException.toString();
	}

	@Override
	public void printStackTrace() {
		originException.printStackTrace();
	}

	@Override
	public void printStackTrace(PrintStream s) {
		originException.printStackTrace(s);
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		originException.printStackTrace(s);
	}
}
