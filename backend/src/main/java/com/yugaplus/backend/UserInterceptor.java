package com.yugaplus.backend;

import java.io.IOException;
import java.util.Optional;

import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yugaplus.backend.api.Status;
import com.yugaplus.backend.api.UserResponse;
import com.yugaplus.backend.config.SecurityConfig;
import com.yugaplus.backend.config.UserRecord;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {

    private String apiKey;

    public UserInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Optional<UserRecord> user = SecurityConfig.getAuthenticatedUser();

        if (!user.isEmpty())
            return true;

        if (request.getHeader("X-Api-Key") != null && request.getHeader("X-Api-Key").equals(apiKey)) {
            return true;
        }

        UserResponse userResponse = new UserResponse(new Status(false, HttpServletResponse.SC_UNAUTHORIZED), null);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writeValueAsString(userResponse);
            response.getWriter().write(json);
            response.getWriter().flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}