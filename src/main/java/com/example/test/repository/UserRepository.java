package com.example.test.repository;

import com.example.test.enums.UserType;
import com.example.test.module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmail(String email);
    Boolean existsByEmail(String email);
    List<User> findAllByRole(UserType userType);


}
