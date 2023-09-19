package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
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
    @Column(name = "user_name",length = 50)
    private String userName;
    @Column(name = "email",length = 50)
    private String email;
    @Column(name = "password",length = 64)
    private String password;
    @Column(name = "f_name",length = 80)
    private String fname;
    @Column(name = "l_name",length = 40)
    private String lname;
    @Column(name = "phone_number",length = 12)
    private String phoneNumber;
    @Column(name = "gender")
    private boolean gender;
    @Column(name = "address",length = 150)
    private String address;
    @Column(name = "profile_pic",length = 64)
    private String profilePic;
    @Column(name = "reset_password_token", length = 50)
    private String resetPasswordToken;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")}
    )
    private List<Role> roles;
    @OneToMany(mappedBy = "userOrder")
    private List<BirdCageOrder> birdCageOrders;
    @OneToMany(mappedBy = "userVoucher")
    private List<Voucher> vouchers;
}
