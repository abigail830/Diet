package com.diet.admin.controller;

import com.diet.admin.core.BaseController;
import com.diet.admin.entity.TbUser;
import com.diet.admin.message.MsgCode;
import com.diet.admin.message.ResponseMsg;
import com.diet.admin.secruity.JwtAuthenticationRequest;
import com.diet.admin.service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * JWT授权
 *
 * @author LiuYu
 */
@Controller
@RequestMapping("/auth")
public class AuthController extends BaseController {

    @Autowired
    private AuthService authService;

    @ResponseBody
    @PostMapping(value = "/login")
    public ResponseMsg login(@RequestBody JwtAuthenticationRequest req) {
        ResponseMsg responseMsg = new ResponseMsg();
        if (StringUtils.isBlank(req.getUserName()) || StringUtils.isBlank(req.getPassword())) {
            return new ResponseMsg(MsgCode.Param_Error);
        }
        String userName = req.getUserName();
        String password = req.getPassword();
        String token = authService.login(userName, password);
        responseMsg.setData(token);
        return responseMsg;
    }

    @ResponseBody
    @PostMapping(value = "/refresh")
    public ResponseMsg refreshAndGetAuthToken() {
        ResponseMsg responseMsg = new ResponseMsg();
        String token = getTokenHeader();
        String refreshedToken = authService.refresh(token);
        if (StringUtils.isBlank(refreshedToken)) {
            return new ResponseMsg(-5, "刷新失败");
        }
        responseMsg.setData(refreshedToken);
        return responseMsg;
    }

    @ResponseBody
    @PostMapping(value = "/register")
    public ResponseMsg register(@RequestBody JwtAuthenticationRequest req) {
        ResponseMsg responseMsg = new ResponseMsg();
        if (StringUtils.isBlank(req.getUserName()) || StringUtils.isBlank(req.getPassword())) {
            return new ResponseMsg(MsgCode.Param_Error);
        }
        String userName = req.getUserName();
        String password = req.getPassword();
        TbUser tbUser = authService.register(userName, password);
        if (tbUser == null) {
            return new ResponseMsg(-6, userName + "已存在");
        }
        String token = authService.login(userName, password);
        responseMsg.setData(token);
        return responseMsg;
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        authService.logout(getTokenFromHeader());
        return "login.html";
    }
}
