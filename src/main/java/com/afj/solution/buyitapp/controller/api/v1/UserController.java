package com.afj.solution.buyitapp.controller.api.v1;

import java.util.UUID;

import javax.validation.Valid;

import com.afj.solution.buyitapp.common.Response;
import com.afj.solution.buyitapp.payload.request.CreateUserRequest;
import com.afj.solution.buyitapp.service.converters.CreateUserRequestToUser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.afj.solution.buyitapp.payload.response.UserResponse;
import com.afj.solution.buyitapp.security.JwtTokenProvider;
import com.afj.solution.buyitapp.service.UserServiceImpl;

import static com.afj.solution.buyitapp.common.Patterns.generateSuccessResponse;

/**
 * @author Tomash Gombosh
 */
@RestController
@RequestMapping(path = "/api/v1/users", produces = "application/json; charset=utf-8")
@Slf4j
public class UserController {

    private final UserServiceImpl userService;

    private final JwtTokenProvider jwtTokenProvider;
    private final CreateUserRequestToUser createUserRequestToUser;

    @Autowired
    public UserController(final UserServiceImpl userService,
                          final JwtTokenProvider jwtTokenProvider,
                          final CreateUserRequestToUser createUserRequestToUser) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.createUserRequestToUser = createUserRequestToUser;
    }

    @ApiOperation(value = "Get my account data", notes = "Anonymous, User Role", authorizations = {@Authorization("Bearer")})
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

    @ApiOperation("Create New User")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User created"),
            @ApiResponse(code = 409, message = "User Already exists"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public @ResponseBody
    Response<String> create(@Valid @RequestBody final CreateUserRequest createUserRequest) {
        userService.save(createUserRequestToUser.convert(createUserRequest));
        return generateSuccessResponse();
    }

}
