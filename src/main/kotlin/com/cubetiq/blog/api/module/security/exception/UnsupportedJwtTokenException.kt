package com.cubetiq.blog.api.module.security.exception

import com.cubetiq.blog.api.exception.BaseException

class UnsupportedJwtTokenException(
    message: String? = "",
) : BaseException(message)