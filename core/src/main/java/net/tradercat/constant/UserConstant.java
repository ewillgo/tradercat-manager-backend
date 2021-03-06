package net.tradercat.constant;

public interface UserConstant {

    int CAPTCHA_SIZE = 5;
    int CAPTCHA_WIDTH = 150;
    int CAPTCHA_HEIGHT = 35;

    String USERNAME_NOT_BLANK = "1000#账号不能为空";
    String PASSWORD_NOT_BLANK = "1001#密码不能为空";
    String CAPTCHA_NOT_BLANK = "1002#验证码不能为空";
    String EMAIL_INCORRECT = "1003#邮箱格式不正确";
    String NICKNAME_INCORRECT = "1004#昵称由中文、字母和数字组成";

    String CAPTCHA_REGISTER = "__USER_REGISTER__";
    String CAPTCHA_LOGIN = "__USER_LOGIN__";
    String CAPTCHA_RESET_PASSWORD = "__USER_RESET_PASSWORD__";
}
