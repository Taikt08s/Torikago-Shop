package com.group3.torikago.Torikago.Shop.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoucherDTO {
    private Long id;
    @Length(max = 50,message = "No more than 50 characters")
    private String voucherName;
    @NotNull
    private float voucherValue;
    @NotNull
    private LocalDateTime createdTime;
    @NotNull
    private LocalDateTime expiredTime;
    @NotNull
    private double maxValue;
    private Boolean status;




}
