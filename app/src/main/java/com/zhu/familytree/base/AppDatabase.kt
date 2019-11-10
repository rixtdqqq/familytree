package com.zhu.familytree.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zhu.familytree.App
import com.zhu.familytree.model.FamilyTreeBean
import com.zhu.familytree.model.MemberDetailBean

/**
 * @description 创建数据库
 *
 * @author QQ657036139
 * @since 2019/11/9
 */
@Database(entities = [FamilyTreeBean::class, MemberDetailBean::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun familyDao(): FamilyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(App.getContext()).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "family_tree.db"
            )
                .build()

    }
}