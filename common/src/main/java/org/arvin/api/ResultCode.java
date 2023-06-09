package org.arvin.api;

public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败");

    private long code;

    private String message;

    private ResultCode(long code,String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public long code() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
