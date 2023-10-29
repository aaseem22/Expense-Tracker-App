package com.example.expensetrackerapp.room_database

sealed interface DetailEvent{
    object SaveDetail: DetailEvent
    data class SetDate(val date: String):DetailEvent
    data class SetCategory(val category: String):DetailEvent
    data class SetAmount(val amount: String):DetailEvent
    data class SetDesc(val desc: String):DetailEvent
    data class SortDetails(val sortType: SortType):DetailEvent
    data class DeleteDetails(val detail: Details):DetailEvent

    object ShowDialog: DetailEvent
    object HideDialog: DetailEvent



}