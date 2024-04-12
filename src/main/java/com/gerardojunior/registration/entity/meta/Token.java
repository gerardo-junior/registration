package com.gerardojunior.registration.entity.meta;

import com.gerardojunior.registration.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Token {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String value;

    @Column
    @Enumerated(EnumType.STRING)
    private TokenType type = TokenType.BEARER;

    @Column
    private boolean revoked;

    @Column
    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
