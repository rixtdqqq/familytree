package com.zhu.familytree.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.zhu.familytree.base.DataCallback
import com.zhu.familytree.model.DetailModel
import com.zhu.familytree.model.MemberDetailBean
import com.zhu.familytree.base.IDetailView

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

    fun getMemberDetailAndShow(memberId: Int) {
        model?.getMemberDetail(memberId, object : DataCallback {
            override fun onSuccess(o: Any) {
                if (o is MemberDetailBean) {
                    mView?.showDetail(o)
                }
            }

            override fun onError(message: String) {
                mView?.showErrorToast(message)
            }
        })

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        model = null
        mView = null
    }
}