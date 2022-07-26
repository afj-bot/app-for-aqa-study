package com.afj.solution.buyitapp.controller.api.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.afj.solution.buyitapp.model.User;
import com.afj.solution.buyitapp.payload.response.JwtResponse;
import com.afj.solution.buyitapp.service.UserAuthServiceImpl;
import com.afj.solution.buyitapp.service.UserServiceImpl;

/**
 * @author Tomash Gombosh
 */
@RestController
@RequestMapping(path = "/api/v1/auth", produces = "application/json; charset=utf-8")
@Slf4j
public class AuthController {

    private final UserAuthServiceImpl userAuthService;
    private final UserServiceImpl userService;

    @Autowired
    public AuthController(final UserAuthServiceImpl userAuthService,
                          final UserServiceImpl userService) {
        this.userAuthService = userAuthService;
        this.userService = userService;
    }

    @ApiOperation("Generate anonymous cookie for user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Anonymous cookie generated successfully"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @GetMapping("/anonymous")
    public @ResponseBody
    ResponseEntity<Void> authAnonymous() {
        log.info("Set cookie to generate anonymous token");
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, userAuthService.generateAnonymousCookie().toString())
                .build();
    }

    @ApiOperation("Generate anonymous toke for user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Anonymous token generated successfully"),
            @ApiResponse(code = 401, message = "Invalid cookie supplied"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @PostMapping("/anonymous")
    public @ResponseBody
    JwtResponse authAnonymous(@CookieValue(name = "anonymous") final String anonymous) {
        log.info("Create anonymous token for user");
        userAuthService.checkAnonymousCookie(anonymous);
        final User anonymousUser = userService.saveAnonymous();
        return new JwtResponse(userAuthService.loginAnonymous(anonymous, anonymousUser.getId()));
    }

}

