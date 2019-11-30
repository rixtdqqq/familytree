package com.zhu.familytree.base

import com.zhu.familytree.model.FamilyTreeBean

/**
 * @description 主页面的view接口
 *
 * @author QQ657036139
 * @since 2019/11/9
 */
interface IMainView : IView {

    fun checkFileManagerPermission()

    fun showAllFamilyTree(members: List<FamilyTreeBean>, map: HashMap<Int, FamilyTreeBean>)

    fun searchKeyword(keyword: String)

    fun showSaveSuccess(message:String)
}