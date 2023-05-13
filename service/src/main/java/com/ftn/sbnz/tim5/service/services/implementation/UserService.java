package com.ftn.sbnz.tim5.service.services.implementation;


import com.ftn.sbnz.tim5.model.RegistrationVerification;
import com.ftn.sbnz.tim5.model.User;
import com.ftn.sbnz.tim5.service.dto.response.UserResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.WrongVerifyTryException;
import com.ftn.sbnz.tim5.service.repository.UserRepository;
import com.ftn.sbnz.tim5.service.services.interfaces.IClientService;
import com.ftn.sbnz.tim5.service.services.interfaces.IRoleService;
import com.ftn.sbnz.tim5.service.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IClientService clientService;
    @Override
    public User getVerifiedUser(String email) throws EntityNotFoundException {
        return userRepository.getVerifiedUser(email)
                .orElseThrow(() -> new EntityNotFoundException("User is not found."));
    }

    @Override
    public UserResponse create(String email, String name, String surname, String password, String confirmPassword, String role) {
        return null;
    }


//    @Override
//    public boolean checkIfUserAlreadyExists(String email) {
//        Optional<User> user = userRepository.findByEmail(email);
//        return user.isPresent();
//    }


    @Override
    public boolean activate(String verifyId, int securityCode) throws WrongVerifyTryException, EntityNotFoundException {
        RegistrationVerification verify = verificationService.update(verifyId, securityCode);
        clientService.activateAccount(verify.getUserEmail());
        return true;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

}
