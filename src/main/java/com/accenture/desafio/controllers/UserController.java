package com.accenture.desafio.controllers;


import com.accenture.desafio.objects.User;
import com.accenture.desafio.services.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("user")
    public ResponseEntity<Object> postUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("user")
    public ResponseEntity<Object> getAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping("login")
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        return userService.loginUser(user);
    }

    @GetMapping("profile/{userId}")
    public ResponseEntity<Object> getUserByIdAndToken(@PathVariable String userId, @RequestHeader("token") String token) {
        return userService.getUserByIdAndToken(userId,token);
    }
}
