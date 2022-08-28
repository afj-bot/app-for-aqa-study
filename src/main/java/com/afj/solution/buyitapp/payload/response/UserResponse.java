package com.afj.solution.buyitapp.payload.response;

import java.io.Serial;
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
 * @author Kristian Gombosh
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(value = "UserResponse", description = "Simple User response")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 5884159037589642451L;

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
            name = "username",
            dataType = "String",
            value = "Username of the application User",
            example = "Username"
    )
    private String username;

    @ApiModelProperty(
            name = "role",
            dataType = "String",
            value = "Role of the application User"
    )
    private Set<String> userRole;

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

    @ApiModelProperty(
            name = "dateOfBirth",
            dataType = "ZonedDateTime",
            value = "Date Of Birth of the application User"
    )
    private ZonedDateTime dateOfBirth;

    @ApiModelProperty(
            name = "accountNonExpired",
            dataType = "boolean",
            value = "Account Non Expired status of the application User"
    )
    private boolean accountNonExpired;

    @ApiModelProperty(
            name = "accountNonLocked",
            dataType = "boolean",
            value = "Account Non Locked status of the application User"
    )
    private boolean accountNonLocked;

    @ApiModelProperty(
            name = "credentialsNonExpired",
            dataType = "boolean",
            value = "Credentials Non Expired status of the application User"
    )
    private boolean credentialsNonExpired;

    @ApiModelProperty(
            name = "enabled",
            dataType = "boolean",
            value = "Is account enabled of the application User"
    )
    private boolean enabled;

    @ApiModelProperty(
            name = "createdAt",
            dataType = "ZonedDateTime",
            value = "User account creation date"
    )
    private ZonedDateTime createdAt;

    @ApiModelProperty(
            name = "updatedAt",
            dataType = "ZonedDateTime",
            value = "User account update date"
    )
    private ZonedDateTime updatedAt;

    public UserResponse(final Consumer<UserResponse> builder) {
        requireNonNull(builder).accept(this);
    }
}
