package com.zhu.familytree.model

import com.zhu.familytree.base.AppDatabase
import com.zhu.familytree.base.BaseModel

/**
 * @description 详情model
 *
 * @author QQ657036139
 * @since 2019/11/9
 */

class DetailModel : BaseModel() {

    fun getMemberDetail(memberId: String, onSuccess: (o: Any) -> Unit, onError: (String) -> Unit) {
        AppDatabase.getInstance().familyDao()
            .queryMemberByMemberIdAndParentId(memberId).subscribeDbResult({
                onSuccess(it)
            }, {
                onError("未查询到此人的详情信息")
            })

    }
}