package com.linhplus.UserManagement.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linhplus.UserManagement.dto.response.ResponseErrorDTO;
import com.linhplus.UserManagement.dto.response.ResponseObjectDTO;
import com.linhplus.UserManagement.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
public class CustomOnePerRequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equalsIgnoreCase("/auth/login")){
            filterChain.doFilter(request, response);
        }else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                String token = authorizationHeader.substring("Bearer ".length());
                try{
                    DecodedJWT decodedJWT = JWTUtil.getInstance().decodeToken(token);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                            = new UsernamePasswordAuthenticationToken(
                                    decodedJWT.getSubject()
                                    , null
                                    , decodedJWT.getClaim("roles").asList(String.class).stream().map(o ->
                                         new SimpleGrantedAuthority(o.toString())
                                    ).collect(Collectors.toList()));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    filterChain.doFilter(request, response);
                }catch (Exception e){
                    ResponseErrorDTO responseErrorDTO = new ResponseErrorDTO("Token is not valid", new HashMap<>());
                    responseErrorDTO.getErrors().put("cause", e.getMessage());
                    response.setStatus(400);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.getWriter().write(new ObjectMapper().writeValueAsString(responseErrorDTO));
                }
            }
            else {
                ResponseErrorDTO responseErrorDTO = new ResponseErrorDTO("Authentication header is not valid", null);
                response.setStatus(400);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(new ObjectMapper().writeValueAsString(responseErrorDTO));
            }
        }
    }
}
