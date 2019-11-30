package com.zhu.familytree.presenter

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.zhu.familytree.base.IDetailView
import com.zhu.familytree.model.Constants
import com.zhu.familytree.model.DetailModel
import com.zhu.familytree.model.MemberDetailBean

/**
 * @description 详情
 *
 * @author QQ657036139
 * @since 2019/11/9
 */
class DetailPresenter(view: IDetailView) : LifecycleObserver {

    private var model: DetailModel? = null
    private var mView: IDetailView? = null

    init {
        model = DetailModel()
        mView = view
    }

    /**
     * 根据memberId和parentId查询memberId对应的家人成员详情信息
     */
    fun getMemberDetailAndShow(memberId: String) {
        Log.d(Constants.DETAIL_TAG, "DetailPresenter : memberId = $memberId")
        model?.getMemberDetail(memberId, { o ->
            Log.d(Constants.DETAIL_TAG, "DetailPresenter : o = $o")
            if (o is MemberDetailBean) {
                mView?.showDetail(o)
            }
        },

            {
                mView?.showErrorToast(it)
            }
        )

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        model = null
        mView = null
    }
}