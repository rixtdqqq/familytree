package com.zhu.familytree.presenter

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import com.zhu.familytree.base.IMainView
import com.zhu.familytree.model.Constants
import com.zhu.familytree.model.FamilyTreeBean
import com.zhu.familytree.model.MainModel
import java.util.*

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
        model.importFamilyTreeDataFromSDCard({

            mView.showSaveSuccess("导入成功")
        },

            {
                mView.showErrorToast(it as String)
            }
        )
    }

    fun queryAllMembers() {
        model.getAllMember({ message ->
            Log.d(Constants.MAIN_TAG, "message = $message")
            mView.showErrorToast(message)
        }, { o ->
            Log.d(Constants.MAIN_TAG, "o = $o")
            if (o is List<*> && o.isNotEmpty()) {
                val members = o as List<FamilyTreeBean>
                val membersList = mutableListOf<FamilyTreeBean>()
                for (bean in members) {
                    if (bean.parentId.isNotEmpty()) {
                        bean.children = getSubMembersByParentId(bean.memberId, members)
                        if ("0" == bean.parentId) {
                            membersList.add(bean)
                        }
                    }
                }
                val map: HashMap<Int, FamilyTreeBean> = hashMapOf()
                sortList(members, map) // 排序
                mView.showAllFamilyTree(members, map)
            } else mView.showErrorToast("无数据，请导入数据。")
        }
        )
    }

    private fun getSubMembersByParentId(
        parentId: String,
        members: List<FamilyTreeBean>
    ): List<FamilyTreeBean> {
        val membersList = mutableListOf<FamilyTreeBean>()
        for (member in members) {
            if (parentId == member.parentId) {
                membersList.add(member)
            }
        }
        return membersList
    }

    private fun sortList(members: List<FamilyTreeBean>, map: HashMap<Int, FamilyTreeBean>) {
        for (temp in members) {
            map[temp.memberId.toInt()] = temp
            temp.displayOrder = temp.memberId.length

        }
        Collections.sort(members, object : Comparator<FamilyTreeBean> {
            override fun compare(bean1: FamilyTreeBean, bean2: FamilyTreeBean): Int {
                val level1 = FamilyTreeUtil.getLevel(bean1, map)
                val level2 = FamilyTreeUtil.getLevel(bean2, map)
                if (level1 == level2) {
                    if (bean1.parentNodeId.toString() == bean2.parentNodeId.toString()) { // 左边小
                        return if (bean1.displayOrder > bean2.displayOrder) 1 else -1
                    } else { // 如果父辈id不相等
                        // 同一级别，不同父辈
                        val beanTemp1 = FamilyTreeUtil.getFamilyTree(bean1.memberId.toInt(), map)
                        val beanTemp2 = FamilyTreeUtil.getFamilyTree(bean2.memberId.toInt(), map)
                        if (null == beanTemp1 || null == beanTemp2) {
                            return 0
                        }
                        return compare(beanTemp1, beanTemp2) // 父辈
                    }
                } else { // 不同级别
                    if (level1 > level2) {  // 左边级别大       左边小
                        if (bean1.parentNodeId.toString() == bean2.memberId) 1 else {
                            val temp = FamilyTreeUtil.getFamilyTree(bean1.parentNodeId, map)
                            if (null == temp) {
                                return 1
                            }
                            return compare(temp, bean2)
                        }
                    } else { // 右边级别大   右边小
                        if (bean2.parentNodeId.toString() == bean1.memberId) return -1 else {
                            val temp = FamilyTreeUtil.getFamilyTree(bean2.parentNodeId, map)
                            if (null == temp) {
                                return -1
                            }
                            return compare(bean1, temp)
                        }
                    }
                }
                return 0
            }
        })

    }

}