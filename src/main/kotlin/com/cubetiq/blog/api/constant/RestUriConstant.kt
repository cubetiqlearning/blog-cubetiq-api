package com.cubetiq.blog.api.constant

object RestUriConstant {
    const val IND = "/api/v1"

    object Backend {
        const val INDEX = "$IND/backend"
        const val CATEGORY = "$INDEX/category"
    }

    object Frontend {
        const val INDEX = "$IND/frontend"
        const val POST = "$INDEX/post"
        const val CATEGORY = "$INDEX/category"
    }
}