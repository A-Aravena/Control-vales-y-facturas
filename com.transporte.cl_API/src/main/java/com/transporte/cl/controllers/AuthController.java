package com.transporte.cl.controllers;

import com.transporte.cl.models.DAO.User.GetUserAllDAO;
import com.transporte.cl.models.DAO.User.SelectUserDAO;
import com.transporte.cl.models.ERole;
import com.transporte.cl.models.Role;
import com.transporte.cl.models.User;
import com.transporte.cl.payload.request.LoginRequest;
import com.transporte.cl.payload.request.SignupRequest;
import com.transporte.cl.payload.response.JwtResponse;
import com.transporte.cl.payload.response.MessageResponse;
import com.transporte.cl.repository.RoleRepository;
import com.transporte.cl.repository.UserRepository;
import com.transporte.cl.security.jwt.JwtUtils;
import com.transporte.cl.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Value("${images.folder}")
  private String imagesFolder;

  @CrossOrigin("*")
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
                         userDetails.getId(),
                         userDetails.getNombres(),
                         userDetails.getUsername(), 
                         userDetails.getEmail(),
                         roles,
                         userDetails.getEmpresa()));
  }

  @CrossOrigin("*")
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username está en uso!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email está en uso!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getNombres(),signUpRequest.getUsername(),
               signUpRequest.getEmail(),
               signUpRequest.getNotificacion(),encoder.encode(signUpRequest.getPassword()), signUpRequest.getEmpresa());

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_TRANSPORTE)
          .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "general":
          Role adminRole = roleRepository.findByName(ERole.ROLE_GENERAL)
              .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
          roles.add(adminRole);
          break;

        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_TRANSPORTE)
              .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
          roles.add(userRole);
        }
      });
    }
    user.setNotificacion(false);
    user.setRoles(roles);
    userRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("Usuario registrado!"));
  }

  @CrossOrigin("*")
  @PutMapping("/update/{id}")
  public User updateUser(@PathVariable Long id, @RequestBody SignupRequest signupRequest) {
    Optional<User> userOpt = userRepository.findById(id);

    if(userOpt.isPresent()) {
      User userIngreso = userOpt.get();
      String pass = userIngreso.getPassword();
      userIngreso.setNombres(signupRequest.getNombres());
      userIngreso.setUsername(signupRequest.getUsername());
      userIngreso.setNotificacion(signupRequest.getNotificacion());
      userIngreso.setEmail(signupRequest.getEmail());
      userIngreso.setEmpresa(signupRequest.getEmpresa());

      // Actualizar roles
      Set<String> strRoles = signupRequest.getRole();
      Set<Role> roles = new HashSet<>();

      if (strRoles == null) {
        Role userRole = roleRepository.findByName(ERole.ROLE_TRANSPORTE)
                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
        roles.add(userRole);
      } else {
        strRoles.forEach(role -> {
          switch (role) {
            case "general":
              Role adminRole = roleRepository.findByName(ERole.ROLE_GENERAL)
                      .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
              roles.add(adminRole);
              break;
            default:
              Role userRole = roleRepository.findByName(ERole.ROLE_TRANSPORTE)
                      .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
              roles.add(userRole);
          }
        });
      }

      userIngreso.setRoles(roles);

          if(signupRequest.getPassword() == null || signupRequest.getPassword().isEmpty())
        userIngreso.setPassword(encoder.encode(userIngreso.getPassword()));
      else {
        userIngreso.setPassword(encoder.encode(signupRequest.getPassword()));
      }



      userRepository.save(userIngreso);
    }

    return null;
  }



  @CrossOrigin("*")
  @GetMapping("/select")
  public List<SelectUserDAO> selectUser() {
    List<Object[]> users = userRepository.selectUser();
    return users.stream().map(
            row -> new SelectUserDAO(
                    (Long) row[0],
                    (String) row[1]
            )
    ).collect(Collectors.toList());
  }

  @CrossOrigin("*")
  @GetMapping("/usuariosFull")
  public List<GetUserAllDAO> getUsersFull() {
    List<Object[]> users = userRepository.getUsersFull();
    return users.stream().map(
            row -> new GetUserAllDAO(
                    (Long) row[0],
                    (String) row[1],
                    (String) row[2],
                    (Boolean) row[3],
                    (String) row[4]

            )
    ).collect(Collectors.toList());
  }

}
