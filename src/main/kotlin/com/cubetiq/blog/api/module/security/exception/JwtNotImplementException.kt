package com.cubetiq.blog.api.module.security.exception

import com.cubetiq.blog.api.exception.BaseException


class JwtNotImplementException(
    message: String? = "User details service not implement yet!"
) : BaseException(message)