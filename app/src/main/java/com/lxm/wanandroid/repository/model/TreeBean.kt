package com.lxm.wanandroid.repository.model

import java.io.Serializable

data class TreeBean(
    val children: List<TreeBean>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
):Serializable

