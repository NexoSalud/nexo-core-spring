package com.nexo.core.config;

import com.nexo.core.audit.UserAuditorAware;
import com.nexo.core.context.UserContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuración de auditoría JPA activada mediante @EnableJpaAuditing.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "userAuditorAware")
public class NexoJpaAuditConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "userAuditorAware")
    public AuditorAware<String> userAuditorAware(UserContext userContext) {
        return new UserAuditorAware(userContext);
    }
}
