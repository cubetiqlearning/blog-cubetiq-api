package com.cubetiq.blog.api.service

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
interface UserAuthService : UserDetailsService