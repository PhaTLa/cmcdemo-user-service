package com.example.user_management.filter;

import com.example.user_management.common.AppConstants;
import com.example.user_management.common.ScriptingUtil;
import com.example.user_management.security.ApiKeyAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class ApiKeyAuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            String apiKey = getApiKey((HttpServletRequest) servletRequest);
            log.info("API key:  {}",apiKey);
            if(apiKey != null) {
                String decodedServiceID = ScriptingUtil.decodeBase64(apiKey);
                if(Arrays.asList(AppConstants.SERVICE_PRODUCT_ID).contains(decodedServiceID)){
                    ApiKeyAuthenticationToken apiToken = new ApiKeyAuthenticationToken(apiKey, AuthorityUtils.NO_AUTHORITIES);
                    SecurityContextHolder.getContext().setAuthentication(apiToken);
                }else {
                    HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
                    httpResponse.setStatus(401);
                    httpResponse.getWriter().write("Invalid API Key");
                    return;
                }
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getApiKey(HttpServletRequest httpRequest) {
        String apiKey = null;

        String authHeader = httpRequest.getHeader(AppConstants.API_KEY_HEADER);
        if(authHeader != null) {
            authHeader = authHeader.trim();
            if(authHeader.startsWith(AppConstants.API_KEY_PLACEHOLDER)) {
                apiKey = authHeader.substring(AppConstants.API_KEY_PLACEHOLDER.length()).trim();
            }
        }

        return apiKey;
    }
}
