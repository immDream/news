package com.immdream.security.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.security.security
 *
 * @author immDream
 * @date 2023/04/11/23:29
 * @since 1.8
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    /**
     * 当用户尝试访问安全的REST资源而不提供任何凭据时，将调用此方法发送401 响应
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException == null ? "Unauthorized" : authException.getMessage());
    }
}