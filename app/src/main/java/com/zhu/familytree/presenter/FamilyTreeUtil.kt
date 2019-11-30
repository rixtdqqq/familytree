package com.zhu.familytree.presenter

import com.zhu.familytree.model.FamilyTreeBean

/**
 * @description 工具类
 *
 * @author QQ657036139
 * @since 2019/11/9
 */
object FamilyTreeUtil {
    fun getLevel(familyTreeBean: FamilyTreeBean?, map: HashMap<Int, FamilyTreeBean>): Int {
        return if (0 == familyTreeBean?.parentNodeId) 0 else (1 + getLevel(
            getFamilyTree(
                familyTreeBean?.parentNodeId,
                map
            ), map
        ))
    }

    fun getFamilyTree(id: Int?, map: HashMap<Int, FamilyTreeBean>): FamilyTreeBean? {
        return if (id in map) map[id] else null
    }
}