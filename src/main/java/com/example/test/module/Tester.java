package com.example.test.module;

import com.example.test.enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Tester extends BaseEntity implements UserDetails {
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String middleName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true,nullable = false,length = 10)
    private String idNumber;
    @Column(nullable = false,unique = true)
    private String passportSerial;
    @Column(nullable = false,length = 500)
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType role;
    @ManyToOne(fetch = FetchType.EAGER)
    private Position position;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(
                new SimpleGrantedAuthority("ROLE_"+this.role)
        );
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.idNumber;
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
}
