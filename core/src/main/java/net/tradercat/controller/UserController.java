package net.tradercat.controller;

import net.tradercat.config.UserCentralProperties;
import net.tradercat.dto.user.LoginRequest;
import net.tradercat.dto.user.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.trianglex.common.dto.Result;
import org.trianglex.common.exception.BusinessException;
import org.trianglex.common.security.auth.SignUtils;
import org.trianglex.common.support.captcha.Captcha;
import org.trianglex.common.support.captcha.CaptchaRender;
import org.trianglex.common.support.captcha.CaptchaValidator;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static net.tradercat.constant.UrlConstant.*;

@Controller
@RequestMapping(C_USER)
public class UserController {

    private static final int CAPTCHA_SIZE = 5;
    private static final int CAPTCHA_WIDTH = 150;
    private static final int CAPTCHA_HEIGHT = 40;
    private static final String CAPTCHA_REGISTER = "__USER_REGISTER__";
    private static final String CAPTCHA_LOGIN = "__USER_LOGIN__";
    private static final String CAPTCHA_RESET_PASSWORD = "__USER_RESET_PASSWORD__";

    @Autowired
    private UserCentralProperties userCentralProperties;

    @ResponseBody
    @PostMapping(value = M_USER_POST_LOGIN)
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest,
                                       @SessionAttribute(CAPTCHA_LOGIN) Captcha captcha) {

        Result<LoginResponse> result = new Result<>();

        if (captcha == null || CaptchaValidator.isCaptchaTimeout(captcha)) {
            throw new BusinessException("");
        }

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAppKey(userCentralProperties.getAppKey());
        loginResponse.setOriginalString(SignUtils.generateOriginalString(loginRequest));
        loginResponse.setSign(SignUtils.sign(loginRequest, userCentralProperties.getAppSecret()));

        result.setStatus(0);
        result.setMessage("succeed");
        result.setData(loginResponse);
        return result;
    }

    @GetMapping(M_USER_GET_LOGIN_CAPTCHA)
    public String loginCaptcha(HttpSession session, HttpServletResponse response) {
        CaptchaRender captchaRender = new CaptchaRender(CAPTCHA_SIZE, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
        session.setAttribute(CAPTCHA_LOGIN, captchaRender.getCaptcha());
        captchaRender.render(response);
        return null;
    }
}
