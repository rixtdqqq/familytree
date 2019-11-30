package com.zhu.familytree.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * @description 族谱成员
 *
 * @author QQ657036139
 * @since 2019/11/9
 */

@Entity(tableName = "family_members")
@Parcelize
data class FamilyTreeBean(
    @PrimaryKey
    var id: Long? = null,
    var memberId: String = "-",
    var parentId: String = "-",
    var name: String = "-",
    var gender: String = "-",
    // 0表示父节点
    @Ignore
    var parentNodeId: Int = 0,
    // 是否展开了节点
    @Ignore
    var isExpand: Int = 0,
    // 是否选中了
    @Ignore
    var isSelected: Int = 0,
    // 同一个级别的显示顺序
    @Ignore
    var displayOrder: Int = 0,
    // 1为叶子节点,0不是
    @Ignore
    var isLeaf: Int = 1,
    @Ignore
    var children: List<FamilyTreeBean> = mutableListOf()
) : Parcelable