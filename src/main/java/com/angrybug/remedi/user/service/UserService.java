package com.angrybug.remedi.user.service;


import com.angrybug.remedi.user.model.User;
import com.angrybug.remedi.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    //회원가입
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    //인증
    public String auth(User user) {
        Optional<User> username = userRepository.findByUsername(user.getUsername());

        if(username.isPresent()) {
            User u = username.get();
            if(passwordEncoder.matches(
                    user.getPassword(),
                    u.getPassword()
            )) {
                return "Ok";
            }
            else {
                throw new BadCredentialsException("Bad credentials");
            }
        }
        else {
            throw new BadCredentialsException("Bad credentials");
        }

    }

}
