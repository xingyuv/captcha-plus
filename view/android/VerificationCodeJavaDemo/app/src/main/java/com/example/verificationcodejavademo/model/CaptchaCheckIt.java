package com.example.verificationcodejavademo.model;

/**
 * Date:2020/5/18
 * author:wuyan
 */
public class CaptchaCheckIt {
    private String captchaType;
    private String token;
    private boolean result;
    private boolean opAdmin;

    public String getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(String captchaType) {
        this.captchaType = captchaType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isOpAdmin() {
        return opAdmin;
    }

    public void setOpAdmin(boolean opAdmin) {
        this.opAdmin = opAdmin;
    }
}
