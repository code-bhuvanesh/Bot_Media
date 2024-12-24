package com.code_bhuvanesh.BotMedia.controllers;

import com.code_bhuvanesh.BotMedia.Model.User;
import com.code_bhuvanesh.BotMedia.Respones.LoginResponse;
import com.code_bhuvanesh.BotMedia.Respones.MainResponse;
import com.code_bhuvanesh.BotMedia.Respones.ResponseType;
import com.code_bhuvanesh.BotMedia.dtos.LoginUserDto;
import com.code_bhuvanesh.BotMedia.dtos.RegisterUserDto;
import com.code_bhuvanesh.BotMedia.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.code_bhuvanesh.BotMedia.services.JwtService;

@CrossOrigin(origins = "http://localhost:5174")
@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse()
                                        .setToken(jwtToken)
                                        .setExpiresIn(jwtService.getExpirationTime())
                                        .setResponse(ResponseType.SUCCESS);

        return ResponseEntity.ok(loginResponse);
    }
}