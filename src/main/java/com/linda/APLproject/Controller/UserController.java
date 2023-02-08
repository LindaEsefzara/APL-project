package com.linda.APLproject.Controller;

import com.linda.APLproject.Configuration.AppPasswordConfig;
import com.linda.APLproject.Repository.UserRepository;
import com.linda.APLproject.Service.UserService;
import com.linda.APLproject.Model.User;
import com.linda.APLproject.authorities.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final AppPasswordConfig appPasswordConfig;
    private final UserService userService;



    @Autowired
    public UserController(UserRepository userRepository, AppPasswordConfig appPasswordConfig, UserService userService) {
        this.userRepository = userRepository;
        this.appPasswordConfig = appPasswordConfig;
        this.userService = userService;
    }
    @GetMapping(value="/login")
    public String login() {
        return "login.html";
    }

    @GetMapping(value="/logout")
    public String logout() {
        return "logout.html";
    }

    @GetMapping("/register")
    public String displayRegisterUser(User user) {    // THIS ARGUMENT MUST EXIST

        return "register.html";
    }

    @PostMapping("/register")
    public String registerUser(User user, BindingResult result, Model model) {

        if (result.hasErrors()) {

            return "register";
        }

        String role = String.valueOf(user.getAuthorities().iterator().next());

        switch (role) {
            case "Admin" ->  user.setAuthorities(UserRoles.ADMIN.getGrantedAuthorities());
            case "User" -> user.setAuthorities(UserRoles.USER.getGrantedAuthorities());
        }

        user.setPassword(appPasswordConfig.bCryptPasswordEncoder().encode(user.getPassword()));
        user.setEmail(user.getEmail());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        System.out.println(user);
        userRepository.save(user);

        return "header";
    }
}
