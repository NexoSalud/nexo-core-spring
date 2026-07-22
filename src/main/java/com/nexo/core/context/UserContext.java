package com.nexo.core.context;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Contexto de usuario scoped por petición HTTP (@RequestScope).
 * Contiene el ID del usuario autenticado y sus roles inyectados desde el API Gateway.
 */
@Component
@RequestScope
public class UserContext {

    private String userId;
    private List<String> roles = new ArrayList<>();

    public UserContext() {
    }

    public UserContext(String userId, List<String> roles) {
        this.userId = userId;
        this.setRoles(roles);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getRoles() {
        return roles != null ? Collections.unmodifiableList(roles) : Collections.emptyList();
    }

    public void setRoles(List<String> roles) {
        this.roles = roles != null ? new ArrayList<>(roles) : new ArrayList<>();
    }

    public boolean hasRole(String role) {
        return this.roles != null && this.roles.contains(role);
    }

    public void clear() {
        this.userId = null;
        if (this.roles != null) {
            this.roles.clear();
        }
    }
}
