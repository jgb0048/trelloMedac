package com.medac.trello.api.resources;

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


@RestController
@RequestMapping(value = "/user", produces = APPLICATION_JSON_VALUE)
public class UserResource implements TrelloApi {

    private final UserRepository userRepository;

    @Autowired
    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<UserView> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userRepository.findOneByEmail(request.email())
                .filter(user -> user.getPassword().equals(request.password().trim()))
                .map(user -> new UserView(user.getName()))
                .orElseThrow(InvalidLoginCredentialsException::new));
    }

    @PostMapping("/register")
    public ResponseEntity<UserView> register(@Valid @RequestBody RegisterRequest request) {
        final var newUser = new User(request.name(),request.userName(), request.email(), request.password());
        return ResponseEntity.ok(new UserView(userRepository.save(newUser).getName()));
    }
}
