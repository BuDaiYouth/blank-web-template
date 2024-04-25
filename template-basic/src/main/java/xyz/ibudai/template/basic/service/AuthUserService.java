package xyz.ibudai.template.basic.service;

import xyz.ibudai.template.basic.model.AuthUser;

import javax.servlet.http.HttpServletResponse;

public interface AuthUserService {

    boolean login(AuthUser authUser, HttpServletResponse response);
}
