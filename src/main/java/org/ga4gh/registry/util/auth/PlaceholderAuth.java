package org.ga4gh.registry.util.auth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ga4gh.registry.exception.ForbiddenException;
import org.springframework.beans.factory.annotation.Value;

public class PlaceholderAuth {

    @Value("${registry.auth.secret}")
    private String secret;

    public PlaceholderAuth() {

    }

    public void authorize(String authHeader) throws ForbiddenException {
        if (authHeader == null) {
            throw new ForbiddenException("no authorization token provided");
        }
        Pattern pattern = Pattern.compile("^bearer (.+)$");
        Matcher matcher = pattern.matcher(authHeader);
        if (!matcher.find()) {
            throw new ForbiddenException("authorization header malformed");
        }
        String token = matcher.group(1);
        if (!getSecret().equals(token)) {
            throw new ForbiddenException("invalid token");
        }
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }
}