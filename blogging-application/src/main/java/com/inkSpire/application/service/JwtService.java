package com.inkSpire.application.service;


import com.inkSpire.application.dto.authentication.AuthenticationRequest;
import com.inkSpire.application.dto.authentication.AuthenticationResponse;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface JwtService {
    String SECRET = "c0bf98d4541670e7d45bf512ca614f939d4617ae902848fb92b6858b6c8e3f4e";

    int VALIDITY_TIME = 3600 * 5;

    String extractUsername(String token);

    Date extractExpiration(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    Boolean validateToken(String token, UserDetails userDetails);

    String generateToken(String userName);

    AuthenticationResponse generateAuthenticationResponse(AuthenticationRequest request);
}