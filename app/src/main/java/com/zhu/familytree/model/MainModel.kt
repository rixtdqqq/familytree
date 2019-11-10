package com.zhu.familytree.model

import android.os.Environment
import com.zhu.familytree.App
import com.zhu.familytree.base.AppDatabase
import com.zhu.familytree.base.BaseModel
import com.zhu.familytree.base.DataCallback
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jxl.Workbook
import java.io.File
import java.io.FileInputStream

/**
 * 主页面的数据操作页面
 *
 * @author QQ657036139
 * @since 2019/11/9
 */
class MainModel : BaseModel() {

    fun hasSDCard(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }


    fun importFamilyTreeDataFromSDCard(callback: DataCallback) {

        Environment.getExternalStorageDirectory()
        val filePath = App.getContext().getExternalFilesDir(null)?.path + "/familyTree.xls"
        val readFile = File(filePath);
        if (!readFile.exists()) {
            callback.onError("未找到族谱文件，请确认文件路径或文件名。");
            return;
        }
        val inputStream = FileInputStream(readFile)
        val workbook = Workbook.getWorkbook(inputStream)
        saveFamilyTreeData2Database(workbook, callback)
        saveMemberDetailData2Database(workbook, callback)
    }

    private fun saveFamilyTreeData2Database(workbook: Workbook, callback: DataCallback) {
        val familyTreeBeans = mutableListOf<FamilyTreeBean>()
        val sheet = workbook.getSheet(0)
        sheet.apply {
            for (i in 1 until rows) {
                val memberId = getCell(0, i)
                val parentId = getCell(1, i)
                val name = getCell(2, i)
                val gender = getCell(3, i)
                if (memberId.contents.isEmpty()) {
                    continue
                }
                val bean = FamilyTreeBean(
                    id = null,
                    memberId = memberId.contents.toInt(),
                    parentId = parentId.contents.toInt(),
                    name = name.contents,
                    gender = gender.contents.toInt()
                )
                familyTreeBeans.add(bean)
            }
        }
        AppDatabase.getInstance().familyDao().insertAllMembers(familyTreeBeans)
            .subscribeDbResult({ callback.onSuccess(it) }, { callback.onError("保存数据失败，请复试") })

    }

    private fun saveMemberDetailData2Database(workbook: Workbook, callback: DataCallback) {
        val memberDetailBeans = mutableListOf<MemberDetailBean>()
        val sheet = workbook.getSheet(1)
        sheet.apply {
            for (i in 1 until rows) {
                val memberId = getCell(0, i)
                val name = getCell(1, i)
                val gender = getCell(2, i)
                val birth = getCell(3, i)
                val oldDate = getCell(4, i)
                val mateName = getCell(5, i)
                val nativePlace = getCell(6, i)
                val address = getCell(7, i)
                val profession = getCell(8, i)
                val education = getCell(9, i)
                val parent1 = getCell(10, i)
                val parent2 = getCell(11, i)
                val event = getCell(12, i)
                if (memberId.contents.isEmpty()) {
                    continue
                }
                val bean = MemberDetailBean(
                    id = null,
                    memberId = memberId.contents.toInt(),
                    name = name.contents,
                    gender = gender.contents.toInt(),
                    birth = birth.contents,
                    oldDate = oldDate.contents,
                    mateName = mateName.contents,
                    nativePlace = nativePlace.contents,
                    address = address.contents,
                    profession = profession.contents,
                    education = education.contents,
                    parent1 = parent1.contents,
                    parent2 = parent2.contents,
                    event = event.contents
                )
                memberDetailBeans.add(bean)
            }
        }
        workbook.close()
        AppDatabase.getInstance().familyDao().insertAllDetails(memberDetailBeans)
            .subscribeDbResult({ callback.onSuccess(it) }, { callback.onError("保存数据失败，请复试") })
    }
}