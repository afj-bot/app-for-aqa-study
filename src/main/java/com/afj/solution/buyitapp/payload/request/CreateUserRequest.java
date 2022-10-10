package com.afj.solution.buyitapp.payload.request;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.afj.solution.buyitapp.common.Adapters;

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

    @Serial
    private static final long serialVersionUID = 3863852548921806327L;

    @ApiModelProperty(
            name = "firstName",
            dataType = "String",
            value = "First Name of the application User",
            example = "First Name"
    )
    @Size(max = 255, message = "error.value.string.max")
    private String firstName;

    @ApiModelProperty(
            name = "lastName",
            dataType = "String",
            value = "Last Name of the application User",
            example = "Last Name"
    )
    @Size(max = 255, message = "error.value.string.max")
    private String lastName;

    @ApiModelProperty(
            name = "username",
            dataType = "String",
            value = "username of the User",
            example = "Bedove",
            required = true
    )

    @Size(max = 255, message = "error.value.string.max")
    private String username;

    @ApiModelProperty(
            name = "email",
            dataType = "String",
            value = "User email",
            example = "test@gmail.com",
            required = true
    )
    @Email(message = "error.value.email")
    private String email;


    @ApiModelProperty(
            name = "password",
            dataType = "String",
            value = "password",
            example = "NewPassword!",
            required = true
    )
    @NotEmpty(message = "error.value.string.not-empty")
    private String password;

    @ApiModelProperty(
            name = "phoneNumber",
            dataType = "String",
            value = "User phone number",
            example = "0975255207"
    )
    @Size(max = 12, message = "error.value.string.max")
    private String phoneNumber;

    @ApiModelProperty(
            name = "homeAddress",
            dataType = "String",
            value = "User home address",
            example = "Some Street 123"
    )
    private String homeAddress;

    @ApiModelProperty(
            name = "privacyPolicy",
            dataType = "boolean",
            value = "The privacy policy of user",
            example = "true",
            required = true
    )
    private boolean privacyPolicy;

    @ApiModelProperty(
            name = "dateOfBirth",
            dataType = "ZonedDateTime",
            value = "User birthday",
            example = "07/27/2022"
    )
    @JsonDeserialize(using = Adapters.ZonedDateTimeDeserializer.class)
    private ZonedDateTime dateOfBirth;

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
