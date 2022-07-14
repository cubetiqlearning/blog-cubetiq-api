package com.cubetiq.blog.api.web.controller

import com.cubetiq.blog.api.util.TextUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore

@ApiIgnore
@RestController
@RequestMapping("/test")
class TestController {
//    @GetMapping("/password")
//    fun generatePassword(): String {
//        return TextUtils.encryptedPassword("123456")
//    }
}