package com.example.expensetrackerapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerapp.data_charts.CategoryAmountData
import com.example.expensetrackerapp.data_charts.ExpenseData
import com.example.expensetrackerapp.expenseslist.CardData
import com.example.expensetrackerapp.room_database.DetailEvent
import com.example.expensetrackerapp.room_database.DetailState
import com.example.expensetrackerapp.room_database.Details
import com.example.expensetrackerapp.room_database.DetailsDAO
import com.example.expensetrackerapp.room_database.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardListViewModel(
    private val dao: DetailsDAO
) : ViewModel() {
    val categoryAmountData = mutableListOf<CategoryAmountData>()

    //newwly addede
     // Initialize with an empty list

    // Add a function to update the data

    ///


    private val _sortType = MutableStateFlow(SortType.DATE)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _details = _sortType
        .flatMapLatest { sortType->
            when(sortType){
                SortType.DATE -> dao.getDetailsByDate()
                SortType.CATEGORY -> dao.getDetailsByCategory()
                SortType.AMOUNT -> dao.getDetailsByAmount()
            }
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

//cardView ViewModel
    val cardList = mutableListOf<CardData>()
    val expenseDataList = mutableStateListOf<ExpenseData>()
    private val _state = MutableStateFlow(DetailState())
    val state = combine(_state,_sortType,_details){
            state,sortType,details ->
        state.copy(
            details =details,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DetailState())
    fun onEvent(event: DetailEvent){
        when(event){
            is DetailEvent.DeleteDetails ->
            {
                viewModelScope.launch {
                    dao.deleteDetails(event.detail)
                }
            }
            DetailEvent.HideDialog->{
                _state.update { it.copy(isAddingDetail = false) }
            }

            DetailEvent.SaveDetail -> {
                val date = state.value.date
                val category = state.value.category
                val amount = state.value.amount
                val desc = state.value.desc

//                if(date.isBlank() ||category.isBlank() ||amount.isBlank() ||desc.isBlank() ){
//                    return
//                }
                val detail = Details(
                    date = date,
                    category = category,
                    amount = amount,
                    desc = desc
                )
                viewModelScope.launch {
                    dao.insetDetails(detail)
                }
                _state.update {
                    it.copy(
                        date="",
                        category = "",
                        amount = "",
                        desc=""

                    )
                }
            }
            is DetailEvent.SetAmount -> {
                _state.update { it.copy(
                    amount = event.amount
                ) }
            }
            is DetailEvent.SetCategory -> {
                _state.update { it.copy(
                    category = event.category
                ) }
            }
            is DetailEvent.SetDate ->  {
                _state.update { it.copy(
                    date = event.date
                ) }
            }
            is DetailEvent.SetDesc ->  {
                _state.update { it.copy(
                    desc = event.desc
                ) }
            }
            is DetailEvent.SortDetails -> {
                _sortType.value = event.sortType
            }

            else -> {}
        }
    }
    fun updateUserEnteredData(category: String, amount: String) {
        _state.update {
            it.copy(
                category = category,
                amount = amount
            )
        }
    }
   fun addCategoryAmountData(category: String, amount: Double) {
    val newData = CategoryAmountData(category, amount)
    categoryAmountData.add(newData)

}
}
