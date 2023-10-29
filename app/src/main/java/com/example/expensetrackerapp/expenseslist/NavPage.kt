package com.example.expensetrackerapp.expenseslist

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.expensetrackerapp.viewmodel.CardListViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val cardListViewModel = viewModel<CardListViewModel>()

    NavHost(navController, startDestination = "cardFormScreen") {
        composable("cardFormScreen") {
            //AddExpenses(navController=navController,cardListViewModel, state = )
        }
        composable("cardListScreen") {
          //  CardListScreen(navController, cardListViewModel)
        }

    }
}
@Preview
@Composable
fun pre(){
    AppNavigation()
}