package noemicoppotelli.final_project_backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import noemicoppotelli.final_project_backend.entities.User;
import noemicoppotelli.final_project_backend.payloads.AuthResponse;
import noemicoppotelli.final_project_backend.payloads.LoginRequest;
import noemicoppotelli.final_project_backend.payloads.RegisterRequest;
import noemicoppotelli.final_project_backend.repositories.UserRepository;
import noemicoppotelli.final_project_backend.security.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email già registrata");
        }

        User.Ruolo ruolo = (request.ruolo() == null || request.ruolo().isBlank())
                ? User.Ruolo.USER
                : User.Ruolo.valueOf(request.ruolo());

        User user = new User();
        user.setNome(request.nome());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRuolo(ruolo);

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Registrazione avvenuta con successo");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        String token = jwtUtils.generateToken(request.email());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
