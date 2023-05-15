package com.ftn.sbnz.tim5.service.services.interfaces;


import com.ftn.sbnz.tim5.model.Client;
import com.ftn.sbnz.tim5.model.Role;
import com.ftn.sbnz.tim5.service.dto.response.UserResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;

public interface IClientService {
    Client getClient(String email) throws EntityNotFoundException;
//    UserResponse create(
//            String email,
//            String name,
//            String surname,
//            String password,
//            Role role);
    boolean activateAccount(String userEmail) throws EntityNotFoundException;
    Client getClientById(Long id) throws EntityNotFoundException;
}
