package aaa.android.plant.viewmodel

import aaa.android.plant.db.ApplicationRoomDatabase
import aaa.android.plant.entity.DiseaseInformation
import aaa.android.plant.repository.Repository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: Repository


    val allSignUpRequest: LiveData<List<DiseaseInformation>>

    val loginData = CompletableDeferred<List<DiseaseInformation>>()
    val insertResponse = CompletableDeferred<Long>()

    val updateStatusResponse = CompletableDeferred<Int>()

    val searchDataList: LiveData<List<DiseaseInformation>>


    init {
        val wordsDao = ApplicationRoomDatabase.getDatabase(app, viewModelScope).expenseDao()
        repository = Repository(wordsDao)

        allSignUpRequest = repository.allSignUpRequest

        searchDataList = repository.allSignUpRequest

    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */


    suspend fun insertSignUp(diseaseInformation: DiseaseInformation): Long {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.insertSignUp(diseaseInformation)
            insertResponse.complete(result)
        }
        return insertResponse.await()
    }








    suspend fun updateUserStatus(status: String, id: Int): Int {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateUserStatus(status, id)
            updateStatusResponse.complete(result)
        }
        return updateStatusResponse.await()
    }

    suspend fun getuserLoginCheck(mobile: String, password: String): List<DiseaseInformation> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getuserLoginCheck(mobile, password)
            loginData.complete(result)
        }
        return loginData.await()
    }














}