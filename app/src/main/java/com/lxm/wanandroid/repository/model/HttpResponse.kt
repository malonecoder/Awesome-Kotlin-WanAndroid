package com.lxm.wanandroid.repository.model
/**
 * data : {"curPage":1,"datas":[{"apkLink":"","author":"红橙Darren","chapterId":245,"chapterName":"集合相关","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3326,"link":"https://www.jianshu.com/p/9edd74769f21","niceDate":"2018-08-27","origin":"","projectLink":"","publishTime":1535372956000,"superChapterId":245,"superChapterName":"Java深入","tags":[],"title":"数据结构算法 - 栈和队列","type":0,"userId":-1,"visible":1,"zan":0}],"offset":0,"over":false,"pageCount":80,"size":20,"total":1596}
 * errorCode : 0
 * errorMsg :
 */
data class HttpResponse<T>(
    var data: T? = null,
    var errorCode: Int = 0,
    var errorMsg: String? = null
)