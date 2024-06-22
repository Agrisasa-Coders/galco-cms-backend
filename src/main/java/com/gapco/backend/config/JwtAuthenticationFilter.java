package com.gapco.backend.config;

import com.gapco.backend.exception.InternalServerErrorException;
import com.gapco.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException
    {
        final String authorizationHeader = request.getHeader(AUTHORIZATION);
        final String jwt;
        final String userEmail;

        if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer ")){

            jwt = authorizationHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);

            if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){

                try{
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                    if(jwtService.isTokenValid(jwt,userDetails)){

                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails,null, userDetails.getAuthorities()
                        );

                        authenticationToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );

                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    } else {
                        throw new InternalServerErrorException("Token is invalid or expired");
                    }
                } catch(Exception e){
                    throw new InternalServerErrorException(e.getMessage());
                }

            }

            filterChain.doFilter(request,response);

        }else{

            filterChain.doFilter(request,response);
        }
    }
}
