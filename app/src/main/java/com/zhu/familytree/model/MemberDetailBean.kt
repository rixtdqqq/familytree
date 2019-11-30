package com.zhu.familytree.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
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
    var id: Long? = null,
    var memberId: String = "-",
    var name: String = "-",
    var gender: String = "-",
    var birth: String = "-",
    // 去世日期
    var oldDate: String = "-",
    // 伴侣
    var mateName: String = "-",
    // 籍贯
    var nativePlace: String = "-",
    var address: String = "-",
    var profession: String = "-",
    var education: String = "-",
    var parent1Id: String = "-",
    var parent1Name: String = "-",
    var parent2Id: String = "-",
    var parent2Name: String = "-",
    var event: String = "-"
) : Parcelable
//insert into member_detail values(null,3,'ying',0,'1999-1-1','-','某某','广东五华','深圳龙岗','android开发','江西理工大学本科','朱镇文','朱某某','Single：其回调为onSuccess和onError，查询成功会在onSuccess中返回结果，需要注意的是，如果未查询到结果，即查询结果为空，会直接走onError回调，抛出EmptyResultSetException异常。');