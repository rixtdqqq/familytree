package com.zhu.familytree.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.zhu.familytree.R
import com.zhu.familytree.base.IMainView
import com.zhu.familytree.presenter.MainPresenter
import kotlinx.android.synthetic.main.dialog_import.*

/**
 * @description 导入数据的底部弹窗
 *
 * @author QQ657036139
 * @since 2019/11/10
 */
class DialogImportData(
    context: Context,
    presenter: MainPresenter
) :
    Dialog(context, R.style.ActionSheetDialogStyle) {

    private val mPresenter: MainPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_import)
        window?.run {
            setGravity(Gravity.BOTTOM)
            val params = attributes
            params.apply {
                width = WindowManager.LayoutParams.MATCH_PARENT
                height = WindowManager.LayoutParams.WRAP_CONTENT
            }
            attributes = params
        }
        initView()
    }

    private fun initView() {
        tv_import_start.setOnClickListener {
            mPresenter.importFamilyTreeDataFromSDCard2Database()
            this.dismiss()
        }
        tv_import_instruction.setOnClickListener {
            context.startActivity(Intent(context, InstructionImportActivity::class.java))
            this.dismiss()
        }

        tv_import_cancel.setOnClickListener {
            this.dismiss()
        }
    }
}