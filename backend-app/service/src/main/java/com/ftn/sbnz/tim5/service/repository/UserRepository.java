package com.ftn.sbnz.tim5.service.repository;

import com.ftn.sbnz.tim5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email=?1")
    Optional<User> getVerifiedUser(String email);
}
