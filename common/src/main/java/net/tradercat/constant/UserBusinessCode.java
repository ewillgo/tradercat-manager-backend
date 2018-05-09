package net.tradercat.constant;

import org.trianglex.common.exception.ApiCode;

public enum UserBusinessCode implements ApiCode {

    CAPTCHA_INCORRECT(2000, "验证码错误");

    private Integer status;
    private String messge;

    UserBusinessCode(Integer status, String messge) {
        this.status = status;
        this.messge = messge;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return messge;
    }
}
