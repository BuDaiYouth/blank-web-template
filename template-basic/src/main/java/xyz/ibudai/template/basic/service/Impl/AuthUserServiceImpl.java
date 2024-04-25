package xyz.ibudai.template.basic.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import xyz.ibudai.template.basic.controller.LoginController;
import xyz.ibudai.template.basic.model.AuthUser;
import xyz.ibudai.template.basic.service.AuthUserService;
import xyz.ibudai.template.basic.util.AESUtil;
import xyz.ibudai.template.basic.util.JwtTokenUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    private final Logger log = LoggerFactory.getLogger(LoginController.class);

    private static final Map<String, String> userMap = new ConcurrentHashMap<>();

    private final static String DEFAULT_FE_KEY = "ibd.mock.data.ky";
    private final static String DEFAULT_FE_IV = "ibd.mock.data.iv";

    @Value("classpath:user.json")
    private Resource resource;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean login(AuthUser authUser, HttpServletResponse response) {
        boolean success;
        try {
            if (userMap.isEmpty()) {
                try (InputStream in = resource.getInputStream()) {
                    List<AuthUser> userList = objectMapper.readValue(in, new TypeReference<List<AuthUser>>() {
                    });
                    for (AuthUser item : userList) {
                        userMap.put(item.getUsername(), item.getPassword());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (userMap.isEmpty()) {
                throw new IllegalAccessException("There is not vail user, please register first.");
            }

            String username = authUser.getUsername();
            String password = userMap.get(username);
            if (StringUtils.isBlank(password)) {
                success = false;
                log.error("User [{}] is not existed.", username);
            } else {
                String backPwd = AESUtil.desEncrypt(password);
                String frontPwd = AESUtil.desEncrypt(authUser.getPassword(), DEFAULT_FE_KEY, DEFAULT_FE_IV);
                success = Objects.equals(backPwd, frontPwd);
            }
            if (success) {
                String key = objectMapper.writeValueAsString(authUser);
                String token = JwtTokenUtil.createJWT(key, TimeUnit.HOURS.toMillis(12));
                response.addHeader("Token", token);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return success;
    }
}
