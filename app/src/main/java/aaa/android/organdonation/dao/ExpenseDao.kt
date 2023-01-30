package aaa.android.organdonation.dao

import aaa.android.organdonation.entity.ExpenseInfo
import aaa.android.organdonation.entity.UserSignUp
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*


@Dao
interface ExpenseDao {


    @Query("SELECT * FROM expense_table WHERE date BETWEEN :from AND :to")
    fun getAllExpense(from: Long, to: Long): LiveData<List<ExpenseInfo>>

    /*@Query("Select * from expense_table ORDER BY id DESC")
    fun getAllExpense(): LiveData<List<ExpenseInfo>>
*/

    @Query("Select * from expense_table ORDER BY id DESC")
    fun getExpense(): List<ExpenseInfo>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(expenseInfo: ExpenseInfo): Long


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserSignUp(userSignUp: UserSignUp): Long

    @Query("SELECT * FROM user_signup_table where status='PV'")
    fun getAllUSerSignUp(): LiveData<List<UserSignUp>>


    @Query("DELETE FROM expense_table")
    fun deleteAll()


    @Query("DELETE FROM expense_table where id=:id")
    fun deleteByID(id: Int)

    @Query("UPDATE user_signup_table SET status =:status where id=:id")
    fun updateUserStatus(status: String?,id:Int): Int


    @Query("SELECT * FROM user_signup_table where mobile=:mobile AND password =:password")
    fun getuserLoginCheck(mobile:String,password:String): List<UserSignUp>
}