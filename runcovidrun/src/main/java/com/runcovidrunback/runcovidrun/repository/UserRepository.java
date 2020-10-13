package com.runcovidrunback.runcovidrun.repository;

import com.runcovidrunback.runcovidrun.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByName(String name);
    boolean existsByPass(String pass);
//    boolean passIsValid(String pass);

}
