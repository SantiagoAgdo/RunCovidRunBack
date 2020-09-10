package com.runcovidrunback.runcovidrun.repository;

import com.runcovidrunback.runcovidrun.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByName(String name);
}
