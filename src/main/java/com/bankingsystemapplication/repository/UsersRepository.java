package com.bankingsystemapplication.repository;

import com.bankingsystemapplication.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface UsersRepository  extends JpaRepository<Users,Long> {
    Users findByUsername(String username);
    List<Users> findUsersByUsername(String email);
}
