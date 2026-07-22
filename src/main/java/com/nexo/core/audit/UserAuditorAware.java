package com.nexo.core.audit;

import com.nexo.core.context.UserContext;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementación de AuditorAware<String> que obtiene el ID de usuario activo
 * directamente desde el UserContext inyectado por la petición HTTP.
 */
@Component("userAuditorAware")
public class UserAuditorAware implements AuditorAware<String> {

    private static final String DEFAULT_SYSTEM_USER = "SYSTEM";

    private final UserContext userContext;

    public UserAuditorAware(UserContext userContext) {
        this.userContext = userContext;
    }

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        try {
            String userId = userContext.getUserId();
            if (userId != null && !userId.isBlank()) {
                return Optional.of(userId);
            }
        } catch (Exception e) {
            // Manejo defensivo en caso de ser invocado fuera del contexto de una petición HTTP
            return Optional.of(DEFAULT_SYSTEM_USER);
        }
        return Optional.of(DEFAULT_SYSTEM_USER);
    }
}
