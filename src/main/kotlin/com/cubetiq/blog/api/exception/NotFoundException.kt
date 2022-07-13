package com.cubetiq.blog.api.exception

class NotFoundException(
   message: String? = "Not found!"
) : BaseException(message)