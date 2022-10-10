package com.afj.solution.buyitapp.controller.api.v1;

import java.util.UUID;
import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.afj.solution.buyitapp.common.Response;
import com.afj.solution.buyitapp.payload.request.CreateUserRequest;
import com.afj.solution.buyitapp.payload.request.UpdateUserRequest;
import com.afj.solution.buyitapp.payload.response.UserResponse;
import com.afj.solution.buyitapp.security.JwtTokenProvider;
import com.afj.solution.buyitapp.service.user.UserServiceImpl;

import static com.afj.solution.buyitapp.constans.Patterns.generateSuccessResponse;

/**
 * @author Tomash Gombosh
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/users", produces = "application/json; charset=utf-8")
public class UserController {

    private final UserServiceImpl userService;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(final UserServiceImpl userService,
                          final JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ApiOperation(value = "Get my account data", notes = "Admin, User Role", authorizations = {@Authorization("Bearer")})
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

    @ApiOperation(value = "Create New User", notes = "Anonymous Role", authorizations = {@Authorization("Bearer")})
    @ApiResponses({
            @ApiResponse(code = 201, message = "User created"),
            @ApiResponse(code = 400, message = "User Already exists"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public @ResponseBody
    Response<String> create(@Valid @RequestBody final CreateUserRequest createUserRequest) {
        final String token = jwtTokenProvider.getToken().substring(7);
        final UUID userId = jwtTokenProvider.getUuidFromToken(token);
        log.info("Registration an user for id {} with data({})", userId, createUserRequest);
        userService.createUser(createUserRequest, userId);
        return generateSuccessResponse();
    }

    @ApiOperation(value = "Update my data", notes = "All Roles", authorizations = {@Authorization("Bearer")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "User updated"),
            @ApiResponse(code = 400, message = "User not exist"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/me")
    public @ResponseBody
    Response<String> updateCurrentUser(@Valid @RequestBody final UpdateUserRequest updateUserRequest) {
        final String token = jwtTokenProvider.getToken().substring(7);
        final UUID userId = jwtTokenProvider.getUuidFromToken(token);
        userService.updateUser(userId, updateUserRequest);
        return generateSuccessResponse();
    }
}
