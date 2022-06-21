package com.cubetiq.blog.api.web.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView
import springfox.documentation.annotations.ApiIgnore

@ApiIgnore
@RestController
class ApiDoc {
    @GetMapping(value = ["api-doc", "api-docs"])
    fun redirect(): RedirectView {
        return RedirectView("/swagger-ui/index.html")
    }
}