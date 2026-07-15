package com.katllv.scorekeeper_be.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter // generates get methods for all fields
@Setter // generates set methods for all fields
@NoArgsConstructor // generates a no-argument constructor, eg public User() {}
@Entity // marks this class as a JPA entity, meaning it will be mapped to a database table
@Table(name = "users") // specifies the name of the database table to which this entity will be mapped
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // specifies that the id field is the primary key and its value will be generated automatically using UUID strategy
    private UUID id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "display_name", nullable = true)
    private String displayName;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Instant updatedAt;
}