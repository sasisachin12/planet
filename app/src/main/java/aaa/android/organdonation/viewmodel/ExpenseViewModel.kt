package aaa.android.organdonation.viewmodel

import aaa.android.organdonation.entity.ExpenseInfo
import aaa.android.organdonation.entity.UserSignUp
import aaa.android.organdonation.db.ExpenseRoomDatabase
import aaa.android.organdonation.entity.DonorFormDetails
import aaa.android.organdonation.entity.Hospital
import aaa.android.organdonation.repository.Repository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: Repository

    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allExpenses: LiveData<List<ExpenseInfo>>
    val allSignUpRequest: LiveData<List<UserSignUp>>
    var allDonorRequest: LiveData<List<DonorFormDetails>>
    val currentData = CompletableDeferred<List<ExpenseInfo>>()
    val loginData = CompletableDeferred<List<UserSignUp>>()
    val insertResponse = CompletableDeferred<Long>()
    val insertdonorformResponse = CompletableDeferred<Long>()
    val updateStatusResponse = CompletableDeferred<Int>()
    val updateDonorStatusResponse = CompletableDeferred<Int>()
    val searchDataList: LiveData<List<UserSignUp>>
    val alldonorFormList = CompletableDeferred<List<DonorFormDetails>>()
    val allHospital = CompletableDeferred<List<Hospital>>()
    val allUserdonorFormList = CompletableDeferred<List<DonorFormDetails>>()
    var allHospitialRequest: LiveData<List<Hospital>>

    init {
        val wordsDao = ExpenseRoomDatabase.getDatabase(app, viewModelScope).expenseDao()
        repository = Repository(wordsDao)
        allExpenses = repository.allExpenses
        allSignUpRequest = repository.allSignUpRequest
        allDonorRequest = repository.allDonorFomRequest
        searchDataList = repository.allSignUpRequest
        allHospitialRequest = repository.getHospitalDetails
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    suspend fun insert(word: ExpenseInfo): Long {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.insert(word)
            insertResponse.complete(result)
        }
        return insertResponse.await()
    }

    suspend fun insertSignUp(userSignUp: UserSignUp): Long {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.insertSignUp(userSignUp)
            insertResponse.complete(result)
        }
        return insertResponse.await()
    }

    suspend fun insertUserDonorForm(donorFormDetails: DonorFormDetails): Long {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.insertUserDonorForm(donorFormDetails)
            insertdonorformResponse.complete(result)
        }
        return insertdonorformResponse.await()
    }

    suspend fun getAllDonor(): List<ExpenseInfo> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAll()
            currentData.complete(result)
        }
        return currentData.await()
    }


    suspend fun deleteByID(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getDeleteById(id)

        }

    }


    suspend fun updateUserStatus(status: String, id: Int): Int {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateUserStatus(status, id)
            updateStatusResponse.complete(result)
        }
        return updateStatusResponse.await()
    }

    suspend fun getuserLoginCheck(mobile: String, password: String): List<UserSignUp> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getuserLoginCheck(mobile, password)
            loginData.complete(result)
        }
        return loginData.await()
    }


    suspend fun updateDonorStatusResponse(status: String, id: Int): Int {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateDonorFormStatus(status, id)
            updateDonorStatusResponse.complete(result)
        }
        return updateDonorStatusResponse.await()
    }


    suspend fun getDonorAllFormResponse(): List<DonorFormDetails> {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getDonorFormStatus()
            data?.let { alldonorFormList.complete(it) }

        }
        return alldonorFormList.await()
    }




    suspend fun insertHospital(hospital: Hospital): Long {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.insertHospital(hospital)
            insertResponse.complete(result)
        }
        return insertResponse.await()
    }

    suspend fun updateHospitalStatusResponse(status: String, id: Int): Int {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateHospitalStatusResponse(status, id)
            updateDonorStatusResponse.complete(result)
        }
        return updateDonorStatusResponse.await()
    }

    suspend fun getHospitalDetails(): List<Hospital> {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getHospitalDetails()
            data?.let { allHospital.complete(it) }

        }
        return allHospital.await()
    }

}