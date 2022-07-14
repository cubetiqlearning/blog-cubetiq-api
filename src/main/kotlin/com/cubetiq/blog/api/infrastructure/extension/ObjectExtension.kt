package com.cubetiq.blog.api.infrastructure.extension

import com.cubetiq.blog.api.util.JsonUtils
import com.fasterxml.jackson.core.type.TypeReference

fun Any?.toJson() = JsonUtils.parseObjectToString(this)
fun Any?.toJsonNode() = JsonUtils.toJsonNode(this)
fun <T> String?.fromJson(typeRef: TypeReference<T>) = JsonUtils.fromJson(this, typeRef)