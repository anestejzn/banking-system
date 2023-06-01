package com.ftn.sbnz.tim5.service.controller;


import com.ftn.sbnz.tim5.service.dto.request.LoginRequest;
import com.ftn.sbnz.tim5.service.dto.request.RetireeRegistrationRequest;
import com.ftn.sbnz.tim5.service.dto.response.LoginResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.InvalidCredsException;
import com.ftn.sbnz.tim5.service.services.interfaces.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
    @Autowired
    private IAuthService authService;

    @PostMapping(path="/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@Valid @RequestBody final LoginRequest loginRequest, HttpServletResponse response) throws InvalidCredsException, EntityNotFoundException {
        return authService.login(loginRequest.getEmail(), loginRequest.getPassword(), response);
    }


    @PostMapping(path = "/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(final HttpServletRequest request) {
        authService.logout(request);
    }

}
