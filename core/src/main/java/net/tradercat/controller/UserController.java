package net.tradercat.controller;

import net.tradercat.dto.LoginRequest;
import net.tradercat.dto.RegisterRequest;
import net.tradercat.dto.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.trianglex.common.dto.Result;
import org.trianglex.common.exception.ClientApiException;
import org.trianglex.common.security.auth.SignUtils;
import org.trianglex.common.support.captcha.Captcha;
import org.trianglex.common.support.captcha.CaptchaRender;
import org.trianglex.common.support.captcha.CaptchaValidator;
import org.trianglex.usercentral.api.UasClient;
import org.trianglex.usercentral.api.core.UasProperties;
import org.trianglex.usercentral.api.dto.UasRegisterRequest;
import org.trianglex.usercentral.api.dto.UasRegisterResponse;
import org.trianglex.usercentral.api.enums.GenderType;

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
    private UasClient uasClient;

    @Autowired
    private UasProperties uasProperties;

    @ResponseBody
    @PostMapping(value = M_USER_POST_REGISTER)
    public Result<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest,
                                             @SessionAttribute(name = CAPTCHA_REGISTER, required = false) Captcha serverCaptcha,
                                             HttpSession session) {
        // 校验验证码
        processCaptcha(new Captcha(registerRequest.getCaptcha()), serverCaptcha, CAPTCHA_REGISTER, session);

        UasRegisterRequest uasRegisterRequest = new UasRegisterRequest();
        uasRegisterRequest.setUsername(registerRequest.getUsername());
        uasRegisterRequest.setPassword(registerRequest.getPassword());
        uasRegisterRequest.setNickname(registerRequest.getNickname());
        uasRegisterRequest.setGender(GenderType.SECRET.getCode());
        uasRegisterRequest.setAppKey(uasProperties.getAppKey());
        uasRegisterRequest.setSign(SignUtils.sign(registerRequest, uasProperties.getAppSecret()));

        Result<UasRegisterResponse> result = uasClient.register(uasRegisterRequest);
        return Result.of(result.getStatus(), result.getMessage(),
                result.getStatus() == SUCCESS.getStatus().intValue()
                        ? new RegisterResponse(result.getData().getTicketString())
                        : null
        );
    }

    @ResponseBody
    @PostMapping(value = M_USER_POST_LOGIN)
    public Result login(@Valid @RequestBody LoginRequest loginRequest,
                        @SessionAttribute(name = CAPTCHA_LOGIN, required = false) Captcha serverCaptcha,
                        HttpSession session) {
        // 校验验证码
        processCaptcha(new Captcha(loginRequest.getCaptcha()), serverCaptcha, CAPTCHA_LOGIN, session);
        return Result.of(SUCCESS);
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

    private void processCaptcha(
            Captcha clientCaptcha, Captcha serverCaptcha, String captchaSessionKey, HttpSession session) {

        try {
            if (!CaptchaValidator.equalsIgnoreCase(clientCaptcha, serverCaptcha)) {
                throw new ClientApiException(CAPTCHA_INCORRECT);
            }

            if (CaptchaValidator.isCaptchaTimeout(serverCaptcha)) {
                throw new ClientApiException(CAPTCHA_TIMEOUT);
            }
        } finally {
            session.setAttribute(captchaSessionKey, null);
        }

    }
}
