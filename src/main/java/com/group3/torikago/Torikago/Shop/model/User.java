package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "user_name", length = 50)
    private String userName;
    @Column(name = "email", length = 50)
    private String email;
    @Column(name = "password", length = 64)
    private String password;
    @Column(name = "f_name", length = 80)
    private String fname;
    @Column(name = "l_name", length = 40)
    private String lname;
    @Column(name = "phone_number", length = 12)
    private String phoneNumber;
    @Column(name = "gender")
    private boolean gender;
    @Column(name = "address", length = 150)
    private String address;
    @Column(name = "profile_pic", length = 64)
    private String profilePic;
    @Column(name = "reset_password_token", length = 64)
    private String resetPasswordToken;
    @Column(name = "isEnable")
    private boolean enabled;
    @Column(name = "verification_code", length = 64, updatable = false)
    private String verificationCode;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role", referencedColumnName = "name")
    private Role role;
    @OneToMany(mappedBy = "userOrder")
    private List<Order> birdCageOrders;
    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_provider", length = 15)
    private AuthenticationProvider authProvider;
    @OneToMany(mappedBy = "userId")
    private List<CartItems> cartItems;
    @Column(name = "create_date",length = 10)
    private LocalDateTime createDate;
    @Column(name = "update_date",length = 10)
    private LocalDateTime updatedDate;
}
