package com.zhu.familytree.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.zhu.familytree.R
import com.zhu.familytree.base.IMainView
import com.zhu.familytree.databinding.ActivityMainBinding
import com.zhu.familytree.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IMainView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        presenter = MainPresenter(this)
        empty_view.visibility = View.VISIBLE
        tv_import_data.setOnClickListener {
            val dialog = DialogImportData(this, presenter)
            dialog.show()
        }
    }

    override fun checkFileManagerPermission() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 10)
        } else {
            presenter.importFamilyTreeDataFromSDCard2Database()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 10 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            presenter.importFamilyTreeDataFromSDCard2Database()
        } else {
            val showExternal =
                shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (showExternal) {
                Snackbar.make(home_page_container, "你未打开存储权限", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(home_page_container, "请去设置-应用管理-朱氏族谱-应用权限，允许存储权限", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun showErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
