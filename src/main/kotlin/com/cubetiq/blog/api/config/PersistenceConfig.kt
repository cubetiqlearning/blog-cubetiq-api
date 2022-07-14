package com.cubetiq.blog.api.config

import com.cubetiq.blog.api.util.StaticBeanUtils
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
            val user = StaticBeanUtils.getCurrentUser()
            val userId = if (user != null) "user:${user.id}" else "anonymousUser"
            return Optional.ofNullable(userId)
        }
    }

    @Bean
    fun auditorAware(): AuditorAware<String> {
        return AuditorAwareImpl()
    }
}