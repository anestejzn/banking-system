package com.ftn.sbnz.tim5.service.services.implementation;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ftn.sbnz.tim5.model.User;
import com.ftn.sbnz.tim5.service.dto.response.LoginResponse;
import com.ftn.sbnz.tim5.service.dto.response.UserResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.InvalidCredsException;
import com.ftn.sbnz.tim5.service.exception.InvalidJWTException;
import com.ftn.sbnz.tim5.service.security.FingerprintProperties;
import com.ftn.sbnz.tim5.service.security.FingerprintUtils;
import com.ftn.sbnz.tim5.service.security.JWTUtils;
import com.ftn.sbnz.tim5.service.security.UserPrinciple;
import com.ftn.sbnz.tim5.service.services.interfaces.IAuthService;
import com.ftn.sbnz.tim5.service.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


@Service

public class AuthService implements IAuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailService emailService;
    @Autowired
    private IUserService userService;

    @Override
    public LoginResponse login(final String email, final String password, final HttpServletResponse response) throws InvalidCredsException {
        Authentication authenticate;
        try {
            System.out.println("lalal");
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (Exception ignored) {
            throw new InvalidCredsException("Invalid creds!");
        }

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserPrinciple userPrinciple = (UserPrinciple) authenticate.getPrincipal();
        UserResponse userResponse = userPrinciple.getUser();

        String rawFingerprint = FingerprintUtils.generateRandomRawFingerprint();

        Cookie cookie = new Cookie(FingerprintProperties.FINGERPRINT_COOKIE, rawFingerprint);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(3600*4);
        response.addCookie(cookie);
        System.out.println("lalallal");

        return new LoginResponse(JWTUtils.generateJWT(email, rawFingerprint), userResponse);
    }


    @Override
    public void logout(HttpServletRequest request) {
        DecodedJWT jwt = JWTUtils.extractJWTFromRequest(request);
        try {
            String email = JWTUtils.extractEmailFromJWT(jwt);
            User usr = userService.getVerifiedUser(email);
        } catch (InvalidJWTException | EntityNotFoundException ignored) {
        }
    }
}
