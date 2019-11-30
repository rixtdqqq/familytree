package com.zhu.familytree.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.zhu.familytree.R
import com.zhu.familytree.model.FamilyTreeBean
import android.text.Html
import com.zhu.familytree.model.Constants
import com.zhu.familytree.presenter.FamilyTreeUtil
import com.zhu.familytree.view.MainActivity


/**
 * @description 族谱adapter
 *
 * @author QQ657036139
 * @since 2019/11/9
 */
class FamilyTreeAdapter(
    val context: Context,
    val familyTreeBeans: List<FamilyTreeBean>,
    val map: HashMap<Int, FamilyTreeBean> = hashMapOf(),
    var keywords: String = ""
) : BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_family_tree, parent, false)
            holder = ViewHolder()
            holder.apply {
                text = view.findViewById(R.id.text)
                icon = view.findViewById(R.id.icon)
                ib_select = view.findViewById(R.id.ib_select)
            }
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        val bean = familyTreeBeans[position]
        val level = FamilyTreeUtil.getLevel(bean, map)
        holder.apply {
            icon?.setPadding(25 * level, icon?.paddingTop ?: 0, 0, icon?.paddingBottom ?: 0)
            if (0 == bean.isLeaf) { //如果为父节点
                icon?.visibility = View.VISIBLE
                if (bean.isExpand != 0) { //不展开显示加号
                    icon?.setImageResource(R.drawable.ic_arrow_drop_up_black_16dp)
                } else { //展开显示减号
                    icon?.setImageResource(R.drawable.ic_arrow_drop_down_black_16dp)
                }
            } else { //如果叶子节点，不占位显示
                icon?.visibility = View.INVISIBLE
            }
            if (keywords.isNotEmpty() && bean.name.contains(keywords)) {
                val index = bean.name.indexOf(keywords)
                val len = keywords.length
                val temp = Html.fromHtml(
                    bean.name.substring(0, index)
                            + "<font color=#FF0000>"
                            + bean.name.substring(index, index + len) + "</font>"
                            + bean.name.substring(
                        index + len,
                        bean.name.length
                    )
                )

                text?.text = temp
            } else {
                text?.text = bean.name
            }
            text?.compoundDrawablePadding = dip2px(context, 10.0f)
        }
        return view!!

    }

    private fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 长按跳转到详情页面
     */
    fun onItemLongClick(position: Int) {
        val bean = familyTreeBeans[position]
        val intent = (context as MainActivity).intent
        intent.putExtra(Constants.MEMBER_ID, bean.memberId)
        intent.putExtra(Constants.PARENT_ID, bean.parentId)
        context.startActivity(intent)
    }

    fun onItemClick(position: Int) {
        val bean = familyTreeBeans[position]
        if (1 != bean.isLeaf && bean.isExpand == 1) {
            for (temp in familyTreeBeans) {
                if (temp.parentNodeId.toString() == bean.memberId) {
                    temp.isExpand = 0
                }
            }
            bean.isExpand = 0
        } else {
            bean.isExpand = 1
        }
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): FamilyTreeBean {
        return familyTreeBeans[convertPosition(position)]
    }

    private fun convertPosition(position: Int): Int {
        var count = 0
        var i = 0
        for (temp in familyTreeBeans) {
            i++
            if (temp.parentNodeId == 0) {
                count++
            } else {
                if (getItemIsExpand(temp.parentNodeId) == 0) {
                    count++
                }
            }
            if (position == count - 1) {
                return i
            }
        }
        return 0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        var count = 0
        for (temp in familyTreeBeans) {
            if (temp.parentNodeId == 0) {
                count++
            } else {
                if (getItemIsExpand(temp.parentNodeId) == 0) {
                    count++
                }
            }
        }
        return count
    }

    private fun getItemIsExpand(parentNodeId: Int): Int {
        for (temp in familyTreeBeans) {
            if (temp.memberId == parentNodeId.toString()) {
                return temp.isExpand
            }
        }
        return 0
    }

    /**
     * 搜索的时候，先关闭所有的条目，然后，按照条件，找到含有关键字的数据
     * 如果是叶子节点，
     */
    fun setKeyword(keyword: String) {
        this.keywords = keyword
        for (treePoint in familyTreeBeans) {
            treePoint.isExpand = 0
        }
        if (keyword.isNotEmpty()) {
            for (tempTreePoint in familyTreeBeans) {
                if (tempTreePoint.name.contains(keyword)) {
                    if (tempTreePoint.isLeaf == 0) {   //非叶子节点
                        tempTreePoint.isExpand = 0
                    }
                    //展开从最顶层到该点的所有节点
                    openExpand(tempTreePoint)
                }
            }
        }
        this.notifyDataSetChanged()
    }

    private fun openExpand(treePoint: FamilyTreeBean?) {
        val parentNodeId = treePoint?.parentNodeId
        if (parentNodeId == 0) {
            treePoint.isExpand = 1
        } else {
            map[parentNodeId]?.isExpand = 1
            openExpand(map[parentNodeId])
        }
    }

    internal inner class ViewHolder {
        var text: TextView? = null
        var icon: ImageView? = null
        var ib_select: ImageButton? = null
    }
}