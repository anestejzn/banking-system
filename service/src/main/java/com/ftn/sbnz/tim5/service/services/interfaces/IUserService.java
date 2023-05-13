package com.ftn.sbnz.tim5.service.services.interfaces;



import com.ftn.sbnz.tim5.model.User;
import com.ftn.sbnz.tim5.service.dto.response.UserResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.WrongVerifyTryException;


public interface IUserService {
    User getVerifiedUser(String email) throws EntityNotFoundException;
    UserResponse create(
            String email,
            String name,
            String surname,
            String password,
            String confirmPassword,
            String role
    );
    boolean activate(String verifyId, int securityCode) throws WrongVerifyTryException, EntityNotFoundException;
    User save(User user);
}
