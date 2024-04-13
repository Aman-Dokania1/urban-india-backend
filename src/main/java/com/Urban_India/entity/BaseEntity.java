package com.Urban_India.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Base entity to provide id and is_Active field.
 * Name: BaseEntity
 * @author : Aman Dokania
 * @Date :13/04/24
 * @Time :12:37 pm
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@SuperBuilder
public class BaseEntity extends Auditable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // will be set when persisting

    @Column(name = "is_active")
    @Builder.Default
    private boolean isActive = true;
}
