package aaa.android.plant.dao


import aaa.android.plant.entity.DiseaseInformation
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*


@Dao
interface ExpenseDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserSignUp(diseaseInformation: DiseaseInformation): Long

    @Query("SELECT * FROM user_signup_table")
    fun getAllUSerSignUp(): LiveData<List<DiseaseInformation>>



    @Query("UPDATE user_signup_table SET status =:status where id=:id")
    fun updateUserStatus(status: String?, id: Int): Int


    @Query("SELECT * FROM user_signup_table where mobile=:mobile AND password =:password")
    fun getuserLoginCheck(mobile: String, password: String): List<DiseaseInformation>



}