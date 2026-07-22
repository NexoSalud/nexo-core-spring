package com.nexo.core.config;

import com.nexo.core.context.UserContext;
import com.nexo.core.exception.GlobalExceptionHandler;
import com.nexo.core.filter.UserContextFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Auto-configuración principal de la librería nexo-core-spring.
 * Registra automáticamente el contexto de usuario, filtro HTTP de interceptación,
 * manejador global de errores y auditoría JPA al importar el JAR.
 */
@AutoConfiguration
@Import({NexoJpaAuditConfiguration.class})
public class NexoCoreAutoConfiguration {

    @Bean
    @RequestScope
    @ConditionalOnMissingBean
    public UserContext userContext() {
        return new UserContext();
    }

    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnMissingBean
    public UserContextFilter userContextFilter(UserContext userContext) {
        return new UserContextFilter(userContext);
    }

    @Bean
    @ConditionalOnWebApplication
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
