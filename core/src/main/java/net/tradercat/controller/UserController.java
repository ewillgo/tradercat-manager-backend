package net.tradercat.controller;

import net.tradercat.config.UserCentralProperties;
import net.tradercat.dto.user.LoginRequest;
import net.tradercat.dto.user.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.trianglex.common.dto.Result;

import javax.validation.Valid;

import static net.tradercat.constant.UrlConstant.C_USER;
import static net.tradercat.constant.UrlConstant.M_USER_POST_LOGIN;

@Controller
@RequestMapping(C_USER)
public class UserController {

    @Autowired
    private UserCentralProperties userCentralProperties;

    @ResponseBody
    @PostMapping(value = M_USER_POST_LOGIN, produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(
            @Valid @ModelAttribute LoginRequest loginRequest, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("username", loginRequest.getUsername());
        redirectAttributes.addAttribute("password", loginRequest.getPassword());
        return "redirect:" + userCentralProperties.getLoginUrl();
    }
}
