package com.gerardojunior.registration.dto;

import com.gerardojunior.registration.entity.meta.User;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SearchUserRequest {

    private String fullName;

    private String firstname;

    private String lastname;

    private String email;

    private String address;

    private String mobileNumber;

    private String createdAt;

    public Specification<User> toSpecification() {
        return (root, criteriaQuery, criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotEmpty(firstname)) {
                predicates.add(criteriaBuilder.like(root.get("firstname"), firstname));
            }
            if (StringUtils.isNotEmpty(email)) {
                predicates.add(criteriaBuilder.like(root.get("email"), email));
            }
            if (createdAt != null) {
                predicates.add(criteriaBuilder.equal(root.get("createdAt"), createdAt));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
