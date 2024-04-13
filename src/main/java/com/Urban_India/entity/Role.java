package com.Urban_India.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="role")
@SuperBuilder
public class Role extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private String name;

     @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
     @JoinTable(name = "users_roles",
             joinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"),
             inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id")
     )
    private Set<User> userSet;
}
