package com.ftn.sbnz.tim5.service.repository;

import com.ftn.sbnz.tim5.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

    @Query("select e from Employer e where e.name=?1")
    Optional<Employer> getEmployerByName(String name);

}
