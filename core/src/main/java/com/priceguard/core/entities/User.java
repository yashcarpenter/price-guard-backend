package com.priceguard.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    @Column(name = "user_name", unique = true)
    private String userName;
    @Column(name = "email", unique = true)
    private String email;
    private String password;
    private boolean emailVerified;

    @ElementCollection
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserProducts> userProducts;

    public User(String userEmail) {
        this.email = userEmail;
    }
}
