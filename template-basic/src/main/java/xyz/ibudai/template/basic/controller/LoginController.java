package xyz.ibudai.template.basic.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ibudai.template.basic.model.Identity;
import xyz.ibudai.template.basic.util.JwtTokenUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/basic/user")
public class LoginController {

    @Autowired
    private ObjectMapper objectMapper;

    private static final String DEFAULT_NAME = "admin";
    private static final String DEFAULT_PWD = "NQNSLwaThtUHidJ+BuHoiQ==";

    @PostMapping("login")
    public Boolean login(@RequestBody Identity identity, HttpServletResponse response) {
        boolean success;
        try {
            String username = identity.getUsername();
            String password = identity.getPassword();
            success = Objects.equals(username, DEFAULT_NAME)
                    && Objects.equals(password, DEFAULT_PWD);
            if (success) {
                String key = objectMapper.writeValueAsString(identity);
                String token = JwtTokenUtil.createJWT(key, TimeUnit.HOURS.toMillis(12));
                response.addHeader("Token", token);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return success;
    }
}
