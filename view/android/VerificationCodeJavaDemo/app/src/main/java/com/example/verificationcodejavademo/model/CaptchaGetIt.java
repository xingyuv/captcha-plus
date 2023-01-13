package com.example.verificationcodejavademo.model;

/**
 * Date:2020/5/18
 * author:wuyan
 */
public class CaptchaGetIt {
    private String originalImageBase64;//图表url 目前用base64 data
    private String jigsawImageBase64;
    private Point point;
    private String token;//获取的token 用于校验
    private boolean result;
    private boolean opAdmin;
    private String secretKey;

    public String getOriginalImageBase64() {
        return originalImageBase64;
    }

    public void setOriginalImageBase64(String originalImageBase64) {
        this.originalImageBase64 = originalImageBase64;
    }

    public String getJigsawImageBase64() {
        return jigsawImageBase64;
    }

    public void setJigsawImageBase64(String jigsawImageBase64) {
        this.jigsawImageBase64 = jigsawImageBase64;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
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

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
