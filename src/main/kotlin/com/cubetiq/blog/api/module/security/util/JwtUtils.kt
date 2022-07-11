package com.cubetiq.blog.api.module.security.util

import com.cubetiq.blog.api.module.security.exception.*
import io.jsonwebtoken.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*
import javax.servlet.http.HttpServletRequest

object JwtUtils {
    private const val AUTHORIZATION_HEADER: String = "Authorization"
    private const val AUTHORIZATION_TYPE: String = "Bearer "

    private var secretKey: String = "blog123"
    private var tokenExpiredInMillis: Long = 604800000 // 1 week
    private var passwordStrength: Int = 10
    private var passwordEncoder: PasswordEncoder? = null
    private var userDetailsService: UserDetailsService? = null

    // setters
    fun setUserDetailsService(_userDetailsService: UserDetailsService) = apply {
        this.userDetailsService = _userDetailsService
    }

    fun setPasswordStrength(_passwordStrength: Int) = apply {
        this.passwordStrength = _passwordStrength
    }

    fun setSecretKey(_secretKey: String) = apply {
        this.secretKey = _secretKey
    }

    fun setTokenExpiredInMillis(_tokenExpiredInMillis: Long) = apply {
        this.tokenExpiredInMillis = _tokenExpiredInMillis
    }

    // getters
    fun getUserDetailsService(): UserDetailsService {
        if (this.userDetailsService == null)
            throw JwtNotImplementException()

        return this.userDetailsService!!
    }

    fun getPasswordEncoder(): PasswordEncoder {
        if (this.passwordEncoder == null)
            this.passwordEncoder = BCryptPasswordEncoder(this.passwordStrength)

        return this.passwordEncoder!!
    }

    private fun getSecretKey(): String = Base64.getEncoder().encodeToString(secretKey.toByteArray())

    private fun getTokenExpiredInMillis(): Long = this.tokenExpiredInMillis

    fun extractToken(request: HttpServletRequest): String? {
        val headerToken = request.getHeader(AUTHORIZATION_HEADER)?.toString() ?: ""
        val isBearerToken = headerToken.trim().lowercase().startsWith(AUTHORIZATION_TYPE.lowercase())
        if (!isBearerToken)
            return null

        val token = headerToken.substring(AUTHORIZATION_TYPE.length)
        val isValidJwtThreePart = token.split(".").size == 3
        if (!isValidJwtThreePart)
            return null

        return token
    }

    private fun validateTokenExpired(claims: Claims): Boolean {
        if (claims.expiration.after(Date()))
            return true

        return false
    }

    fun resolveUserFromToken(token: String?): UsernamePasswordAuthenticationToken? {
        val claims = this.decryptToken(token) ?: return null
        val isTokenExpired = this.validateTokenExpired(claims)

        if (!isTokenExpired)
            return null

        val username = claims.subject
        val user = this.getUserDetailsService().loadUserByUsername(username) ?: return null
        if (!user.isEnabled)
            throw UserNotEnabledException("User is disabled!")

        return resolveAuthFromUser(user)
    }

    private fun resolveAuthFromUser(user: UserDetails): UsernamePasswordAuthenticationToken {
        val auth = UsernamePasswordAuthenticationToken(user.username, user.password, user.authorities)
        auth.details = user
        return auth
    }

    fun encryptToken(user: UserDetails): String {
        val currentDateMillisecond = Date().time + this.getTokenExpiredInMillis()
        val expireDate = Date(currentDateMillisecond)

        return Jwts.builder()
            .setSubject(user.username)
            .setIssuedAt(Date())
            .setExpiration(expireDate)
            .signWith(SignatureAlgorithm.HS256, this.getSecretKey())
            .compact()
    }

    private fun decryptToken(token: String?): Claims? {
        token ?: return null
        val secretKey = this.getSecretKey()

        return try {
            Jwts.parser()
                .setSigningKey(secretKey)
                .parse(token)
                .body as? Claims
        } catch (ex: SignatureException) {
            throw SignatureTokenException("Invalid JWT Signature")
        } catch (ex: MalformedJwtException) {
            throw MalformedJwtTokenException("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            throw ExpiredJwtTokenException("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            throw UnsupportedJwtTokenException("Unsupported JWT exception")
        } catch (ex: IllegalArgumentException) {
            throw EmptyJwtClaimsException("Jwt claims string is empty")
        }
    }
}