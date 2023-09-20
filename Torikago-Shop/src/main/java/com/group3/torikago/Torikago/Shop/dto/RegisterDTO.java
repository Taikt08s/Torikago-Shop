package com.group3.torikago.Torikago.Shop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private Long id;
    @NotEmpty(message = "Username is required")
    @Length(max = 15,message = "No more than 15 characters")
    private String userName;
    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"
            , message = "Minimum 8 characters, at least one uppercase letter and number")
    @NotEmpty(message = "Password is required")
    private String password;
    @NotEmpty(message = "First name is required")
    private String fname;
    @NotEmpty(message = "Last name is required")
    private String lname;
    @NotEmpty(message = "Address is required")
    @Length(max = 150,message = "No more than 150 characters")
    private String address;
    @NotEmpty(message = "Phone number is required")
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b"
            , message = "Please enter a valid (+84) phone number")
    private String phoneNumber;
    @NotNull(message = "Gender is required")
    private Boolean gender;
    private String profilePic;
    private String verificationCode;
}
