package com.gerardojunior.registration.dto;

import com.gerardojunior.registration.entity.meta.User;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

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
        return (root, criteriaQuery, criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotEmpty(fullName)) {
//                predicates.add(criteriaBuilder.like(criteriaBuilder.concat(root.get("firstname"), root.get("lastname")), fullName));

                predicates.add(criteriaBuilder.like(criteriaBuilder.concat(criteriaBuilder.concat(root.get("firstname"), " ") , root.get("lastname")), fullName.toLowerCase()));
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
