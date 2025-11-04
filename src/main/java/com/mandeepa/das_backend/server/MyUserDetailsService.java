package com.mandeepa.das_backend.server;

import com.mandeepa.das_backend.entity.UserEntity;
import com.mandeepa.das_backend.exception.UnAuthorizedException;
import com.mandeepa.das_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        UserEntity user = userRepository.findByUsername(username);
//        if(user == null){
//            System.out.println("User not found");
//            throw new UnAuthorizedException("User not found");
//        }
//        return new UserPrincipal(user);
//    }
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getUserType().name())
                .build();
    }
}
