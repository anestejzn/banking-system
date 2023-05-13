package com.ftn.sbnz.tim5.service.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ftn.sbnz.tim5.model.User;
import com.ftn.sbnz.tim5.service.dto.response.UserResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.FingerprintCookieNotFoundException;
import com.ftn.sbnz.tim5.service.exception.InvalidJWTException;
import com.ftn.sbnz.tim5.service.services.implementation.UserService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.ftn.sbnz.tim5.service.security.JwtProperties.HEADER_STRING;
import static com.ftn.sbnz.tim5.service.security.JwtProperties.TOKEN_PREFIX;
import static com.ftn.sbnz.tim5.service.security.JwtProperties.SECRET;


//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final UserService userService;
//
//    public JwtAuthenticationFilter(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    protected void doFilterInternal(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull FilterChain filterChain
//    ) throws ServletException, IOException {
//        if (!headerIsInvalid(request.getHeader(HEADER_STRING))) {
//            Authentication authentication = getAuthentication(request);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private boolean headerIsInvalid(String header) {
//        return header == null || !header.startsWith(TOKEN_PREFIX) || header.equals(TOKEN_PREFIX);
//    }
//
//    private Authentication getAuthentication(HttpServletRequest request) {
//        try {
//            return getUsernamePasswordAuthentication(request);
//        } catch (EntityNotFoundException | InvalidJWTException exception) {
//            return null;
//        }
//    }
//
//    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) throws EntityNotFoundException, InvalidJWTException {
//        DecodedJWT jwt = JWTUtils.extractJWTFromRequest(request);
//        User user = userService.getVerifiedUser(JWTUtils.extractEmailFromJWT(jwt));
//
//        return getSpringAuthToken(user);
//    }
//
//    private UsernamePasswordAuthenticationToken getSpringAuthToken(User user) {
//        return getUsernamePasswordAuthenticationToken(new UserResponse(user));
//    }
//
//    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(UserResponse userResponse) {
//        UserPrinciple principal = new UserPrinciple(userResponse);
//
//        return new UsernamePasswordAuthenticationToken(
//                userResponse.getEmail(),
//                principal.getPassword(),
//                principal.getAuthorities()
//        );
//    }
//
//    private String getFingerprintFromCookie(HttpServletRequest request) throws FingerprintCookieNotFoundException {
//        Cookie fingerprintCookie = Arrays.stream(request.getCookies())
//                .filter(cookie -> FingerprintProperties.FINGERPRINT_COOKIE.equals(cookie.getName()))
//                .findFirst().orElse(null);
//
//        if (fingerprintCookie == null)
//            throw new FingerprintCookieNotFoundException("Cookie does not contain fingerprint!");
//
//        return fingerprintCookie.getValue();
//    }
//}

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private final UserService userService;

    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            UserService userService
    ) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JwtProperties.HEADER_STRING);

        if (headerIsInvalid(header)) {
            chain.doFilter(request, response);
        }
        else {
            Authentication authentication = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        Authentication authentication = null;
        try {
            authentication = getUsernamePasswordAuthentication(request);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        return authentication;
    }

    private boolean headerIsInvalid(String header){

        return header == null || !header.startsWith(TOKEN_PREFIX);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request)
            throws EntityNotFoundException {
        String token = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(TOKEN_PREFIX,"");

        String email = JWT.require(HMAC512(SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();

        return emailIsNotNull(email) ? getSpringAuthToken(email) : null;
    }

    private UsernamePasswordAuthenticationToken getSpringAuthToken(String email)
            throws EntityNotFoundException
    {
        UserResponse userDTO = new UserResponse(userService.getVerifiedUser(email));
        return getUsernamePasswordAuthenticationToken(userDTO);
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(UserResponse userDTO) {
        UserPrinciple principal = new UserPrinciple(userDTO);

        return new UsernamePasswordAuthenticationToken(
                userDTO.getEmail(),
                principal.getPassword(),
                principal.getAuthorities()
        );
    }

    private boolean emailIsNotNull(String email){
        return email != null;
    }
}