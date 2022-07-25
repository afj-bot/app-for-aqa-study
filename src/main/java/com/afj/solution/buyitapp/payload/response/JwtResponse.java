package com.afj.solution.buyitapp.payload.response;

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
public class JwtResponse {
    @ApiModelProperty(
            name = "token",
            dataType = "String",
            value = "JWT access token",
            example = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2MDg0NDk5MzEsImlhdCI6MTYwNzU4NTkzMSwidXNlcm5hbWUiOiJibGFja2puZWNvIn0",
            required = true
    )
    private String token;
}
