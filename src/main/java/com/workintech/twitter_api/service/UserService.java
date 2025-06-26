package com.workintech.twitter_api.service;


import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    //user kaydetme
    public User save(User user) {
        return userRepository.save(user);
    }

    //tum userlari getirme
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    //id ye gore user bulma
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    //id ye gore user silme
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
