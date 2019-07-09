package com.lxm.wanandroid.repository.model

data class WelfareResponse(
    val error: Boolean,
    val results: List<Welfare>
)

data class Welfare(
    val _id: String,
    val createdAt: String,
    val desc: String,
    val publishedAt: String,
    val source: String,
    val type: String,
    val url: String,
    val used: Boolean,
    val who: String
)