package com.example.expensetrackerapp.room_database

data class DetailState(
    val details:List<Details> = emptyList(),
    val date: String="",
    var category:  String ="",
    val amount: String="",
    val desc:  String ="",
    val isAddingDetail:Boolean= false,
    val sortType: SortType = SortType.DATE
)
