package org.silentsoft.todoist.core.config.security.authentication;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());

        // TODO encryption & check through database
        if ("admin".equals(username) && "admin".equals(password)) {
            // TODO role should retrieve from database
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("admin", null, roles);
            return token;
        } else if ("user".equals(username) && "user".equals(password)) {
            // TODO role should retrieve from database
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("user", null, roles);
            return token;
        }

        throw new BadCredentialsException("Nah,");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}