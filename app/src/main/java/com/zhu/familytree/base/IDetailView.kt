package com.zhu.familytree.base

import com.zhu.familytree.model.MemberDetailBean

/**
 * @description mvp的view
 *
 * @author QQ657036139
 * @since 2019/11/9
 */
interface IDetailView : IView {
    fun showDetail(detail: MemberDetailBean)
}