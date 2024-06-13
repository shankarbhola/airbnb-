package com.airbnb.config;

import com.airbnb.entity.User;
import com.airbnb.payload.ErrorDto;
import com.airbnb.repository.UserRepository;
import com.airbnb.service.JWTService;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Component
public class JWTResponseFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private UserRepository userRepository;
    private final Gson gson = new Gson();

    public JWTResponseFilter(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(8, tokenHeader.length() - 1);

            try {
                String username = jwtService.getUsername(token);
                System.out.println(username);
                Optional<User> opUser = userRepository.findByUsername(username);
                if (opUser.isPresent()) {
                    User user = opUser.get();
                    System.out.println(user.getRole().getRoleName());
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, "null", Collections.singleton(new SimpleGrantedAuthority(user.getRole().getRoleName())));
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                ErrorDto dto = new ErrorDto(e.getMessage(), new Date(), request.getRequestURI());
                response.getWriter().write(gson.toJson(dto));
                return;
            }


        }
        filterChain.doFilter(request, response);
    }
}
