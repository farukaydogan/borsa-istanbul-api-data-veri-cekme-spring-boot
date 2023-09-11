package com.bist.tracking.app.service;

import com.bist.tracking.app.model.User;
import com.bist.tracking.app.repository.StockRepository;
import com.bist.tracking.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public Optional<User> getUserById(Integer userId){
        return userRepository.findById(userId);
    }
//
//    public User getUserById(Integer userId) {
//        User defaultUser = new User(); // varsayılan bir User nesnesi oluşturun.
//        return userRepository.findById(userId).orElse(defaultUser);
//    }
    public List<User> findAll(){
        return userRepository.findAll();
    }

}
