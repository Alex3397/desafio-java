package com.accenture.desafio.controllers;


import com.accenture.desafio.objects.User;
import com.accenture.desafio.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("user")
    private ResponseEntity<Object> postUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("user")
    private ResponseEntity<Object> getAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping("login")
    private ResponseEntity<Object> loginUser(@RequestBody User user) {
        return userService.loginUser(user);
    }

    @GetMapping("profile/{userId}")
    private ResponseEntity<Object> getUserByIdAndToken(@PathVariable String userId, @RequestHeader("token") String token) {
        return userService.getUserByIdAndToken(userId,token);
    }
}
