package com.cubetiq.blog.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.Contact
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket

@Configuration
@EnableWebMvc
class SpringFoxConfig {
    companion object {
        private const val AUTHORIZATION_KEY: String = "Bearer"
        private const val AUTHORIZATION_HEADER: String = "Authorization"
    }

    private fun apiKey(): ApiKey {
        return ApiKey(
            AUTHORIZATION_KEY,
            AUTHORIZATION_HEADER,
            "header",
        )
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference(AUTHORIZATION_KEY, authorizationScopes))
    }

    private fun securityContext(): SecurityContext? {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build()
    }

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.cubetiq.blog.api.web"))
            .paths(PathSelectors.any())
            .build()
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))
            .apiInfo(
                ApiInfoBuilder()
                    .description("Welcome to CUBETIQ API")
                    .title("CUBETIQ API Service")
                    .version("0.0.1-SNAPSHOT")
                    .contact(
                        Contact(
                            "CUBETIQ Solution",
                            "https://cubetiqs.com",
                            "ops@cubetiqs.com",
                        )
                    )
                    .build()
            )
    }
}