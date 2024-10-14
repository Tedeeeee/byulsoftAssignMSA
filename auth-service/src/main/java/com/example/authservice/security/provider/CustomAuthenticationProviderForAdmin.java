package com.example.authservice.security.provider;

import com.example.authservice.security.service.CustomAdminDetailService;
import com.example.authservice.security.service.CustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class CustomAuthenticationProviderForAdmin implements AuthenticationProvider {

    private final CustomAdminDetailService customAdminDetailService;
    private final CustomPasswordEncoder customPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String memberEmail = authentication.getName();
        String memberPassword = authentication.getCredentials().toString();

        UserDetails user = customAdminDetailService.loadUserByUsername(memberEmail);

        if (!customPasswordEncoder.match(memberPassword, user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}