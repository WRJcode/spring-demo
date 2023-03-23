package org.arvin.api;


public class CommonResult<T> {

    private long code;

    private String message;

    private T data;

    protected CommonResult(){

    }

    public long getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    protected CommonResult(long code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     *
     * @param data
     * @return
     * @param <T>
     */
    public static <T> CommonResult<T> success(T data){
        return new CommonResult<>(ResultCode.SUCCESS.code(),ResultCode.SUCCESS.getMessage(),data);
    }

    /**
     *
     * @param data
     * @param message
     * @return
     * @param <T>
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.code(), message, data);
    }

    /**
     *
     * @param errorCode
     * @return
     * @param <T>
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(errorCode.code(), errorCode.getMessage(), null);
    }

    /**
     *
     * @param errorCode
     * @param message
     * @return
     * @param <T>
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode,String message) {
        return new CommonResult<T>(errorCode.code(), message, null);
    }

    /**
     *
     * @param message
     * @return
     * @param <T>
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAILED.code(), message, null);
    }

    /**
     *
     * @return
     * @param <T>
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }
}
