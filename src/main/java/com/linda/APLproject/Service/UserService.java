package com.linda.APLproject.Service;

import com.linda.APLproject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;


    public void save(User user) {
        userRepository.save(user);
    }

    public void saveById(long user_Id) {
        User user = userRepository.findById(user_Id).get();
        userRepository.save(user);
    }


    public User findById(long user_id){
        User user = userRepository.findById(user_id).get();
        return user;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public List<User> userSearcher(String userName, String email) {
        if
        (userName != null && email != null) {
            return findAll();

        }else if
        (userName == null && email !=null) {
            return findAll();

        } else return new ArrayList<>();

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User findUserByEmail(String email) {
        return null;
    }
}
