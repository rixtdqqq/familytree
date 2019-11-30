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

data class FamilyTreeListBean(
    val list: List<FamilyTreeBean> = mutableListOf()
)