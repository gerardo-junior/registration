package com.gerardojunior.registration.repositories;

import com.gerardojunior.registration.entity.meta.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, String>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(String email);

    Integer countByEmailOrDocument(String email, String document);

    Optional<User> findByDocument(String document);

    Page<User> findAll(Specification<User> specification, Pageable pageable);

}
