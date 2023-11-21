package com.gerardojunior.registration.dto;

import com.gerardojunior.registration.entity.meta.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class SearchUserRequestTest {

    @Test
    void testToSpecification() {
        // Arrange
        SearchUserRequest searchUserRequest = SearchUserRequest.builder()
                .fullName("John Doe")
                .email("john.doe@example.com")
                .createdAt("2022-01-01")
                .build();

        // Mocking JPA Criteria API components
        Root<User> root = mock(Root.class);
        CriteriaQuery<?> criteriaQuery = mock(CriteriaQuery.class);
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);

        // Act
        Specification<User> specification = searchUserRequest.toSpecification();
        Specification<User> result = specification.and((root1, criteriaQuery1, criteriaBuilder1) -> null);

        // Call the toPredicate method of Specification
        Predicate predicate = result.toPredicate(root, criteriaQuery, criteriaBuilder);

        // Assert
        assertEquals(3, predicate.getExpressions().size()); // Three fields are used in the specification: fullName, email, createdAt
    }

    @Test
    void testToSpecificationWithEmptyValues() {
        // Arrange
        SearchUserRequest searchUserRequest = new SearchUserRequest();

        // Mocking JPA Criteria API components
        Root<User> root = mock(Root.class);
        CriteriaQuery<?> criteriaQuery = mock(CriteriaQuery.class);
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);

        // Act
        Specification<User> specification = searchUserRequest.toSpecification();
        Specification<User> result = specification.and((root1, criteriaQuery1, criteriaBuilder1) -> null);

        // Call the toPredicate method of Specification
        Predicate predicate = result.toPredicate(root, criteriaQuery, criteriaBuilder);

        // Assert
        assertEquals(0, predicate.getExpressions().size()); // No fields are used in the specification as all values are empty
    }
}