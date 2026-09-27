package com.example.employee_management.security.jwt;

import com.example.employee_management.entity.Account;
import com.example.employee_management.entity.InvalidatedToken;
import com.example.employee_management.repository.InvalidatedTokenRepository;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class JwtUtil {

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @Value("${jwt.expiration}")
    private Long EXPIRATION_TIME;

    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    public String generateToken(Account account, String roleName) {
        try {
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
//            String roleName = account.getRole().getName();
            String username = account.getUsername();
            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(account.getPhoneNumber())
//                    .issuer("devteria.com")
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plus(EXPIRATION_TIME, ChronoUnit.HOURS)))
                    .claim("username", username)// Có thể thêm claim tùy ý
                    .claim("roleName", roleName)
                    .jwtID(UUID.randomUUID().toString())
                    .build();

            SignedJWT signedJWT = new SignedJWT(header, jwtClaimsSet);
            signedJWT.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return signedJWT.serialize();
        } catch (Exception e) {
            log.error("Error generating token", e);
            throw new RuntimeException("Token generation failed", e);
        }
    }

    // kiểm tra xem Jwt đã bị vô hiệu hoá hay chưa - kiểm tra token có nằm trong blacklist hay không.
    public boolean invalidedToken(String id) {
        return invalidatedTokenRepository.existsById(id);
    }

    // đưa token vào blacklist khi logout - vô hiệu hoá cái token
    public void inActiveToken(SignedJWT signToken) throws ParseException {
        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expirationTime = signToken.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jit).expiryTime(expirationTime).build();

        invalidatedTokenRepository.save(invalidatedToken);
    }
}
