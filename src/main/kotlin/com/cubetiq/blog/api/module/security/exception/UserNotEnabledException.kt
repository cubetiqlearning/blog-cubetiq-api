package com.cubetiq.blog.api.module.security.exception

import com.cubetiq.blog.api.exception.BaseException

class UserNotEnabledException(
    message: String? = "",
) : BaseException(message)