package com.ricomincia.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerDto {
    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    @Schema(
            description = "Name of the customer", example = "SwathiD"
    )
    private String name;

    @NotEmpty(message = "Name can not be a null or empty")
    @Email(message = "Email address should be a valid value")
    @Schema(
            description = "Email address of the customer", example = "example@gmail.com"
    )
    private String email;

    @Max(value=9999999999l, message="Mobile number must be 10 digits")
    @Schema(
            description = "Mobile Number of the customer", example = "9345432123"
    )
    private Long mobileNumber;

    @Valid
    @Schema(
            description = "Account details of the Customer"
    )
    private AccountsDto accountsDto;
}
