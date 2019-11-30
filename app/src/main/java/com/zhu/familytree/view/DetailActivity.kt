package com.zhu.familytree.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.zhu.familytree.R
import com.zhu.familytree.base.IDetailView
import com.zhu.familytree.databinding.ActivityDetailBinding
import com.zhu.familytree.model.Constants
import com.zhu.familytree.model.MemberDetailBean
import com.zhu.familytree.presenter.DetailPresenter
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), IDetailView {

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_detail)
        val presenter = DetailPresenter(this)
        lifecycle.addObserver(presenter)
        intent?.let {
            it.extras?.let {
                val memberId = it.getString(Constants.MEMBER_ID, "0")
                presenter.getMemberDetailAndShow(memberId)
            }
        }

        toolbar.run {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showDetail(detail: MemberDetailBean) {
        Log.d(Constants.DETAIL_TAG, "DetailActivity : person = $detail")
        binding.member = detail
    }

    override fun showErrorToast(message: String) {
        Snackbar.make(detail_container, message, Snackbar.LENGTH_LONG).show()
    }
}
