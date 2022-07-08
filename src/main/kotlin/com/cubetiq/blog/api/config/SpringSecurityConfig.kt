package com.cubetiq.blog.api.config

import com.cubetiq.blog.api.constant.RestUriConstant
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true
)
class SpringSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
//        http.authorizeRequests {
//            it.antMatchers("${RestUriConstant.Backend}/**").authenticated()
//            it.antMatchers(HttpMethod.PUT, "${RestUriConstant.Backend.CATEGORY}/**").authenticated()
//            it.antMatchers(HttpMethod.GET, "${RestUriConstant.Frontend.POST}/**").permitAll()
//            it.antMatchers("${RestUriConstant.Frontend.POST}/**").authenticated()
//        }
        return http.build()
    }
}