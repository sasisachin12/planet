package aaa.android.organdonation.repository

import aaa.android.organdonation.entity.ExpenseInfo
import aaa.android.organdonation.entity.UserSignUp
import aaa.android.organdonation.util.AppConstant.DATE_FORMAT_ONE
import aaa.android.organdonation.util.utils.convertDateToLong
import aaa.android.organdonation.util.utils.getCurrentMonthEnd
import aaa.android.organdonation.util.utils.getCurrentMonthStart
import aaa.android.organdonation.dao.ExpenseDao
import aaa.android.organdonation.entity.DonorFormDetails
import aaa.android.organdonation.entity.Hospital

import androidx.lifecycle.LiveData

class Repository(private val expenseDao: ExpenseDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.


    val startDate = convertDateToLong(getCurrentMonthStart(), DATE_FORMAT_ONE)
    val endDate = convertDateToLong(getCurrentMonthEnd(), DATE_FORMAT_ONE)

    val allExpenses: LiveData<List<ExpenseInfo>> =
        expenseDao.getAllExpense(startDate, endDate)


    val allSignUpRequest: LiveData<List<UserSignUp>> =
        expenseDao.getAllUSerSignUp()

    val allDonorFomRequest: LiveData<List<DonorFormDetails>> =
        expenseDao.getDonorFormRequestDetails()

    val getHospitalDetails: LiveData<List<Hospital>> =
        expenseDao.getHospitalDetails()

    suspend fun insert(expenseInfo: ExpenseInfo): Long {
        return expenseDao.insert(expenseInfo)
    }


    suspend fun getAll(): List<ExpenseInfo> {
        return expenseDao.getExpense()
    }


    suspend fun getDeleteById(id: Int) {
        expenseDao.deleteByID(id)
    }

    suspend fun insertSignUp(userSignUp: UserSignUp): Long {
        return expenseDao.insertUserSignUp(userSignUp)
    }


    suspend fun insertUserDonorForm(donorFormDetails: DonorFormDetails): Long {
        return expenseDao.insertUserDonorForm(donorFormDetails)
    }

    suspend fun updateUserStatus(status: String, id: Int): Int {
        return expenseDao.updateUserStatus(status, id)
    }


    suspend fun getuserLoginCheck(mobile: String, password: String): List<UserSignUp> {
        return expenseDao.getuserLoginCheck(mobile, password)
    }

    suspend fun updateDonorFormStatus(status: String, id: Int): Int {
        return expenseDao.updateDonorStatus(status, id)
    }

    suspend fun getDonorFormStatus(): List<DonorFormDetails>? {
        return expenseDao.getDonorFormRequestDetails().value
    }

    suspend fun insertHospital(hospital: Hospital): Long {
        return expenseDao.insertHospital(hospital)
    }

    suspend fun updateHospitalStatusResponse(status: String, id: Int): Int {
        return expenseDao.updateHospitalStatusResponse(status, id)
    }

    suspend fun getHospitalDetails(): List<Hospital>? {
        return expenseDao.getHospitalDetails().value
    }

}