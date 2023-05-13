package com.ftn.sbnz.tim5.service.services.interfaces;



import com.ftn.sbnz.tim5.service.dto.response.LoginResponse;
import com.ftn.sbnz.tim5.service.exception.InvalidCredsException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface IAuthService {
    void logout(HttpServletRequest request);
    LoginResponse login(final String email, final String password, final HttpServletResponse response) throws InvalidCredsException;
}
