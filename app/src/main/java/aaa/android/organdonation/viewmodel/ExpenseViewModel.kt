package aaa.android.organdonation.viewmodel

import aaa.android.organdonation.entity.ExpenseInfo
import aaa.android.organdonation.entity.UserSignUp
import aaa.android.organdonation.db.ExpenseRoomDatabase
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
    val currentData = CompletableDeferred<List<ExpenseInfo>>()
    val insertResponse = CompletableDeferred<Long>()
    val updateStatusResponse = CompletableDeferred<Int>()
    val searchDataList : LiveData<List<UserSignUp>>

    init {
        val wordsDao = ExpenseRoomDatabase.getDatabase(app, viewModelScope).expenseDao()
        repository = Repository(wordsDao)
        allExpenses = repository.allExpenses
        allSignUpRequest = repository.allSignUpRequest
        searchDataList = repository.allSignUpRequest
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

    suspend fun getExpense(): List<ExpenseInfo> {
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


    suspend fun updateUserStatus(status: String,id:Int): Int {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateUserStatus(status,id)
            updateStatusResponse.complete(result)
        }
        return updateStatusResponse.await()
    }

    suspend fun getExpense(mobile:String,password:String): LiveData<List<UserSignUp>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getuserLoginCheck(mobile, password)
            //searchData.complete(result)
        }
        return searchDataList.await()
    }

}