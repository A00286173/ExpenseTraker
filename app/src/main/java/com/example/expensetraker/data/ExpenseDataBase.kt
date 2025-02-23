package com.example.expensetraker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.expensetraker.data.dao.ExpenseDao
import com.example.expensetraker.data.model.ExpenseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ExpenseEntity::class], version = 2)
abstract class ExpenseDataBase: RoomDatabase(){
    abstract fun expenseDao(): ExpenseDao

    companion object {
        const val DATABASE_NAME = "expense_database"

        @JvmStatic
        fun getDatabase(context: Context): ExpenseDataBase {
            return Room.databaseBuilder(
                context,
                ExpenseDataBase::class.java,
                DATABASE_NAME
            ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        InitBasicData(context)
                    }

                    fun InitBasicData(context: Context){
                        CoroutineScope(Dispatchers.IO).launch {
                            val dao = getDatabase(context).expenseDao()
                            dao.insertExpense(ExpenseEntity(1, "Salary", 1200.0, System.currentTimeMillis(), "Work", "Income"))
                            dao.insertExpense(ExpenseEntity(2, "Rent", 400.0, System.currentTimeMillis(), "Starbucks", "Expense"))
                            dao.insertExpense(ExpenseEntity(3, "Groceries", 140.0, System.currentTimeMillis(), "Netflix", "Expense"))
                            dao.insertExpense(ExpenseEntity(4, "Freelancing", 200.0, System.currentTimeMillis(), "Paypal", "Income"))

                        }
                    }
                })
                .build()
        }
    }

}