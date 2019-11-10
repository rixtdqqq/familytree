package com.zhu.familytree.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zhu.familytree.model.FamilyTreeBean
import com.zhu.familytree.model.MemberDetailBean
import io.reactivex.Single

/**
 * @description 族谱数据接口
 *
 * @author QQ657036139
 * @since 2019/11/9
 */
@Dao
interface FamilyDao {
    /**
     * 插入所有的族谱成员
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMembers(familyMembers: List<FamilyTreeBean>): Single<List<Long>>

    /**
     * 插入所有的族谱成员的详细信息
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDetails(membersDetail: List<MemberDetailBean>): Single<List<Long>>

    /**
     * 根据memberId查询一个族谱成员的详细信息
     */
    @Query("SELECT * FROM member_detail WHERE memberId=:memberId")
    fun queryMemberByMemberId(memberId: Int): Single<MemberDetailBean>
}