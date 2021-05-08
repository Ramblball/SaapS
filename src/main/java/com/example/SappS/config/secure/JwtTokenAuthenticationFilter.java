package com.example.SappS.config.secure;

import com.example.SappS.database.services.UserService;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    JwtConfig jwtConfig;
    JwtTokenProvider jwtTokenProvider;
    UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        String header = httpServletRequest.getHeader(jwtConfig.getHeader());

        if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String token = header.replace(jwtConfig.getPrefix(), "");
        if (jwtTokenProvider.validateToken(token)) {
            Claims claims = jwtTokenProvider.getClaimsFromJWT(token);
            String username = claims.getSubject();

            UsernamePasswordAuthenticationToken auth =
                    userService.findByName(username)
                            .map(user -> {
                                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                                authorities.add(new SimpleGrantedAuthority("USER"));
                                UsernamePasswordAuthenticationToken authenticationToken =
                                        new UsernamePasswordAuthenticationToken(user, user.getPassword(), authorities);
                                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                                return authenticationToken;
                            })
                            .orElse(null);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
