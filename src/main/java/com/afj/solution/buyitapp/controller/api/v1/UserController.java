package com.afj.solution.buyitapp.controller.api.v1;

import java.util.UUID;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.afj.solution.buyitapp.payload.response.UserResponse;
import com.afj.solution.buyitapp.security.JwtTokenProvider;
import com.afj.solution.buyitapp.service.UserServiceImpl;

/**
 * @author Tomash Gombosh
 */
@RestController
@RequestMapping(path = "/api/v1/users", produces = "application/json; charset=utf-8")
@Slf4j
public class UserController {

    private final UserServiceImpl userService;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(final UserServiceImpl userService,
                          final JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ApiOperation(value = "Get my account data", authorizations = {@Authorization("Bearer")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Request was successfully"),
            @ApiResponse(code = 401, message = "Invalid username/password supplied"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @GetMapping("/me")
    public @ResponseBody
    UserResponse me() {
        final String token = jwtTokenProvider.getToken().substring(7);
        final UUID userId = jwtTokenProvider.getUuidFromToken(token);
        log.info("Receive 'ME' request for user with username {} and id {}",
                jwtTokenProvider.getUsernameFromToken(token), userId);
        return userService.getMe(userId);
    }

}
