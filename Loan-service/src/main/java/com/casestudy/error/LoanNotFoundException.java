package com.casestudy.error;

public class LoanNotFoundException extends Exception{
public LoanNotFoundException() {
	super();
}

public LoanNotFoundException(String message) {
	super(message);
	
}
public LoanNotFoundException(String message,Throwable cause) {
	super(message,cause);
	
}
public LoanNotFoundException(Throwable cause) {
	super(cause);
	
}
public LoanNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
	
}


}
