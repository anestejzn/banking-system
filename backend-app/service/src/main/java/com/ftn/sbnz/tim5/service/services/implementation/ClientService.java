package com.ftn.sbnz.tim5.service.services.implementation;

import com.ftn.sbnz.tim5.model.Client;
import com.ftn.sbnz.tim5.model.Role;
import com.ftn.sbnz.tim5.service.dto.response.UserResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.repository.ClientRepository;
import com.ftn.sbnz.tim5.service.services.interfaces.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client getClient(String email) throws EntityNotFoundException {
        return clientRepository.getClientByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User is not found."));
    }

//    @Override
//    public UserResponse create(String email, String name, String surname, String password, Role role) {
//        String salt = generateRandomString(SALT_LENGTH);
//        String hashedPassword = getHash(password);
//        Client client = clientRepository.save(
//                new Client(email, hashedPassword, name, surname, salt, AccountStatus.NON_CSR,
//                        ZERO_FAILED_ATTEMPTS, null, role
//                ));
//
//        return new UserDTO(regularUser);
//    }


    public boolean activateAccount(String userEmail) throws EntityNotFoundException {
        Client client = this.getClient(userEmail);
        client.setVerified(true);
        clientRepository.save(client);

        return true;
    }

    @Override
    public Client getClientById(Long id) throws EntityNotFoundException {
        return null;
    }
}
