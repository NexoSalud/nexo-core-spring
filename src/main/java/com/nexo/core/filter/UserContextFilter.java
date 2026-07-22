package com.nexo.core.filter;

import com.nexo.core.context.UserContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filtro HTTP OncePerRequestFilter que extrae los headers HTTP X-User-Id y X-User-Roles
 * (inyectados por el API Gateway KrakenD) y los asigna al UserContext de la petición.
 */
@Component
public class UserContextFilter extends OncePerRequestFilter {

    public static final String HEADER_USER_ID = "X-User-Id";
    public static final String HEADER_USER_ROLES = "X-User-Roles";

    private final UserContext userContext;

    public UserContextFilter(UserContext userContext) {
        this.userContext = userContext;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String userId = request.getHeader(HEADER_USER_ID);
        String rolesHeader = request.getHeader(HEADER_USER_ROLES);

        if (StringUtils.hasText(userId)) {
            userContext.setUserId(userId.trim());
        }

        if (StringUtils.hasText(rolesHeader)) {
            List<String> roles = Arrays.stream(rolesHeader.split(","))
                    .map(String::trim)
                    .filter(role -> !role.isEmpty())
                    .collect(Collectors.toList());
            userContext.setRoles(roles);
        } else {
            userContext.setRoles(Collections.emptyList());
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            // El ciclo de vida de @RequestScope limpia automáticamente la instancia al finalizar la petición HTTP.
        }
    }
}
