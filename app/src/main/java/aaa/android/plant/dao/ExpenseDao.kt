package aaa.android.plant.dao


import aaa.android.plant.entity.DiseaseInformation
import aaa.android.plant.entity.SearchHistoryDiseaseInformation
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


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchHistory(diseaseInformation: SearchHistoryDiseaseInformation): Long


    @Query("SELECT * FROM SearchHistory")
    fun getAllSearchHistory(): LiveData<List<SearchHistoryDiseaseInformation>>

    @Query("UPDATE user_signup_table SET name=:name,email =:email,mobile=:mobile,password=:password,status =:status,data=:data where id=:id")
    fun updateUserStatus(id: Int?,name:String,email:String,mobile:String,password:String,status:String,data:ByteArray?): Int


    @Query("SELECT * FROM user_signup_table where mobile=:mobile AND password =:password")
    fun getuserLoginCheck(mobile: String, password: String): List<DiseaseInformation>

    @Query("DELETE FROM user_signup_table where id=:id")
    fun deleteByID(id: Int)

}