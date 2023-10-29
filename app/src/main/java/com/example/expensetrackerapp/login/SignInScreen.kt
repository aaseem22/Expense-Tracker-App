package com.example.expensetrackerapp.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetrackerapp.R

@Composable
fun SignINScreen(
    state: SignInState,
    onSignInClick: ()-> Unit
)
{

    val dark_purple = Color(0xFFFFF2D8)
    val mid_purple = Color(0xFFD0BFFF)
    val darkpurple = Color(0xFFBCA37F)
    val bgCOlor = Color(0xFFF8C9)
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError ) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }
Column(modifier =
Modifier
    .fillMaxSize()
    .background(dark_purple),
    // add padding
    verticalArrangement = Arrangement.Top,
horizontalAlignment = Alignment.CenterHorizontally)
{
    Box(modifier = Modifier
        .size(height = 550.dp, width = 800.dp)
        .fillMaxWidth()
        .background(darkpurple),
       contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.accounting),
            contentDescription = null, // Provide a meaningful description for accessibility
            modifier = Modifier.size(350.dp)
        )
    }
    Column(modifier = Modifier.padding(16.dp),
   verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {
  Text(text = "Track your expense with ease", fontSize = 25.sp,
      textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
      )
        Text(text = "The Expense Tracker App", fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Button(
            onClick = onSignInClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .background(Color.Black, shape = RoundedCornerShape(percent = 50)),
            colors = ButtonDefaults.buttonColors(colorResource(id =R.color.black) )
        ) {

            Text(
                text = "Sign In",
                color = Color.White // Set text color to white
            )
        }
    }

}
}

