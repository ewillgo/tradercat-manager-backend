package net.tradercat.constant;

public interface UserConstant {

    int CAPTCHA_SIZE = 5;
    int CAPTCHA_WIDTH = 150;
    int CAPTCHA_HEIGHT = 35;

    String USERNAME_NOT_BLANK = "1000#账号不能为空";
    String PASSWORD_NOT_BLANK = "1001#密码不能为空";
    String CAPTCHA_NOT_BLANK = "1002#验证码不能为空";

    String CAPTCHA_REGISTER = "__USER_REGISTER__";
    String CAPTCHA_LOGIN = "__USER_LOGIN__";
    String CAPTCHA_RESET_PASSWORD = "__USER_RESET_PASSWORD__";
}
