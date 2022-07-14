package com.cubetiq.blog.api.util

import com.cubetiq.blog.api.service.UserAuthService
import org.springframework.context.ApplicationContext

object StaticBeanUtils {
    private val context: ApplicationContext
        get() = AppContextUtils.getContext()

    fun getCurrentUser() = context.getBean(UserAuthService::class.java).getCurrentUserOrNull()
}