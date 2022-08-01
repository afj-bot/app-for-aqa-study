package com.afj.solution.buyitapp.payload.request;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.afj.solution.buyitapp.constans.Patterns.GSON;

/**
 * @author Kristian Gombosh
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "CreateUserRequest", description = "Create user request model")
public class CreateUserRequest implements Serializable {

    private static final long serialVersionUID = 3863852548921806327L;

    @ApiModelProperty(
            name = "firstName",
            dataType = "String",
            value = "First Name of the application User",
            example = "First Name"
    )
    @Size(max = 255)
    private String firstName;

    @ApiModelProperty(
            name = "lastName",
            dataType = "String",
            value = "Last Name of the application User",
            example = "Last Name"
    )
    @Size(max = 255)
    private String lastName;

    @ApiModelProperty(
            name = "username",
            dataType = "String",
            value = "username of the User",
            example = "Bedove",
            required = true
    )

    @Size(max = 255)
    private String username;

    @ApiModelProperty(
            name = "email",
            dataType = "String",
            value = "User email",
            example = "test@gmail.com",
            required = true
    )
    @Email
    private String email;


    @ApiModelProperty(
            name = "password",
            dataType = "String",
            value = "password",
            example = "NewPassword!",
            required = true
    )
    private String password;

    @ApiModelProperty(
            name = "phoneNumber",
            dataType = "String",
            value = "User phone number",
            example = "0975255207"
    )
    @Size(max = 12)
    private String phoneNumber;

    @ApiModelProperty(
            name = "homeAddress",
            dataType = "String",
            value = "User home address",
            example = "Some Street 123"
    )
    private String homeAddress;

    @ApiModelProperty(
            name = "dateOfBirth",
            dataType = "ZonedDateTime",
            value = "User birthday",
            example = "07/27/2022"
    )
    private ZonedDateTime dateOfBirth;

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
