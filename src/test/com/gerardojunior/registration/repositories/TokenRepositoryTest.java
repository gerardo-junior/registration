package com.gerardojunior.registration.repositories;
import com.gerardojunior.registration.entity.meta.Token;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.query.AbstractJpaQuery;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TokenRepository tokenRepository;

    @Test
    void findAllValidTokenByUser() {
        // Mocking
        Integer userId = 1;
        Token token1 = new Token();
        Token token2 = new Token();
        List<Token> tokens = List.of(token1, token2);

        Query query = mock(AbstractJpaQuery.class);
        when(entityManager.createNamedQuery(any(), eq(Token.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(tokens);

        // Test
        List<Token> result = tokenRepository.findAllValidTokenByUser(userId);

        // Verification
        assertEquals(tokens, result);
    }

    @Test
    void findByToken() {
        // Mocking
        String tokenValue = "testToken";
        Token token = new Token();

        Query query = mock(AbstractJpaQuery.class);
        when(entityManager.createNamedQuery(any(), eq(Token.class))).thenReturn(query);
        when(query.setParameter(any(String.class), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(token);

        // Test
        Optional<Token> result = tokenRepository.findByToken(tokenValue);

        // Verification
        assertEquals(Optional.of(token), result);
    }
}}