package com.afj.solution.buyitapp.payload.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Tomash Gombosh
 */
@ApiModel(value = "UpdateUserRequest", description = "Update user request")
public record UpdateUserRequest(
        @ApiModelProperty(
                name = "firstName",
                dataType = "String",
                value = "First name of the user",
                example = "James",
                required = true
        )
        @NotEmpty
        @Size(max = 255)
        String firstName,
        @ApiModelProperty(
                name = "lastName",
                dataType = "String",
                value = "Last name of the user",
                example = "Smith",
                required = true
        )
        @NotEmpty
        @Size(max = 255)
        String lastName,
        @ApiModelProperty(
                name = "phone",
                dataType = "String",
                value = "Phone number of the user",
                example = "+38098XXXXXX",
                required = true
        )
        @NotEmpty
        @Size(max = 255)
        String phone,
        @ApiModelProperty(
                name = "homeAddress",
                dataType = "String",
                value = "Home Address of the user",
                example = "St. Zankovetskoy, 17, Uzhgorod, Ukraine 88000",
                required = true
        )
        @NotEmpty
        @Size(max = 255)
        String homeAddress
) {
}
