package com.keskin.users.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserContextHolder {
    private static final ThreadLocal<String> userEmail = new ThreadLocal<>();
    private static final ThreadLocal<List<String>> userRoles = new ThreadLocal<>();

    public static void setEmail(String email) { userEmail.set(email); }
    public static String getEmail() { return userEmail.get(); }

    public static void setRoles(String rolesHeader) {
        if (rolesHeader == null || rolesHeader.isBlank()) {
            userRoles.set(Collections.emptyList());
            return;
        }

        String cleanHeader = rolesHeader.replace("[", "").replace("]", "").trim();

        List<String> roles = Arrays.stream(cleanHeader.split(","))
                .map(String::trim)
                .filter(role -> !role.isEmpty())
                .collect(Collectors.toList());

        userRoles.set(roles);
    }

    public static List<String> getRoles() {
        return userRoles.get() != null ? userRoles.get() : Collections.emptyList();
    }

    public static boolean isAdmin() {
        return getRoles().stream().anyMatch(role -> role.equalsIgnoreCase("ADMIN"));
    }

    public static void clear() {
        userEmail.remove();
        userRoles.remove();
    }
}