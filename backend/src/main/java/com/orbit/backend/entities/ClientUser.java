package com.orbit.backend.entities;

import com.orbit.backend.enums.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "client_information")
@Getter
@Setter

public class ClientUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;
    @NotBlank
    private String password;
    private String phone;
    private String company;
    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean approved = false;
    @Enumerated(EnumType.STRING)
    private UserRoles role = UserRoles.CLIENT;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
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
        return approved;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return approved;
    }

    @OneToMany(mappedBy = "client",fetch = FetchType.LAZY)
    private List<Project> projects = new ArrayList<>();
}
