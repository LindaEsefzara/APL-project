package com.linda.APLproject;

import com.linda.APLproject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserFinder {
    @Autowired
    UserService userModelService;



    public long getCurrentUserId() {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = details.getUsername();
        long userId = -1;
        for (User userModel : userModelService.findAll());
        {
            if (userModelService.toString().equals(username)){
                userId = getCurrentUserId();

            }
        }

        return userId;
    }

    public boolean getCurrentUser(){
        boolean currentUser = userModelService.findAll().equals(getCurrentUserId());
        return currentUser;
    }
}
