package com.zhu.familytree.presenter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.zhu.familytree.model.FamilyTreeBean

/**
 * @description 族谱adapter
 *
 * @author QQ657036139
 * @since 2019/11/9
 */
class FamilyTreeAdapter(
    val context: Context,
    val familyTreeBeans: List<FamilyTreeBean>,
    val map: HashMap<Int, FamilyTreeBean>
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}