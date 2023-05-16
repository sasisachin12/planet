package aaa.android.plant.entity

import aaa.android.plant.dao.DateConverters
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "user_signup_table")
@Parcelize
class DiseaseInformation(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @field:TypeConverters(DateConverters::class)
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "mobile") val mobile: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var data: ByteArray? = null,
    @ColumnInfo(name = "username") val username: String?= null,
    @ColumnInfo(name = "usermobile") val usermobile: String?= null,
    @ColumnInfo(name = "date") val date: String?=null
): Parcelable


@Entity(tableName = "SearchHistory")
@Parcelize
class SearchHistoryDiseaseInformation(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @field:TypeConverters(DateConverters::class)
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "mobile") val mobile: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var data: ByteArray? = null,
    @ColumnInfo(name = "username") val username: String?= null,
    @ColumnInfo(name = "usermobile") val usermobile: String?= null,
    @ColumnInfo(name = "date") val date: String?=null
): Parcelable



