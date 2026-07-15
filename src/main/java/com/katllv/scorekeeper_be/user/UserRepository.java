package com.katllv.scorekeeper_be.user;

import org.springframework.data.jpa.repository.JpaRepository; // provides CRUD operations and more for the User entity

import java.util.Optional;
import java.util.UUID;

// extends JpaRepository<User, UUID> to inherit basic CRUD operations for the User entity, with UUID as the type of the primary key
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
