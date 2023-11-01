package com.example.demo.init;

import com.example.demo.MyUser;
import com.example.demo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitApplicationRunner implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // add default admin user and set its password if missing
        MyUser admin = userRepository.findFirstByUsername("blue");
        if (admin == null) {
            admin = new MyUser();
            admin.setUsername("blue");
            admin.setPassword(passwordEncoder.encode("shad"));
            admin.setRole("USER");
            userRepository.save(admin);
        }
    }
}
