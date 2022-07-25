package com.afj.solution.buyitapp.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Tomash Gombosh
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @ApiModelProperty(
            name = "username",
            dataType = "String",
            value = "Username of the application User",
            example = "black",
            required = true
    )
    private String username;

    @ApiModelProperty(
            name = "password",
            dataType = "String",
            value = "Password of the application User",
            example = "rest1234",
            required = true
    )
    private String password;

    @Override
    public String toString() {
        return String.format("{ \"username\": \"%s\" }", this.getUsername());
    }
}
