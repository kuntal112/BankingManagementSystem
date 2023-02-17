package com.casestudy.error;

public class LoanAmountLimitException extends Exception{
public LoanAmountLimitException() {
	super();
}

public LoanAmountLimitException(String message) {
	super(message);
	
}
public LoanAmountLimitException(String message,Throwable cause) {
	super(message,cause);
	
}
public LoanAmountLimitException(Throwable cause) {
	super(cause);
	
}
public LoanAmountLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
	
}


}
