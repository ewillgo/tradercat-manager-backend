package net.tradercat.constant;

import org.trianglex.common.exception.ApiCode;

public enum UserApiCode implements ApiCode {

    CAPTCHA_INCORRECT(2000, "验证码错误"),
    CAPTCHA_TIMEOUT(2001, "验证码超时"),
    REGISTER_ERROR(2002, "注册失败");

    private Integer status;
    private String messge;

    UserApiCode(Integer status, String messge) {
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
