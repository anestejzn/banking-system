package com.ftn.sbnz.tim5.service.controller;

import com.ftn.sbnz.tim5.model.User;
import com.ftn.sbnz.tim5.service.dto.request.VerifyRequest;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.WrongVerifyTryException;
import com.ftn.sbnz.tim5.service.services.interfaces.IClientService;

import com.ftn.sbnz.tim5.service.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private IUserService userService;

    @PutMapping("/activate-account")
    @ResponseStatus(HttpStatus.OK)
    public boolean update(@Valid @RequestBody VerifyRequest verifyRequest) throws EntityNotFoundException, WrongVerifyTryException {
        return userService.activate(verifyRequest.getVerifyId(), verifyRequest.getSecurityCode());
    }

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public User getById(@PathVariable String email) throws EntityNotFoundException {

        return userService.getVerifiedUser(email);
    }
}
