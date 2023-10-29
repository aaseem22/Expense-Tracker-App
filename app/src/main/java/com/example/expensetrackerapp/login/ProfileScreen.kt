package com.example.expensetrackerapp.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.expensetrackerapp.R

@Composable
fun ProfileUser(
    navController: NavController,
    userData : UserData?,
    onSignOut:()-> Unit
) {
    val dark_purple = Color(0xFFBEADFA)
    val mid_purple = Color(0xFFEAD7BB)
    val light_purple = Color(0xFFDFCCFB)
    val bgCOlor = Color(0xFFFFF8C9)
    Column(
        modifier = Modifier
            .fillMaxSize()

            .background(mid_purple),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "")
        if (userData?.profilePicURL != null) {
            AsyncImage(
                model = userData.profilePicURL, contentDescription = "pic",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (userData?.userName != null) {
            Column() {
                Text(
                    text =  "Welcome," ,
                    textAlign = TextAlign.Center,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = userData.userName,
                    textAlign = TextAlign.Center,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
        Row (modifier = Modifier.padding(10.dp)){
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp), // Increase the width (thickness) to 2dp
                color = Color.Black // Change the color to black
            )
        }

Column(modifier = Modifier.fillMaxWidth(),
verticalArrangement = Arrangement.Center,
horizontalAlignment = Alignment.CenterHorizontally) {
    Spacer(modifier = Modifier.height(30.dp))
    Text(text = "Start tracking your expenses effortlessly and save more with just one click",
        fontSize = 15.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Light
        )

    Button(onClick ={
        navController.navigate("cardFormScreen2")
    },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.Black, shape = RoundedCornerShape(percent = 50)),
        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.black))) {
        Text(text = "Lets get started",
            color = Color.White
        )
    }
    
    Spacer(modifier = Modifier.padding(35.dp))
    Text(text = "Leaving soon?",
        fontSize = 15.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Light
    )
    Button(onClick = onSignOut,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.Black, shape = RoundedCornerShape(percent = 50)),
        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.black))
    ) {
        Text(text = "Sign Out",
            color = Color.White)

    }
}
      



    }
}