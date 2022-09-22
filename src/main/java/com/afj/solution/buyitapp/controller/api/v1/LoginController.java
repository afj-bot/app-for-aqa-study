package com.afj.solution.buyitapp.controller.api.v1;

import java.io.IOException;
import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.afj.solution.buyitapp.payload.request.LoginRequest;
import com.afj.solution.buyitapp.payload.response.JwtResponse;
import com.afj.solution.buyitapp.service.user.UserAuthServiceImpl;

/**
 * @author Tomash Gombosh
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/v1",
        produces = "application/json; charset=utf-8")
public class LoginController {

    private final UserAuthServiceImpl userAuthService;

    @Autowired
    public LoginController(final UserAuthServiceImpl userAuthService) {
        this.userAuthService = userAuthService;
    }

    @ApiOperation("Login user into the application")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Login was successfully"),
            @ApiResponse(code = 302, message = "Accept privacy policy or User locked or User blocked"),
            @ApiResponse(code = 401, message = "Invalid username/password supplied"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Object login(@Valid @RequestBody final LoginRequest loginRequest) throws IOException {
        log.info("Receive login request from username -> {}", loginRequest.toString());
        final Object loginResult = userAuthService.login(loginRequest);
        return loginResult instanceof String
                ? new JwtResponse((String) loginResult)
                : loginResult;
    }

}
