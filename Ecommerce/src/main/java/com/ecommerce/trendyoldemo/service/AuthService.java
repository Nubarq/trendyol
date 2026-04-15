package com.ecommerce.trendyoldemo.service;



import com.ecommerce.trendyoldemo.dto.request.AuthRequest;
import com.ecommerce.trendyoldemo.dto.response.AuthResponse;
import com.ecommerce.trendyoldemo.entity.UserEntity;
import com.ecommerce.trendyoldemo.exception.CustomException;
import com.ecommerce.trendyoldemo.repository.UserRepository;
import com.ecommerce.trendyoldemo.utility.JwtUtil;
import com.ecommerce.trendyoldemo.utility.RefreshTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final RefreshTokenUtil refreshTokenUtil;
    private final UserRepository userRepository;


    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) throws Exception {
        String email = authRequest.getEmail();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new CustomException("İstifadəçi e-poçtu və ya şifrə yanlışdır", "Invalid phone or password",
                    "Authenticated", 403, null);
        }

//        Optional<UserEntity> findedEmail = userRepository.findByEmail(email);
//        if (findedEmail.isEmpty()) {
//            throw new CustomException(
//                    "İstifadəçi tapılmadı",
//                    "User not found with email: " + email,
//                    "Not Found",
//                    404,
//                    null
//            );
//        }
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException("not found "+email+" email", "not found your email to login",
                "Bad Request", 400, null));

//        UserEntity user = findedEmail.get();

        if (!user.isVerified()) {
            throw new CustomException("Daxil olmaq üçün e-poçtunuzu təsdiqləyin", "Verify your email to login",
                    "Bad Request", 400, null);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        final String jwt = jwtUtil.generateToken(userDetails);
        final String refreshToken = refreshTokenUtil.generateRefreshToken(userDetails); // ✅ Added

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // Updated AuthResponse to include refreshToken
        AuthResponse response = new AuthResponse(email, jwt, refreshToken, roles);

        return ResponseEntity.ok(response);
    }
}
