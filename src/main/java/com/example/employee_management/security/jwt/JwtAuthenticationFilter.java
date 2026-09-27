package com.example.employee_management.security.jwt;

import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        if (token != null) {
            try {
                SignedJWT signedJWT = SignedJWT.parse(token);
                String id = signedJWT.getJWTClaimsSet().getJWTID();

                // Kiểm tra token có bị vô hiệu hóa không
                if (jwtUtil.invalidedToken(id)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token has been blacklisted");
                    return;
                }

                // Giải mã username/email từ token
                String username = signedJWT.getJWTClaimsSet().getSubject();

                //  Lấy role từ token
                String roleName = signedJWT.getJWTClaimsSet().getStringClaim("roleName");

                // Tạo authority từ role: "ADMIN" → "ROLE_ADMIN"
                List<SimpleGrantedAuthority> authorities = List.of(
                        new SimpleGrantedAuthority("ROLE_" + roleName)
                );

                // Tạo Authentication object với authorities
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                username, null, authorities
                        );
//
//                // Tạo Authentication
//                UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(
//                                username, null, List.of() // có thể truyền roles nếu có
//                        );

                // Set vào SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            } catch (Exception e) {
//                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid token format");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
