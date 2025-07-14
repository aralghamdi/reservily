package com.reservly.users.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class CommonUtil {

    public static String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
