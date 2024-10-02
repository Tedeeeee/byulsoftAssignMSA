package com.example.authservice.security.provider;

import com.example.authservice.security.service.CustomPasswordEncoder;
import com.example.authservice.security.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailService customUserDetailService;
    private final CustomPasswordEncoder customPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String memberEmail = authentication.getName();
        String memberPassword = authentication.getCredentials().toString();

        UserDetails user = customUserDetailService.loadUserByUsername(memberEmail);

        if (!memberPassword.equals(user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 틀렸습니다");
        }

//        if (!customPasswordEncoder.match(memberPassword, user.getPassword())) {
//            throw new BadCredentialsException("Bad credentials");
//        }

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
