package com.gerardojunior.registration.entity.meta;

import com.gerardojunior.registration.dto.UpdateUserRequest;
import com.gerardojunior.registration.enums.Gender;
import com.gerardojunior.registration.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true, length = 11)
    private String document;

    @Column(nullable = false, length = 100)
    private String firstname;

    @Column(nullable = false, length = 100)
    private String lastname;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 20)
    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false, length = 25, unique = true)
    private String email;

    @Column(nullable = false, length = 100, unique = true)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname.toLowerCase();
    }


    public void setLastname(String lastname) {
        this.lastname = lastname.toLowerCase();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void merge(UpdateUserRequest request) {
        this.firstname = request.getFirstname();
        this.lastname = request.getLastname();
        this.address = request.getAddress();
        this.gender = request.getGender();
        this.mobileNumber = request.getMobileNumber();
    }
}
