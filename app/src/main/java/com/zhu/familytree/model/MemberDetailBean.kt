package com.zhu.familytree.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * @description 成员详情
 *
 * @author QQ657036139
 * @since 2019/11/9
 */

@Entity(tableName = "member_detail")
@Parcelize
data class MemberDetailBean(
    @PrimaryKey
    val id: Long?,
    val memberId: Int,
    val name: String,
    val gender: Int,
    val birth: String,
    // 去世日期
    val oldDate: String,
    // 伴侣
    val mateName: String,
    // 籍贯
    val nativePlace: String,
    val address: String,
    val profession: String,
    val education: String,
    val parent1: String,
    val parent2: String,
    val event: String
) : Parcelable
//insert into member_detail values(null,3,'ying',0,'1999-1-1','-','某某','广东五华','深圳龙岗','android开发','江西理工大学本科','朱镇文','朱某某','Single：其回调为onSuccess和onError，查询成功会在onSuccess中返回结果，需要注意的是，如果未查询到结果，即查询结果为空，会直接走onError回调，抛出EmptyResultSetException异常。');