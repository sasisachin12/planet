package aaa.android.plant.db

import aaa.android.plant.dao.ExpenseDao

import aaa.android.plant.entity.DiseaseInformation
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(
    entities = arrayOf(DiseaseInformation::class),
    version = 2,
    exportSchema = false
)

abstract class ApplicationRoomDatabase : RoomDatabase() {


    abstract fun expenseDao(): ExpenseDao


    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var expenseDao = database.expenseDao()

                    // Delete all content here.
                    //  expenseDao.deleteAll()

                    // Add sample words.
                    //  var expenseInfo =
                    //       ExpenseInfo(1, date = "16-04-2020", expense = "Hello", amount = "100")
                    //   expenseDao.insert(expenseInfo)
                    // word = Word("World!")
                    //expenseDao.insert(word)

                    // TODO: Add your own words!
                    //  word = Word("TODO!")
                    // expenseDao.insert(word)
                }
            }
        }

    }

    companion object {
        @Volatile
        private var INSTANCE: ApplicationRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ApplicationRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationRoomDatabase::class.java,
                    "disease_database"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }


}