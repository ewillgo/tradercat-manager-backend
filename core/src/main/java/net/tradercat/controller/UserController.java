package net.tradercat.controller;

import net.tradercat.config.UserCentralProperties;
import net.tradercat.dto.user.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.trianglex.common.security.auth.SignUtils;
import org.trianglex.common.util.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;

import static net.tradercat.constant.UrlConstant.C_USER;
import static net.tradercat.constant.UrlConstant.M_USER_GET_LOGIN;

@Controller
@RequestMapping(C_USER)
public class UserController {

    @Autowired
    private UserCentralProperties userCentralProperties;

    @GetMapping(value = M_USER_GET_LOGIN, produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(
            @Valid @ModelAttribute LoginRequest loginRequest, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        redirectAttributes.addAttribute("username", loginRequest.getUsername());
        redirectAttributes.addAttribute("password", loginRequest.getPassword());
        redirectAttributes.addAttribute("originalString", SignUtils.generateOriginalString(loginRequest));
        redirectAttributes.addAttribute("appKey", userCentralProperties.getAppKey());
        redirectAttributes.addAttribute("sign",
                SignUtils.sign(JsonUtils.toJsonString(loginRequest), userCentralProperties.getAppSecret()));

        response.setHeader("Origin", "http://manager.sportsdb.cc");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

        try {
            response.sendRedirect(userCentralProperties.getLoginUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }

//        return "redirect:" + userCentralProperties.getLoginUrl();
        return null;
    }
}
