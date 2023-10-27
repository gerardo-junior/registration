package com.gerardojunior.registration.repositories;

import com.gerardojunior.registration.entity.meta.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Integer countByEmailOrDocument(String email, String document);

    Optional<User> findByDocument(String document);
}
