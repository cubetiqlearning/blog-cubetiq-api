package com.cubetiq.blog.api

import com.cubetiq.blog.api.util.AppContextUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Lazy
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Lazy(false)
@Order(1)
class StaticContextInitializer @Autowired constructor(
    applicationContext: ApplicationContext,
) {
    init {
        AppContextUtils.setContext(applicationContext)
    }
}