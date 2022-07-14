package com.cubetiq.blog.api.exception

class ServiceLoaderException(
   message: String? = "application context load failed!"
) : BaseException(message)