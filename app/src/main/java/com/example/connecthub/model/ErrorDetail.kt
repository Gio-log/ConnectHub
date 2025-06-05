package com.example.connecthub.model

data class ErrorDetail(
    val type: String,
    val loc: List<String>,
    val msg: String,
    val input: String?,
    val ctx: Map<String, Any>?
)
