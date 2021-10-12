package org.silentsoft.todoist.util;

import org.silentsoft.todoist.core.userdetails.UserDetails;
import org.silentsoft.todoist.entity.UserEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {

    public static long getUserId() {
        return getUserEntity().getId();
    }

    public static UserEntity getUserEntity() {
        return getUserEntity(true);
    }

    public static UserEntity getUserEntity(boolean shouldRemovePassword) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserEntity userEntity = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUserEntity();
        if (shouldRemovePassword) {
            userEntity.setPassword(null);
        }
        return userEntity;
    }

    public static UserDetails getUserDetails() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return (UserDetails) securityContext.getAuthentication().getPrincipal();
    }

}
