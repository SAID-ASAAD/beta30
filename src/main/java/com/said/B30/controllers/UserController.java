package com.said.B30.controllers;

import com.said.B30.dtos.userdtos.UserLoginDto;
import com.said.B30.dtos.userdtos.UserRequestDto;
import com.said.B30.dtos.userdtos.UserResponseDto;
import com.said.B30.dtos.userdtos.UserUpdateDto;
import com.said.B30.businessrules.services.UserService;
import com.said.B30.infrastructure.entities.User;
import com.said.B30.security.services.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequest){
        var obj = userService.createUser(userRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.id()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUserData(@Valid @RequestBody UserUpdateDto userUpdateDto, @PathVariable Long id){
        return ResponseEntity.ok(userService.updateUserData(id, userUpdateDto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDto userLogin){
        var userNamePassword = new UsernamePasswordAuthenticationToken(userLogin.name(), userLogin.password());
        var auth = authenticationManager.authenticate(userNamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(token);
    }
}
