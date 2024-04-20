package com.Urban_India.config;

import com.Urban_India.entity.User;
import com.Urban_India.exception.ResourceNotFoundException;
import com.Urban_India.repository.UserRepository;
import com.Urban_India.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {

    @Autowired
    private UserRepository userRepository;

    private static final Long SYSTEM_ID = 0L;

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        if(Objects.isNull(authentication) || !authentication.isAuthenticated()){
            return Optional.empty();
        }
//
        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
//        User user =userRepository.findByUsernameOrEmail(username, username).orElse(null);
//        // if user is null means user is registered yet
//        if(Objects.isNull(user)){
//            return Optional.of(SYSTEM_ID);
//        }
        return Optional.of(currentUser.getId());
    }
}
