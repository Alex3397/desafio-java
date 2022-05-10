package com.accenture.desafio.services;

import com.accenture.desafio.objects.Error;
import com.accenture.desafio.objects.User;
import com.accenture.desafio.repositories.UserRepository;
import com.accenture.desafio.utils.OAuth;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PhoneService phoneService;

    public ResponseEntity<Object> findAllUsers() {
        return ResponseEntity.status(200).body(userRepository.findAll());
    }

    @SneakyThrows
    public ResponseEntity<Object> createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(403).body(new Error("E-mail já existente"));
        }

        var now = LocalDateTime.now().toString();
        user.setCreated(now);
        user.setModified(now);
        user.setLast_login(now);

        user.setToken(OAuth.createJWT(user.getId(), user.getName(), user.getName(), System.currentTimeMillis()));
        user.setId(String.valueOf(UUID.nameUUIDFromBytes(user.getName().getBytes())));

        user.setPassword(OAuth.hashString(user.getPassword()));
        user.setToken(OAuth.hashString(user.getToken()));

        phoneService.savePhones(user.getPhones());
        userRepository.save(user);
        return ResponseEntity.status(201).body(user);
    }

    public ResponseEntity<Object> loginUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank())
            return ResponseEntity.status(400).body(new Error("Email não pode estar em branco"));
        if (user.getPassword() == null || user.getPassword().isBlank())
            return ResponseEntity.status(400).body(new Error("Senha não pode estar em branco"));

        var userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(400).body(new Error("Usuário e/ou senha inválidos"));
        }

        var foundUser = userOptional.get();
        if (!OAuth.hashString(user.getPassword()).equals(foundUser.getPassword())) {
            return ResponseEntity.status(401).body(new Error("Usuário e/ou senha inválidos"));
        }

        foundUser.setLast_login(LocalDateTime.now().toString());
        foundUser.setToken(OAuth.createHashedPassword(foundUser));
        userRepository.save(foundUser);

        return ResponseEntity.status(200).body(foundUser);
    }

    public ResponseEntity<Object> getUserByIdAndToken(String userId, String token) {
        if (token == null || token.isBlank()) return ResponseEntity.status(403).body(new Error("Não autorizado"));
        if (userRepository.findByToken(token).isEmpty()) return ResponseEntity.status(403).body(new Error("Não autorizado"));
        var foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) return ResponseEntity.status(403).body(new Error("Não autorizado"));

        User user = foundUser.get();
        if (!LocalDateTime.now().minusMinutes(30).isBefore(LocalDateTime.parse(user.getLast_login()))) return ResponseEntity.status(403).body(new Error("Sessão inválida"));

        return ResponseEntity.status(200).body(user);
    }
}
