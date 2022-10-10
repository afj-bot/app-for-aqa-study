package com.afj.solution.buyitapp.payload.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Tomash Gombsh
 */
@ApiModel(value = "CreatedByResponse", description = "Create By User response")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreatedByResponse(

        @ApiModelProperty(
                name = "id",
                dataType = "String",
                value = "Uuid of the application User"
        )
        UUID id,
        @ApiModelProperty(
                name = "firstName",
                dataType = "String",
                value = "First Name of the application User",
                example = "First Name"
        )
        String firstName,
        @ApiModelProperty(
                name = "lastName",
                dataType = "String",
                value = "Last Name of the application User",
                example = "Last Name"
        )
        String lastName,
        @ApiModelProperty(
                name = "email",
                dataType = "String",
                value = "Email of the application User",
                example = "Email"
        )
        String email,
        @ApiModelProperty(
                name = "phoneNumber",
                dataType = "String",
                value = "Phone number of the application User"
        )
        String phoneNumber,
        @ApiModelProperty(
                name = "homeAddress",
                dataType = "String",
                value = "Home address of the application User"
        )
        String homeAddress) {
}
