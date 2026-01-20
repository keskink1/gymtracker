package com.keskin.workouts.config;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;

public class UserContextHolder {
    private static final ThreadLocal<String> userEmail = new ThreadLocal<>();
    private static final ThreadLocal<List<String>> userRoles = new ThreadLocal<>();

    public static void setEmail(String email) { userEmail.set(email); }
    public static String getEmail() { return userEmail.get(); }

    public static void setRoles(String rolesHeader) {
        if (rolesHeader == null || rolesHeader.isBlank()) {
            userRoles.set(Collections.emptyList());
        } else {
            userRoles.set(Arrays.asList(rolesHeader.split(",")));
        }
    }

    public static List<String> getRoles() {
        return userRoles.get() != null ? userRoles.get() : Collections.emptyList();
    }

    public static boolean isAdmin() {
        return getRoles().contains("ADMIN");
    }

    public static void clear() {
        userEmail.remove();
        userRoles.remove();
    }
}