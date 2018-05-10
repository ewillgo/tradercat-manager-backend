package net.tradercat.controller;

import net.tradercat.config.UserCentralProperties;
import net.tradercat.dto.user.LoginRequest;
import net.tradercat.dto.user.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.trianglex.common.dto.Result;
import org.trianglex.common.exception.ApiErrorException;
import org.trianglex.common.security.auth.SignUtils;
import org.trianglex.common.support.captcha.Captcha;
import org.trianglex.common.support.captcha.CaptchaRender;
import org.trianglex.common.support.captcha.CaptchaValidator;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static net.tradercat.constant.CommonCode.SUCCESS;
import static net.tradercat.constant.UrlConstant.*;
import static net.tradercat.constant.UserBusinessCode.CAPTCHA_INCORRECT;
import static net.tradercat.constant.UserConstant.*;

@Controller
@RequestMapping(C_USER)
public class UserController {

    @Autowired
    private UserCentralProperties userCentralProperties;

    @ResponseBody
    @PostMapping(value = M_USER_POST_LOGIN)
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest,
                                       @SessionAttribute(name = CAPTCHA_LOGIN, required = false) Captcha captcha,
                                       HttpSession session) {

        if (captcha == null || CaptchaValidator.isCaptchaTimeout(captcha)
                || !loginRequest.getCaptcha().equalsIgnoreCase(captcha.getCaptcha())) {
            session.setAttribute(CAPTCHA_LOGIN, null);
            throw new ApiErrorException(CAPTCHA_INCORRECT);
        }

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAppKey(userCentralProperties.getAppKey());
        loginResponse.setOriginalString(SignUtils.generateOriginalString(loginRequest));
        loginResponse.setSign(SignUtils.sign(loginRequest, userCentralProperties.getAppSecret()));

        return Result.of(SUCCESS, loginResponse);
    }

    @GetMapping(M_USER_GET_LOGIN_CAPTCHA)
    public String loginCaptcha(HttpSession session, HttpServletResponse response) {
        CaptchaRender captchaRender = new CaptchaRender(CAPTCHA_SIZE, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
        session.setAttribute(CAPTCHA_LOGIN, captchaRender.getCaptcha());
        captchaRender.render(response);
        return null;
    }

}
