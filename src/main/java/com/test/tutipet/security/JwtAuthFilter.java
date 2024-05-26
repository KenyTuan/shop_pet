package com.test.tutipet.security;

import com.test.tutipet.constants.ApiEndpoints;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {

        if (request.getServletPath().contains(ApiEndpoints.PREFIX + ApiEndpoints.AUTH_V1)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        String emailUser = null;
        String jwt = null;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        emailUser = jwtUtil.extractUsername(jwt);

        if (emailUser != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(emailUser);

            if (jwtUtil.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

//
//    private boolean isByPassToken(@NotNull HttpServletRequest request) {
//        final List<Pair<String, String>> byPassToken = Arrays.asList(
//                Pair.of(ApiEndpoints.PREFIX + ApiEndpoints.PRO_TYPE_V1,"GET"),
//                Pair.of(ApiEndpoints.PREFIX + ApiEndpoints.PRODUCT_V1, "GET"),
//                Pair.of(ApiEndpoints.PREFIX + ApiEndpoints.PRODUCT_V1 + "/{id}", "GET"),
//                Pair.of(ApiEndpoints.PREFIX + ApiEndpoints.AUTH_V1 + "/register", "POST"),
//                Pair.of(ApiEndpoints.PREFIX + ApiEndpoints.AUTH_V1 + "/authenticate", "POST")
//        );
//
//
//    }
}
