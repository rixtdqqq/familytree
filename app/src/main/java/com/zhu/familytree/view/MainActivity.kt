package com.zhu.familytree.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.zhu.familytree.R
import com.zhu.familytree.adapter.FamilyTreeAdapter
import com.zhu.familytree.base.IMainView
import com.zhu.familytree.databinding.ActivityMainBinding
import com.zhu.familytree.model.Constants
import com.zhu.familytree.model.FamilyTreeBean
import com.zhu.familytree.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @description 族谱主页面
 *
 * @author QQ657036139
 * @since 2019-11-17
 */
class MainActivity : AppCompatActivity(), IMainView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainPresenter
    private var adapter: FamilyTreeAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        presenter = MainPresenter(this)
        presenter.queryAllMembers()
        tv_import_data.setOnClickListener {
            val dialog = DialogImportData(this, presenter)
            dialog.show()
        }
        etFilter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchKeyword(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
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
                Snackbar.make(
                    home_page_container,
                    "请去设置-应用管理-朱氏族谱-应用权限，允许存储权限",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun showErrorToast(message: String) {
        empty_view.visibility = View.VISIBLE
        listView.emptyView = empty_view
        etFilter.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showAllFamilyTree(
        members: List<FamilyTreeBean>,
        map: HashMap<Int, FamilyTreeBean>
    ) {
        empty_view.visibility = View.GONE
        etFilter.visibility=View.VISIBLE
        adapter = FamilyTreeAdapter(this, members, map)
        listView.adapter = adapter
        listView.setOnItemLongClickListener { parent, view, position, id ->
            val bean = members[position]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(Constants.MEMBER_ID, bean.memberId)
            startActivity(intent)
            false
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            val bean = members[position]
            if (1 == bean.isLeaf) {
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra(Constants.MEMBER_ID, bean.memberId)
                startActivity(intent)
            } else {
                if (bean.isExpand == 1) {
                    for (temp in members) {
                        if (temp.parentNodeId == bean.memberId.toInt()) {
                            if (0 == bean.isLeaf) {
                                temp.isExpand = 0
                            }
                        }
                    }
                    bean.isExpand = 0
                } else bean.isExpand = 1
            }
            adapter?.notifyDataSetChanged()
        }

    }

    override fun searchKeyword(keyword: String) {
        adapter?.setKeyword(keyword)
    }

    override fun showSaveSuccess(message: String) {
        presenter.queryAllMembers()
        empty_view.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
