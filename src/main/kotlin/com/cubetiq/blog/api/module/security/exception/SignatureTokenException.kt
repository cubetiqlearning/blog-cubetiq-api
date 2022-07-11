package com.cubetiq.blog.api.module.security.exception

import com.cubetiq.blog.api.exception.BaseException

class SignatureTokenException(
    message: String? = "",
) : BaseException(message)