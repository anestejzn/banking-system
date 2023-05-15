package com.ftn.sbnz.tim5.service.repository;

import com.ftn.sbnz.tim5.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long>{
}
