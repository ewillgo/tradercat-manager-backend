package net.tradercat.controller;

import net.tradercat.config.UserCentralProperties;
import net.tradercat.dto.UasResponse;
import net.tradercat.dto.LoginRequest;
import net.tradercat.dto.RegisterRequest;
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
import static net.tradercat.constant.UserApiCode.CAPTCHA_INCORRECT;
import static net.tradercat.constant.UserApiCode.CAPTCHA_TIMEOUT;
import static net.tradercat.constant.UserConstant.*;

@Controller
@RequestMapping(C_USER)
public class UserController {

    @Autowired
    private UserCentralProperties userCentralProperties;

    @ResponseBody
    @PostMapping(value = M_USER_POST_REGISTER)
    public Result<UasResponse> register(@Valid @RequestBody RegisterRequest registerRequest,
                                        @SessionAttribute(name = CAPTCHA_LOGIN, required = false) Captcha serverCaptcha,
                                        HttpSession session) {
        // 校验验证码
        processCaptcha(new Captcha(registerRequest.getCaptcha()), serverCaptcha, CAPTCHA_REGISTER, session);
        return Result.of(SUCCESS, processUasResponse(registerRequest));
    }

    @ResponseBody
    @PostMapping(value = M_USER_POST_LOGIN)
    public Result<UasResponse> login(@Valid @RequestBody LoginRequest loginRequest,
                                     @SessionAttribute(name = CAPTCHA_LOGIN, required = false) Captcha serverCaptcha,
                                     HttpSession session) {
        // 校验验证码
        processCaptcha(new Captcha(loginRequest.getCaptcha()), serverCaptcha, CAPTCHA_LOGIN, session);
        return Result.of(SUCCESS, processUasResponse(loginRequest));
    }

    @GetMapping(M_USER_GET_LOGIN_CAPTCHA)
    public String loginCaptcha(HttpSession session, HttpServletResponse response) {
        CaptchaRender captchaRender = new CaptchaRender(CAPTCHA_SIZE, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
        session.setAttribute(CAPTCHA_LOGIN, captchaRender.getCaptcha());
        captchaRender.render(response);
        return null;
    }

    @GetMapping(M_USER_GET_REGISTER_CAPTCHA)
    public String registerCaptcha(HttpSession session, HttpServletResponse response) {
        CaptchaRender captchaRender = new CaptchaRender(CAPTCHA_SIZE, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
        session.setAttribute(CAPTCHA_REGISTER, captchaRender.getCaptcha());
        captchaRender.render(response);
        return null;
    }

    private <T> UasResponse processUasResponse(T data) {
        UasResponse uasResponse = new UasResponse();
        uasResponse.setAppKey(userCentralProperties.getAppKey());
        uasResponse.setOriginalString(SignUtils.generateOriginalString(data));
        uasResponse.setSign(SignUtils.sign(data, userCentralProperties.getAppSecret()));
        return uasResponse;
    }

    private void processCaptcha(
            Captcha clientCaptcha, Captcha serverCaptcha, String captchaSessionKey, HttpSession session) {

        try {
            if (!CaptchaValidator.equalsIgnoreCase(clientCaptcha, serverCaptcha)) {
                throw new ApiErrorException(CAPTCHA_INCORRECT);
            }

            if (CaptchaValidator.isCaptchaTimeout(serverCaptcha)) {
                throw new ApiErrorException(CAPTCHA_TIMEOUT);
            }
        } finally {
            session.setAttribute(captchaSessionKey, null);
        }

    }
}
