package aaa.android.plant.repository

import aaa.android.plant.entity.DiseaseInformation
import aaa.android.plant.util.AppConstant.DATE_FORMAT_ONE
import aaa.android.plant.util.utils.convertDateToLong
import aaa.android.plant.util.utils.getCurrentMonthEnd
import aaa.android.plant.util.utils.getCurrentMonthStart
import aaa.android.plant.dao.ExpenseDao

import androidx.lifecycle.LiveData

class Repository(private val expenseDao: ExpenseDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.


    val startDate = convertDateToLong(getCurrentMonthStart(), DATE_FORMAT_ONE)
    val endDate = convertDateToLong(getCurrentMonthEnd(), DATE_FORMAT_ONE)



    val allSignUpRequest: LiveData<List<DiseaseInformation>> =
        expenseDao.getAllUSerSignUp()









    suspend fun insertSignUp(diseaseInformation: DiseaseInformation): Long {
        return expenseDao.insertUserSignUp(diseaseInformation)
    }




    suspend fun updateUserStatus(status: String, id: Int): Int {
        return expenseDao.updateUserStatus(status, id)
    }


    suspend fun getuserLoginCheck(mobile: String, password: String): List<DiseaseInformation> {
        return expenseDao.getuserLoginCheck(mobile, password)
    }







}