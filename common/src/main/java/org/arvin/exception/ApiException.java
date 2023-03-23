package org.arvin.exception;

import org.arvin.api.IErrorCode;

public class ApiException extends RuntimeException{
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message){
        super(message);
    }

    public ApiException(Throwable cause){
        super(cause);
    }

    public IErrorCode getErrorCode(){
        return errorCode;
    }
}
