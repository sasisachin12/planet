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

@Entity(tableName = "user_signup_table")
class DonorFormDetails(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @field:TypeConverters(DateConverters::class)
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "age") val age: String,
    @ColumnInfo(name = "bloodtype") val bloodtype: String,
    @ColumnInfo(name = "donate") val donate: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "contactpersonName") val contactpersonName: String,
    @ColumnInfo(name = "contactpersonMobile") val contactpersonMobile: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "state") val state: String,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "address") val address: String,
)