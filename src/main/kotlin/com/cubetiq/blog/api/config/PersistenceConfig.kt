package com.cubetiq.blog.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@Configuration
@EnableJpaAuditing
class PersistenceConfig {
    class AuditorAwareImpl : AuditorAware<String> {
        override fun getCurrentAuditor(): Optional<String> {
            return Optional.ofNullable("brahim")
        }
    }

    @Bean
    fun auditorAware(): AuditorAware<String> {
        return AuditorAwareImpl()
    }
}