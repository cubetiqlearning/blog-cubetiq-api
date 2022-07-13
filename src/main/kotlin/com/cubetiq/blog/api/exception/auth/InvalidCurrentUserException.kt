package com.cubetiq.blog.api.exception.auth

import com.cubetiq.blog.api.exception.BaseException

class InvalidCurrentUserException(
    message: String? = "Bad credentials!"
) : BaseException(message)
