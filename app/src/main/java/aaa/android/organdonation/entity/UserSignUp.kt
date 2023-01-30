package aaa.android.organdonation.entity

import aaa.android.organdonation.dao.DateConverters
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "user_signup_table")
class UserSignUp(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @field:TypeConverters(DateConverters::class)
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "mobile") val mobile: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "status") val status: String
)