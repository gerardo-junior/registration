package com.gerardojunior.registration.dto;

import com.gerardojunior.registration.entity.meta.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserRequest {

    private String fullName;

    private String email;

    private String address;

    private String mobileNumber;

    private String createdAt;

    public Specification<User> toSpecification() {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotEmpty(fullName)) {
                String[] names = fullName.trim().split("\\s+");
                if (names.length > 0) {
                    List<Predicate> namePredicates = new ArrayList<>();
                    for (String name : names) {
                        namePredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstname")), "%" + name.toLowerCase() + "%"));
                        namePredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastname")), "%" + name.toLowerCase() + "%"));
                    }
                    predicates.add(criteriaBuilder.or(namePredicates.toArray(new Predicate[0])));
                }
            }

            if (StringUtils.isNotEmpty(email)) {
                predicates.add(criteriaBuilder.equal(root.get("email"), email));
            }

            if (Objects.nonNull(createdAt)) {
                predicates.add(criteriaBuilder.equal(root.get("createdAt"), createdAt));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}