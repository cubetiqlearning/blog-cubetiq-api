package com.cubetiq.blog.api.config

import com.cubetiq.blog.api.module.security.AuthenticationExceptionEntryPoint
import com.cubetiq.blog.api.module.security.JwtSecurityConfigurer
import com.cubetiq.blog.api.service.UserAuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true
)
class SpringSecurityConfig @Autowired constructor(
    private val userAuthService: UserAuthService,
) {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        // basic http configurer to use with token service
        http
            .httpBasic().disable()
            .cors().and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        // http error handling when auth error or occurs in http exception
        http
            .exceptionHandling()
            .authenticationEntryPoint(AuthenticationExceptionEntryPoint())

        // add jwt security filter and configurer to handling the token service
        http
            .apply(JwtSecurityConfigurer(userAuthService))

        // authorization request filters
        http
            .authorizeRequests {
                it.anyRequest().permitAll()
            }

        return http.build()
    }

    @Bean
    fun getPasswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(10)
    }
}