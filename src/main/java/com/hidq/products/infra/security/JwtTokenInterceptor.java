package com.hidq.products.infra.security;

import com.hidq.products.exception.CustomException;
import com.hidq.products.repository.UserRepository;
import com.hidq.products.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String token = this.recoverToken(request);
            if (token == null) {
                throw new CustomException("Error ao validar autenticação do usuario", HttpStatus.BAD_REQUEST.value());
            }
            String login = tokenService.validateToken(token);
            UserDetails user = userRepository.findByLogin(login);
            if (ObjectUtils.isEmpty(user)) {
                throw new CustomException("Erro ao validar autenticação do usuário", HttpStatus.UNAUTHORIZED.value());
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.UNAUTHORIZED.value());
        }
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}
