package com.afj.solution.buyitapp.payload.response;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.util.Objects.requireNonNull;

/**
 * @author Tomash Gombsh
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(value = "CreatedByResponse", description = "Create By User response")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatedByResponse implements Serializable {

    private static final long serialVersionUID = 3253089907393733239L;

    @ApiModelProperty(
            name = "id",
            dataType = "String",
            value = "Uuid of the application User"
    )
    private UUID id;
    @ApiModelProperty(
            name = "firstName",
            dataType = "String",
            value = "First Name of the application User",
            example = "First Name"
    )
    private String firstName;

    @ApiModelProperty(
            name = "lastName",
            dataType = "String",
            value = "Last Name of the application User",
            example = "Last Name"
    )
    private String lastName;

    @ApiModelProperty(
            name = "email",
            dataType = "String",
            value = "Email of the application User",
            example = "Email"
    )
    private String email;

    @ApiModelProperty(
            name = "phoneNumber",
            dataType = "String",
            value = "Phone number of the application User"
    )
    private String phoneNumber;

    @ApiModelProperty(
            name = "homeAddress",
            dataType = "String",
            value = "Home address of the application User"
    )
    private String homeAddress;


    public CreatedByResponse(final Consumer<CreatedByResponse> builder) {
        requireNonNull(builder).accept(this);
    }
}
