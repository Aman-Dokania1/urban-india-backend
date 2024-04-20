package com.Urban_India.security;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CurrentUser  extends User implements UserDetails{

    private Long id;
    private String name;
    private String email;

    public CurrentUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                       Long id, String name, String email){
        super(username, password, authorities);
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
