package com.priceguard.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "name")
    private String name;

    @Column(name="mobile_number" )
    private String mobileNumber;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @ElementCollection
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserProducts> userProducts;

    public User(String userEmail) {
        this.email = userEmail;
    }
}
