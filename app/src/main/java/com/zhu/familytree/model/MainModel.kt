package com.zhu.familytree.model

import android.os.Environment
import com.zhu.familytree.App
import com.zhu.familytree.base.AppDatabase
import com.zhu.familytree.base.BaseModel
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


    fun importFamilyTreeDataFromSDCard(onError:(String)->Unit, onSuccess:(o:Any) -> Unit) {

        Environment.getExternalStorageDirectory()
        val filePath = App.getContext().getExternalFilesDir(null)?.path + "/familyTree.xls"
        val readFile = File(filePath);
        if (!readFile.exists()) {
            onError("未找到族谱文件，请确认文件路径或文件名。");
            return;
        }
        val inputStream = FileInputStream(readFile)
        val workbook = Workbook.getWorkbook(inputStream)
        saveFamilyTreeData2Database(workbook, onError, onSuccess)
        saveMemberDetailData2Database(workbook, onError, onSuccess)
    }

    private fun saveFamilyTreeData2Database(workbook: Workbook, onError:(String)->Unit, onSuccess:(o:Any) -> Unit) {
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
                    memberId = memberId.contents,
                    parentId = parentId.contents,
                    name = name.contents,
                    gender = gender.contents
                )
                familyTreeBeans.add(bean)
            }
        }
        AppDatabase.getInstance().familyDao().insertAllMembers(familyTreeBeans)
            .subscribeDbResult({ onSuccess(it) }, { onError("保存数据失败，请复试") })

    }

    private fun saveMemberDetailData2Database(workbook: Workbook, onError:(String)->Unit, onSuccess:(o:Any) -> Unit) {
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
                val parent1Name = getCell(11, i)
                val parent2 = getCell(12, i)
                val parent2Name = getCell(13, i)
                val event = getCell(14, i)
                if (memberId.contents.isEmpty()) {
                    continue
                }
                val bean = MemberDetailBean(
                    memberId = memberId.contents,
                    name = name.contents,
                    gender = gender.contents,
                    birth = birth.contents,
                    oldDate = oldDate.contents,
                    mateName = mateName.contents,
                    nativePlace = nativePlace.contents,
                    address = address.contents,
                    profession = profession.contents,
                    education = education.contents,
                    parent1Id = parent1.contents,
                    parent1Name = parent1Name.contents,
                    parent2Id = parent2.contents,
                    parent2Name = parent2Name.contents,
                    event = event.contents
                )
                memberDetailBeans.add(bean)
            }
        }
        workbook.close()
        AppDatabase.getInstance().familyDao().insertAllDetails(memberDetailBeans)
            .subscribeDbResult({ onSuccess(it) }, { onError("保存数据失败，请复试") })
    }

    /**
     * 从数据库中查询所有的族谱成员
     */
    fun getAllMember(onError:(String)->Unit, onSuccess:(o:Any) -> Unit) {
        AppDatabase.getInstance().familyDao().queryAllMembers()
            .subscribeDbResult({ onSuccess(it) }, { onError("查询失败，请退出应用重试。") })
    }
}