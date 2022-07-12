package com.cubetiq.blog.api.infrastructure.extension

import com.cubetiq.blog.api.util.JsonUtils

fun Any?.toJsonNode() = JsonUtils.toJsonNode(this)