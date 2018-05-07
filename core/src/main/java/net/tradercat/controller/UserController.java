package net.tradercat.controller;

import net.tradercat.config.UserCentralProperties;
import net.tradercat.dto.user.LoginRequest;
import net.tradercat.dto.user.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trianglex.common.dto.Result;
import org.trianglex.common.security.auth.SignUtils;

import javax.validation.Valid;

import static net.tradercat.constant.UrlConstant.C_USER;
import static net.tradercat.constant.UrlConstant.M_USER_POST_LOGIN;

@Controller
@RequestMapping(C_USER)
public class UserController {

    @Autowired
    private UserCentralProperties userCentralProperties;

    @ResponseBody
    @PostMapping(value = M_USER_POST_LOGIN)
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        Result<LoginResponse> result = new Result<>();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAppKey(userCentralProperties.getAppKey());
        loginResponse.setOriginalString(SignUtils.generateOriginalString(loginRequest));
        loginResponse.setSign(SignUtils.sign(loginRequest, userCentralProperties.getAppSecret()));

        result.setStatus(0);
        result.setMessage("succeed");
        result.setData(loginResponse);
        return result;
    }
}
