package com.example.verificationcodejavademo.network;

/**
 * Date:2020/5/18
 * author:wuyan
 */
public class BaseResponse<T> {
    private T repData;
    private String repCode;
    private boolean success;
    private boolean error;

    public T getRepData() {
        return repData;
    }

    public void setRepData(T repData) {
        this.repData = repData;
    }

    public String getRepCode() {
        return repCode;
    }

    public void setRepCode(String repCode) {
        this.repCode = repCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
