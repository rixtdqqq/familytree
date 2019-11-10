package com.zhu.familytree.model

import android.os.Parcelable
import androidx.room.Entity
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
    val id: Long?,
    val memberId: Int,
    val parentId: Int,
    val name: String,
    val gender: Int
) : Parcelable