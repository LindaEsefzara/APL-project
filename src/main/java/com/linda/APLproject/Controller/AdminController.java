package com.linda.APLproject.Controller;

import com.linda.APLproject.CurrentUserFinder;
import com.linda.APLproject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    CurrentUserFinder currentUserFinder;

    @GetMapping
    public String adminHome(Model model){
        boolean currentUser = currentUserFinder.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "header";
    }

    @GetMapping(value = "/manageAuthorities")
    public String manageAuthorities(@RequestParam(required = false) String firstName,
                                    @RequestParam(required = false) String lastName,
                                    Model model){
        List<User> users = userService.userSearcher(firstName,lastName);

        model.addAttribute("users", users);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        return "header";
    }

    @GetMapping(value = "/manageAccount")
    public String manageAccount(@RequestParam Long userId, Model model){
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "header";
    }

    @PutMapping(value = "/confirmaccountsetting")
    public String confirmAccountChanges(@RequestParam String role,
                                        @RequestParam Long userId){
        User user = userService.findById(userId);
        user.setRole(role);
        userService.save(user);
        return "header";

    }

    @GetMapping(value = "/accountsettingssaved")
    public String accountSettingsSaved(){
        return "Admin/account-settingssaved.html";
    }
}
