package com.example.user_management.security;

import com.example.user_management.common.AppConstants;
import com.example.user_management.common.ScriptingUtil;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Transient;

import java.util.Arrays;
import java.util.Collection;

@Transient
public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

    private String apiKey;

    public ApiKeyAuthenticationToken(String apiKey, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.apiKey = apiKey;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return apiKey;
    }
}
