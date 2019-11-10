package com.zhu.familytree.model

/**
 * @description 族谱成员
 *
 * @author QQ657036139
 * @since 2019/11/9
 */


data class FamilyTreeEntity(

    val memberId: Int,
    val parentId: Int,
    val name: String,
    val gender: Int,
    // 0表示父节点
    val parentNodeId: Int,
    // 是否展开了节点
    val isExpand: Int,
    // 是否选中了
    val isSelected: Int,
    // 同一个级别的显示顺序
    val displayOrder: Int,
    // 1为叶子节点,0不是
    val isLeaf: Int,
    val children: List<FamilyTreeEntity>
)