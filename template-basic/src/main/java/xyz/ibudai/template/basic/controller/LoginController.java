package xyz.ibudai.template.basic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ibudai.template.basic.model.AuthUser;
import xyz.ibudai.template.basic.service.AuthUserService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/basic/user")
public class LoginController {

    @Autowired
    private AuthUserService authUserService;

    @PostMapping("login")
    public Boolean login(@RequestBody AuthUser authUser, HttpServletResponse response) {
        return authUserService.login(authUser, response);
    }
}
