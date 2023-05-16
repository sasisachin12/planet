package aaa.android.plant.repository

import aaa.android.plant.entity.DiseaseInformation
import aaa.android.plant.util.AppConstant.DATE_FORMAT_ONE
import aaa.android.plant.util.utils.convertDateToLong
import aaa.android.plant.util.utils.getCurrentMonthEnd
import aaa.android.plant.util.utils.getCurrentMonthStart
import aaa.android.plant.dao.ExpenseDao
import aaa.android.plant.entity.SearchHistoryDiseaseInformation

import androidx.lifecycle.LiveData

class Repository(private val expenseDao: ExpenseDao) {



    val allSignUpRequest: LiveData<List<DiseaseInformation>> =
        expenseDao.getAllUSerSignUp()


    val allSearchHistoryRequest: LiveData<List<SearchHistoryDiseaseInformation>> =
        expenseDao.getAllSearchHistory()






    suspend fun insertSignUp(diseaseInformation: DiseaseInformation): Long {
        return expenseDao.insertUserSignUp(diseaseInformation)
    }

    suspend fun insertSearchHistory(diseaseInformation: SearchHistoryDiseaseInformation): Long {
        return expenseDao.insertSearchHistory(diseaseInformation)
    }


    suspend fun updateUserStatus(diseaseInformation: DiseaseInformation): Int {

        val id=diseaseInformation.id
        val name=diseaseInformation.name
        val email=diseaseInformation.email
        val mobile=diseaseInformation.mobile
        val password=diseaseInformation.password
        val status=diseaseInformation.status
        val data=diseaseInformation.data
        return expenseDao.updateUserStatus(id,name,email,mobile,password,status,data)
    }


    suspend fun getuserLoginCheck(mobile: String, password: String): List<DiseaseInformation> {
        return expenseDao.getuserLoginCheck(mobile, password)
    }


    suspend fun getDeleteById(id: Int) {
        expenseDao.deleteByID(id)
    }





}