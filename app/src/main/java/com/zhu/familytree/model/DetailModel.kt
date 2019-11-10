package com.zhu.familytree.model

import com.zhu.familytree.base.AppDatabase
import com.zhu.familytree.base.BaseModel
import com.zhu.familytree.base.DataCallback

/**
 * @description 详情model
 *
 * @author QQ657036139
 * @since 2019/11/9
 */

class DetailModel : BaseModel() {

    fun getMemberDetail(memberId: Int, callback: DataCallback) {
        AppDatabase.getInstance().familyDao()
            .queryMemberByMemberId(memberId).subscribeDbResult({
                callback.onSuccess(it)
            }, {
                callback.onError("未查询到此人的详情信息")
            })

    }
}