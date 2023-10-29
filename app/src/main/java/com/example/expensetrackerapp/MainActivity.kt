package com.example.expensetrackerapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.expensetrackerapp.login.GoogleAuthUIClient
import com.example.expensetrackerapp.room_database.DetailDatabase
import com.example.expensetrackerapp.ui.theme.ExpenseTrackerAppTheme
import com.example.expensetrackerapp.viewmodel.CardListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.expensetrackerapp.add_expenses.AddExpenses1
import com.example.expensetrackerapp.data_charts.YourMainComposable
import com.example.expensetrackerapp.expenseslist.CardListScreen2

import com.example.expensetrackerapp.login.ProfileUser
import com.example.expensetrackerapp.login.SignINScreen
import com.example.expensetrackerapp.login.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        GoogleAuthUIClient(
            context = applicationContext,
            oneTapClient = com.google.android.gms.auth.api.identity.Identity.getSignInClient(
                applicationContext
            )
        )
    }
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            DetailDatabase::class.java,
            "details.db"
        ).build()
    }

    private val viewModel by viewModels<CardListViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CardListViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {  val state by viewModel.state.collectAsState()


                    val cardListViewModel = viewModel<CardListViewModel>()
                    val navController1 = rememberNavController()
                    NavHost(navController = navController1, startDestination = "sign_in") {
                        composable("sign_in") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit,) {
                                if (googleAuthUiClient.getSignInUser() != null) {
                                    navController1.navigate("profile")
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuceesful) {
                                if (state.isSignInSuceesful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign In Successful",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    navController1.navigate("profile")
                                    viewModel.resetState()
                                }
                            }
                            SignINScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val sigInIntentSender = googleAuthUiClient.sigIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                sigInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )
                        }
                        composable(
                            "profile",
                        ) {
                            ProfileUser(
                                userData = googleAuthUiClient.getSignInUser(),
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            "sign out",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        navController1.popBackStack()
                                    }
                                },
                                navController = navController1

                            )

                        }

                        //Add other screen Composable
                        composable("cardListScreen") {
                            CardListScreen2(
                                state = state, onEvent = viewModel::onEvent,
                                navController = navController1,

                            )
                        }
                        //form page
                        composable("cardFormScreen2") {
                            AddExpenses1(
                                navController = navController1,
                                state = state,
                                onEvent = viewModel::onEvent,
                                viewModel = cardListViewModel
                            )
                        }
                        //Chart page
                        composable("chartScreen") {
                            YourMainComposable(
                                navController = navController1,
                                viewModel = cardListViewModel
                            )
                        }
                    }


                }
            }
        }

    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ExpenseTrackerAppTheme {
            Greeting("Android")
        }
    }
}