package com.group3.torikago.Torikago.Shop.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "*Required")
    private String voucherName;
    @NotNull(message = "*Required")
    @Min(value = 0, message = "*Greater than 0%")
    @Max(value = 100, message = "*Less than 100%")
    private Float voucherValue;
    @NotNull(message = "*Required")
    private LocalDateTime createdTime;
    @NotNull(message = "*Required")
    private LocalDateTime expiredTime;
    @NotNull(message = "*Required")
    private Double maxValue;
    private Boolean status;




}
