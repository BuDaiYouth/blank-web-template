package xyz.ibudai.template.basic.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ibudai.template.basic.model.Identity;
import xyz.ibudai.template.basic.util.JwtTokenUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/basic/user")
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(LoginController.class);

    private static final Map<String, String> userMap = new ConcurrentHashMap<>();


    @Value("classpath:user.json")
    private Resource resource;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("login")
    public Boolean login(@RequestBody Identity identity, HttpServletResponse response) {
        boolean success;
        try {
            if (userMap.isEmpty()) {
                try (InputStream in = resource.getInputStream()) {
                    List<Identity> userList = objectMapper.readValue(in, new TypeReference<List<Identity>>() {
                    });
                    for (Identity item : userList) {
                        userMap.put(item.getUsername(), item.getPassword());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            String username = identity.getUsername();
            String password = userMap.get(username);
            if (StringUtils.isBlank(password)) {
                success = false;
                log.error("User [{}] is not existed.", username);
            } else {
                success = Objects.equals(password, identity.getPassword());
            }
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
