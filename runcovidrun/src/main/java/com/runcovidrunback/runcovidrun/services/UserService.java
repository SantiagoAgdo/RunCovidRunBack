package com.runcovidrunback.runcovidrun.services;

import com.runcovidrunback.runcovidrun.entity.User;
import com.runcovidrunback.runcovidrun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> listaUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getOne(int id) {
        return userRepository.findById(id);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }

    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }

    public boolean existsByName(String name) {
        return userRepository.existsByName(name);
    }

    public boolean passIsValid(String pass){
        return userRepository.existsByPass(pass);
    }


}
