package com.sliit.fyp.servicesImpl;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.sliit.fyp.Models.Role;
import com.sliit.fyp.Models.RoleName;
import com.sliit.fyp.Models.User;
import com.sliit.fyp.Repositories.RoleRepository;
import com.sliit.fyp.Repositories.UserRepository;
import com.sliit.fyp.dto.OTPRequestDto;
import com.sliit.fyp.exception.AppException;
import com.sliit.fyp.payload.ApiResponse;
import com.sliit.fyp.payload.JwtAuthenticationResponse;
import com.sliit.fyp.payload.LoginRequest;
import com.sliit.fyp.payload.SignupRequest;
import com.sliit.fyp.security.CustomUserDetailService;
import com.sliit.fyp.security.UserPrinciple;
import com.sliit.fyp.security.jwt.JwtUtil;
import com.sliit.fyp.services.AuthService;
import com.sliit.fyp.util.EmailSender;
import com.sliit.fyp.util.RandomGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  CustomUserDetailService userDetailService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtUtil jwtutil;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  RandomGenerator randomGenerator;

  @Autowired
  UserRepository userRepository;

  @Autowired
  EmailSender emailSender;

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

  @Override
  public ResponseEntity<Object> authenticateUser(LoginRequest loginRequest) {

    String failMessage = "Login failed for user: {}\n{}";
    LOGGER.info(">>> Login request from user with username: {}", loginRequest.getUsername());

    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getUsername().trim(),
              loginRequest.getPassword().trim()));
      UserPrinciple userPrinciple = userDetailService.loadUserByUsername(loginRequest.getUsername().trim());

      Collection<? extends GrantedAuthority> roles = userPrinciple.getAuthorities();
      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = jwtutil.generateToken(loginRequest.getUsername());

      return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, roles.toString(), true));
    } catch (CredentialsExpiredException e) {
      LOGGER.info(failMessage, loginRequest.getUsername(), e.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(true, e.getMessage()));
    } catch (DisabledException | LockedException e) {
      LOGGER.info(failMessage, loginRequest.getUsername(), e.getMessage());
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(true, e.getMessage()));
    } catch (Exception e) {
      LOGGER.error(failMessage, loginRequest.getUsername(), e.getMessage());
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, e.getMessage()));
    }

  }

  @Override
  public ResponseEntity<Object> registerUser(SignupRequest signupRequest) {
    LOGGER.info(">>> Register User ; {}", signupRequest.getName());

    if (Boolean.FALSE.equals(userRepository.existsByEmail(signupRequest.getEmail().trim().toLowerCase()))) {
      try {
        // encode the user password
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        Set<Role> roles = new HashSet<>();

        // set Role to user
        switch (signupRequest.getRole()) {
          case "USER":
            roles.add(
                roleRepository.findByRoleName(RoleName.USER)
                    .orElseThrow(() -> new AppException("User Role not set.")));
            break;
          case "ADMIN":
            roles.add(
                roleRepository.findByRoleName(RoleName.ADMIN)
                    .orElseThrow(() -> new AppException("ADMIN Role not set.")));
            break;
          default:
            throw new IllegalArgumentException("Invalid ROLE  name: " + signupRequest.getRole());
        }
        // save the user
        User user = new User();
        String username = randomGenerator.getRandomUserName();
        user.setEmail(signupRequest.getEmail().trim().toLowerCase());
        user.setName(signupRequest.getName());
        user.setPassword(encodedPassword);
        user.setUserName(username);
        user.setRoles(roles);
        user.setCreatedAt(LocalDate.now());
        userRepository.save(user);
        emailSender
            .sendLoginUsername(new OTPRequestDto(username, signupRequest.getEmail(), 0, signupRequest.getName()));
        LOGGER.info("Successfully created user: {}", user.getUserName());
        return ResponseEntity.ok(new ApiResponse(true, "Successfully created the user"));

      } catch (Exception e) {

        LOGGER.error("Register failed\n{}", e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Unable to create user"));
      }
    } else {
      LOGGER.error("Register failed\n{}", "Email already in used");
      return ResponseEntity.status(HttpStatus.IM_USED).body(new ApiResponse(false, "Email already in used"));
    }

  }

}
