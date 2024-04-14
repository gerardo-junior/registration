package com.gerardojunior.registration.repositories;

import com.gerardojunior.registration.entity.meta.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {

    List<Token> findAllByUser_IdAndExpiredFalseAndRevokedFalse(UUID userId);

    Optional<Token> findByValue(String value);

}