package com.said.B30.controllers;

import com.said.B30.dtos.userdtos.UserLoginDto;
import com.said.B30.dtos.userdtos.UserRequestDto;
import com.said.B30.dtos.userdtos.UserResponseDto;
import com.said.B30.dtos.userdtos.UserUpdateDto;
import com.said.B30.businessrules.services.UserService;
import com.said.B30.infrastructure.entities.User;
import com.said.B30.security.services.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    
    private static final String AUTH_COOKIE_NAME = "B30_AUTH_TOKEN";
    private static final int TOKEN_EXPIRATION_SECONDS = 60 * 15;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid UserLoginDto userLogin, HttpServletResponse response){
        try {
            var userNamePassword = new UsernamePasswordAuthenticationToken(userLogin.name(), userLogin.password());
            var auth = authenticationManager.authenticate(userNamePassword);

            String token = tokenService.generateToken((User) auth.getPrincipal());
            addAuthCookie(response, token, TOKEN_EXPIRATION_SECONDS);

            return "redirect:/home";
        } catch (AuthenticationException e) {
            return "redirect:/users/login?error=true";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        addAuthCookie(response, null, 0);
        return "redirect:/users/login";
    }

    private void addAuthCookie(HttpServletResponse response, String token, int maxAge) {
        Cookie cookie = new Cookie(AUTH_COOKIE_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequest){
        var obj = userService.createUser(userRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.id()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<UserResponseDto> updateUserData(@Valid @RequestBody UserUpdateDto userUpdateDto, @PathVariable Long id){
        return ResponseEntity.ok(userService.updateUserData(id, userUpdateDto));
    }

}
