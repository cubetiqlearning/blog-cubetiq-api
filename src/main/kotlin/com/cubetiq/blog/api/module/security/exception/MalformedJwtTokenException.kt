package com.cubetiq.blog.api.module.security.exception

import com.cubetiq.blog.api.exception.BaseException

class MalformedJwtTokenException(
    message: String? = "",
) : BaseException(message)