package com.example.expensetraker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetraker.data.ExpenseDataBase
import com.example.expensetraker.data.dao.ExpenseDao
import com.example.expensetraker.data.model.ExpenseEntity

class HomeViewModel(dao: ExpenseDao): ViewModel() {
    val expenses = dao.getAllExpenses()

    fun getBalance(list: List<ExpenseEntity>): String{
        var total = 0.0
        list.forEach {
           if(it.type == "Income"){
               total += it.amount
           }else{
               total -= it.amount
           }
        }
        return "$ ${total}"
    }

    fun getTotalExpense(list: List<ExpenseEntity>): String{
        var total = 0.0
        list.forEach {
            if(it.type == "Expense"){
                total += it.amount
            }
        }
        return "$ ${total}"
    }

    fun getTotalIncome(list: List<ExpenseEntity>): String{
        var total = 0.0
        list.forEach {
            if(it.type == "Income"){
                total += it.amount
            }
        }
        return "$ ${total}"

    }

}

class HomeViewModelFactory(private val context: Context): ViewModelProvider.Factory{
    override fun < T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            val dao = ExpenseDataBase.getDatabase(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}