package com.cubetiq.blog.api.module.security

import com.cubetiq.blog.api.module.security.AuthenticationExceptionEntryPoint
import com.cubetiq.blog.api.module.security.util.JwtUtils
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenFilter: OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = JwtUtils.extractToken(request)
            if (!token.isNullOrEmpty()) {
                val auth = JwtUtils.resolveUserFromToken(token)
                if (auth != null)
                    SecurityContextHolder.getContext().authentication = auth
            }

            filterChain.doFilter(request, response)
        } catch (e: AuthenticationServiceException) {
            e.printStackTrace()
            val ex = AuthenticationServiceException(e.message, e.cause)
            AuthenticationExceptionEntryPoint().commence(request, response, ex)
        }
    }
}