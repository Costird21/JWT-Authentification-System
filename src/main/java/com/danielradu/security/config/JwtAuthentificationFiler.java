package com.danielradu.security.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor // It will create a constructor using any final field we declare
public class JwtAuthentificationFiler extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
       final String authHeader = request.getHeader("Authorisation");
       final String jwtToken;
       final String userEmail;

       if (authHeader == null || !authHeader.startsWith("Bearer ")) {
           filterChain.doFilter(request, response);
           return;
       }

       jwtToken = authHeader.substring(7);
       userEmail = jwtService.extractUsername(jwtToken);

       // 1. If we have our userEmail and user is not authenticated
       if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           // 2. Get the userDetails from the database
           UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
           // 3. Check if the user is valid or not
           if (jwtService.isTokenValid(jwtToken, userDetails)) {
               // 4. Create an object of type UsernamePasswordAuthentificationToken
               UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                       // 5. Pass the userDetails, credentials and authorities
                       userDetails,
                       null,
                       userDetails.getAuthorities());
               // 6. Enforce the authToken with the details of our request
               authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               // 7. Update the authToken to the SecurityContextHolder
               SecurityContextHolder.getContext().setAuthentication(authToken);
           }
       }
       filterChain.doFilter(request, response);
    }
}
