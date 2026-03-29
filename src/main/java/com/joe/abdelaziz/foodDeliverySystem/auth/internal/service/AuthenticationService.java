package com.joe.abdelaziz.foodDeliverySystem.auth.internal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.joe.abdelaziz.foodDeliverySystem.common.exception.BusinessLogicException;
import com.joe.abdelaziz.foodDeliverySystem.customer.api.dto.CustomerDTO;
import com.joe.abdelaziz.foodDeliverySystem.customer.api.service.CustomerService;
import com.joe.abdelaziz.foodDeliverySystem.iam.api.auth.AuthenticationRequest;
import com.joe.abdelaziz.foodDeliverySystem.iam.api.auth.AuthenticationResponse;
import com.joe.abdelaziz.foodDeliverySystem.iam.api.auth.RegisterRequest;
import com.joe.abdelaziz.foodDeliverySystem.iam.api.enums.RoleType;
import com.joe.abdelaziz.foodDeliverySystem.iam.api.enums.TokenType;
import com.joe.abdelaziz.foodDeliverySystem.iam.api.model.AppUser;
import com.joe.abdelaziz.foodDeliverySystem.auth.internal.security.JwtService;
import com.joe.abdelaziz.foodDeliverySystem.auth.internal.entity.Token;
import com.joe.abdelaziz.foodDeliverySystem.auth.internal.repository.TokenRepository;
import com.joe.abdelaziz.foodDeliverySystem.shipping.api.dto.CourierDTO;
import com.joe.abdelaziz.foodDeliverySystem.shipping.api.service.CourierService;
import com.joe.abdelaziz.foodDeliverySystem.user.api.dto.AdminDTO;
import com.joe.abdelaziz.foodDeliverySystem.user.api.service.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final CourierService courierService;
  private final AdminService adminService;
  private final CustomerService customerService;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    if (request.getRole() == null) {
      throw new BusinessLogicException("You should provide a valid role (USER, ADMIN, COURIER)");
    }

    UserDetails user = createUser(request);
    Map<String, Object> claims = new HashMap<>();
    claims.put("authorities", user.getAuthorities());
    String jwtToken = jwtService.generateToken(claims, user);
    String refreshToken = jwtService.generateRefreshToken(claims, user);
    saveUserToken(user, jwtToken);

    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    // Remember, the username is a combination of the role + phone number.
    // which is used in [CustomUserDetailsService] class
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getRole().toString() + ":" + request.getPhoneNumber(),
            request.getPassword()));

    UserDetails user = findUserByPhoneNumber(request.getPhoneNumber(), request.getRole());
    if (user == null) {
      throw new BusinessLogicException("No user found with this phone number");
    }

    Map<String, Object> claims = new HashMap<>();
    claims.put("authorities", user.getAuthorities());
    String jwtToken = jwtService.generateToken(claims, user);
    String refreshToken = jwtService.generateRefreshToken(claims, user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);

    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }

  public AuthenticationResponse refreshToken(HttpServletRequest request) {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new BusinessLogicException("Invalid or missing Authorization header");
    }

    final String refreshToken = authHeader.substring(7);
    final String userPhoneNumber = jwtService.extractUserPhoneNumberAndRole(refreshToken).split(":")[1];
    final RoleType roleType = RoleType.valueOf(jwtService.extractUserPhoneNumberAndRole(refreshToken).split(":")[0]);
    if (userPhoneNumber == null) {
      throw new BusinessLogicException("Invalid refresh token");
    }

    UserDetails user = findUserByPhoneNumber(userPhoneNumber, roleType);

    if (user == null) {
      throw new BusinessLogicException("User not found");
    }

    if (jwtService.isTokenValid(refreshToken, user)) {
      Map<String, Object> claims = new HashMap<>();
      claims.put("authorities", user.getAuthorities());
      String accessToken = jwtService.generateToken(claims, user);
      String newRefreshToken = jwtService.generateRefreshToken(claims, user);

      revokeAllUserTokens(user);
      saveUserToken(user, accessToken);

      return AuthenticationResponse.builder()
          .accessToken(accessToken)
          .refreshToken(newRefreshToken)
          .build();
    } else {
      throw new BusinessLogicException("Invalid refresh token");
    }
  }

  private UserDetails createUser(RegisterRequest request) {
    UserDetails user;
    switch (request.getRole()) {
      case COURIER:
        user = createCourier(request);
        break;
      case ADMIN:
        user = createAdmin(request);
        break;
      case USER:
        user = createCustomer(request);
        break;
      default:
        throw new BusinessLogicException("Invalid role type");
    }
    return user;
  }

  private UserDetails createCourier(RegisterRequest request) {
    CourierDTO courier = new CourierDTO();
    courier.setName(request.getName());
    courier.setPhoneNumber(request.getPhoneNumber());
    courier.setPassword(passwordEncoder.encode(request.getPassword()));
    courierService.insertCourier(courier);
    return courierService
        .findByPhoneNumber(request.getPhoneNumber())
        .orElseThrow(() -> new BusinessLogicException("Courier not found after creation"));
  }

  private UserDetails createAdmin(RegisterRequest request) {
    AdminDTO admin = new AdminDTO();
    admin.setName(request.getName());
    admin.setPassword(passwordEncoder.encode(request.getPassword()));
    admin.setPhoneNumber(request.getPhoneNumber());
    if (request.getEmail() != null && !request.getEmail().isBlank()) {
      admin.setEmail(request.getEmail());
    }
    adminService.insertAdmin(admin);
    return adminService
        .findByPhoneNumber(request.getPhoneNumber())
        .orElseThrow(() -> new BusinessLogicException("Admin not found after creation"));
  }

  private UserDetails createCustomer(RegisterRequest request) {
    CustomerDTO customer = new CustomerDTO();
    customer.setName(request.getName());
    customer.setPassword(passwordEncoder.encode(request.getPassword()));
    customer.setPhoneNumber(request.getPhoneNumber());
    customer.setActiveAddressId(null);
    if (request.getEmail() != null && !request.getEmail().isBlank()) {
      customer.setEmail(request.getEmail());
    }
    customerService.insertCustomer(customer);
    return customerService
        .findByPhoneNumber(request.getPhoneNumber())
        .orElseThrow(() -> new BusinessLogicException("Customer not found after creation"));
  }

  private UserDetails findUserByPhoneNumber(String phoneNumber, RoleType roleType) {
    UserDetails user = null;
    if (roleType == RoleType.ADMIN) {
      user = adminService.findByPhoneNumber(phoneNumber).orElse(null);
    } else if (roleType == RoleType.COURIER) {
      user = courierService.findByPhoneNumber(phoneNumber).orElse(null);
    } else if (roleType == RoleType.USER) {
      user = customerService.findByPhoneNumber(phoneNumber).orElse(null);
    }

    return user;
  }

  private void revokeAllUserTokens(UserDetails user) {
    List<Token> validUserTokens = new ArrayList<>();

    if (user instanceof AppUser) {
      validUserTokens = tokenRepository.findAllValidTokenByUser(getUserId(user));
    } else {
      validUserTokens = tokenRepository.findAllValidTokenByCourier(getUserId(user));
    }
    if (!validUserTokens.isEmpty()) {
      validUserTokens.forEach(
          token -> {
            token.setExpired(true);
            token.setRevoked(true);
          });
      tokenRepository.saveAll(validUserTokens);
    }
  }

  private void saveUserToken(UserDetails user, String jwtToken) {
    Long userId = user instanceof AppUser ? getUserId(user) : null;
    Long courierId = user instanceof AppUser ? null : getUserId(user);
    Token token = Token.builder()
        .userId(userId)
        .courierId(courierId)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private Long getUserId(UserDetails user) {
    if (user instanceof AppUser) {
      return ((AppUser) user).getId();
    }
    return courierService.findIdByPhoneNumber(user.getUsername())
        .orElseThrow(() -> new BusinessLogicException("Invalid user type"));
  }
}

