package xyz.ibudai.template.basic.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;

public class URIPattenUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(URIPattenUtil.class);

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    public static String[] slice(String uri, String prefix) {
        String[] uris = uri.split(",");
        if (StringUtils.isNoneBlank(prefix)) {
            if (uris.length > 0) {
                uris = Arrays.stream(uris)
                        .map(it -> prefix + it)
                        .toArray(String[]::new);
            }
        }
        return uris;
    }

    public static Boolean march(String target, String pattern, String prefix) {
        boolean isMarch = false;
        try {
            String[] pattens = URIPattenUtil.slice(pattern, prefix);
            for (String item : pattens) {
                isMarch = pathMatcher.match(item, target);
                if (isMarch) {
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.error("March path failed", e);
        }
        return isMarch;
    }
}
