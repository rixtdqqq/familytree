package com.zhu.familytree.base

/**
 * @description 数据回调
 *
 * @author QQ657036139
 * @since 2019/11/9
 */
interface DataCallback {
    fun onSuccess(o: Any)
    fun onError(message: String)
}