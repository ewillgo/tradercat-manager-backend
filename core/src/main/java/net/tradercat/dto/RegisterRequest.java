package net.tradercat.dto;

import org.trianglex.common.validation.IsNickname;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static net.tradercat.constant.UserConstant.*;

public class RegisterRequest {

    @Email(message = EMAIL_INCORRECT)
    @NotBlank(message = USERNAME_NOT_BLANK)
    private String username;

    @IsNickname(message = NICKNAME_INCORRECT)
    private String nickname;

    @NotBlank(message = PASSWORD_NOT_BLANK)
    private String password;

    @NotBlank(message = CAPTCHA_NOT_BLANK)
    private String captcha;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
