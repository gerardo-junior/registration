package com.gerardojunior.registration.repositories;

import com.gerardojunior.registration.entity.meta.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private ListCrudRepository<User, String> listCrudRepository;

    @Mock
    private JpaSpecificationExecutor<User> specificationExecutor;

    @InjectMocks
    private UserRepository userRepository;

    @Test
    void findByEmail() {
        // Mocking
        String email = "test@example.com";
        User user = new User();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Test
        Optional<User> result = userRepository.findByEmail(email);

        // Verification
        assertEquals(Optional.of(user), result);
    }

    @Test
    void countByEmailOrDocument() {
        // Mocking
        String email = "test@example.com";
        String document = "123456789";
        when(userRepository.countByEmailOrDocument(email, document)).thenReturn(1);

        // Test
        Integer result = userRepository.countByEmailOrDocument(email, document);

        // Verification
        assertEquals(1, result);
    }

    @Test
    void findByDocument() {
        // Mocking
        String document = "123456789";
        User user = new User();
        when(userRepository.findByDocument(document)).thenReturn(Optional.of(user));

        // Test
        Optional<User> result = userRepository.findByDocument(document);

        // Verification
        assertEquals(Optional.of(user), result);
    }

    @Test
    void findAll() {
        // Mocking
        Specification<User> specification = mock(Specification.class);
        Pageable pageable = mock(Pageable.class);
        Page<User> page = mock(Page.class);
        when(userRepository.findAll(specification, pageable)).thenReturn(page);

        // Test
        Page<User> result = userRepository.findAll(specification, pageable);

        // Verification
        assertEquals(page, result);
    }
}