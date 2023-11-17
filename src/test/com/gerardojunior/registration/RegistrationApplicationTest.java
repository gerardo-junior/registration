package com.gerardojunior.registration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RegistrationApplicationTest {

    @Autowired
    private RegistrationApplication registrationApplication;

    @Test
    void contextLoads() {
        // Verifica se o contexto da aplicação é carregado com sucesso
        // e se a instância da classe RegistrationApplication está disponível.
        assert registrationApplication != null;
    }
}