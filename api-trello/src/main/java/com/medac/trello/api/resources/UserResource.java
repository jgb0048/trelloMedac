package com.medac.trello.api.resources;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.medac.trello.api.model.User;
import com.medac.trello.api.model.UserRepository;
import com.medac.trello.api.request.LoginRequest;
import com.medac.trello.api.request.RegisterRequest;
import com.medac.trello.api.resources.exception.InvalidLoginCredentialsException;
import com.medac.trello.api.view.UserView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Map;


@RestController
@RequestMapping(value = "/user", produces = APPLICATION_JSON_VALUE)
public class UserResource implements TrelloApi {

    private final UserRepository userRepository;

    @Autowired
    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
    return userRepository.findOneByEmail(req.email())
      .filter(u -> u.getPassword().equals(req.password().trim()))
      .<ResponseEntity<?>>map(u -> ResponseEntity.ok(new UserView(u.getName())))
      .orElseGet(() -> ResponseEntity.status(401).body(Map.of("message","Invalid email or password")));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
    if (userRepository.findOneByEmail(req.email()).isPresent()) {
        return ResponseEntity.status(409).body(Map.of("message","Email already registered"));
    }
    var user = new User(req.name(), req.userName(), req.email(), req.password());
    userRepository.save(user);
    return ResponseEntity.ok(new UserView(user.getName()));
    }

}
