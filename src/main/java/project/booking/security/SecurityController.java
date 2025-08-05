package project.booking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.booking.data.Customer;
import project.booking.data.CustomerRepository;
import project.booking.dto.AuthRequest;
import project.booking.dto.AuthResponse;
import project.booking.dto.RegisterRequest;
import project.booking.dto.AuthResponse;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    private CustomerRepository customerRepository;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @Autowired
    public SecurityController(CustomerRepository customerRepository,
                              JwtService jwtService,
                              PasswordEncoder passwordEncoder,
                              AuthenticationManager authenticationManager)
    {
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if(customerRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Exception , email already in use");
        }

        var customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role("USER")
                .build();

        customerRepository.save(customer);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AuthResponse.builder()
                        .token(jwtService.generateToken(customer))
                        .build());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var customer = customerRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException ("User not found"));

        return ResponseEntity.ok(AuthResponse.builder()
                .token(jwtService.generateToken(customer))
                .build());
    }



}
