package com.afj.solution.buyitapp.payload.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
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
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "JwtResponse", description = "Access token response")
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = 2162442422246022981L;

    @ApiModelProperty(
            name = "token",
            dataType = "String",
            value = "JWT access token",
            example = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2MDg0NDk5MzEsImlhdCI6MTYwNzU4NTkzMSwidXNlcm5hbWUiOiJibGFja2puZWNvIn0"
    )
    private String token;
}
