package com.ecommerce.trendyoldemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @Column(name = "is_verified")
    private boolean verified;

    @NotNull
    @JsonIgnore
    private String password;

    private Integer userType;
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "user")
    private CustomerEntity customer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ProviderEntity provider;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Card card;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;

    public UserEntity(String username, String password) {
        this.email = username;
        this.password = password;
    }

}
