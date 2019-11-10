package com.zhu.familytree.presenter

import androidx.lifecycle.LifecycleObserver
import com.zhu.familytree.base.DataCallback
import com.zhu.familytree.base.IMainView
import com.zhu.familytree.model.MainModel

/**
 * @description 主页面的presenter
 *
 * @author QQ657036139
 * @since 2019/11/9
 */
class MainPresenter(view: IMainView) : LifecycleObserver {
    private val mView: IMainView = view
    private var model: MainModel = MainModel()

    fun checkFileManagerPermission() {
        if (model.hasSDCard()) {
            mView.checkFileManagerPermission()
        } else {
            mView.showErrorToast("未发现SD卡")
        }
    }

    fun importFamilyTreeDataFromSDCard2Database() {
        model.importFamilyTreeDataFromSDCard(object : DataCallback {

            override fun onSuccess(o: Any) {

            }

            override fun onError(message: String) {
                mView.showErrorToast(message)
            }
        })
    }
}