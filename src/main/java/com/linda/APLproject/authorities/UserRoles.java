package com.linda.APLproject.authorities;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.linda.APLproject.authorities.UserPermissions.*;

public enum UserRoles {

    USER(Set.of(USER_READ)),
    ADMIN(Set.of(ADMIN_READ, ADMIN_WRITE));

    private final Set<UserPermissions> permissionsList;

    UserRoles(Set<UserPermissions> permissions) {
        this.permissionsList = permissions;
    }

    public Set<UserPermissions> getPermissions() {
        return permissionsList;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {


        Set<SimpleGrantedAuthority> permissionsSet = getPermissions().stream().map(
                index -> new SimpleGrantedAuthority(index.getUserPermission())
        ).collect(Collectors.toSet());


        permissionsSet.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissionsSet;
    }
}
