package com.example.expensetrackerapp.login3

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.rememberOneTapSignInState

@Composable
fun OneTapLogIn(){
    val OneTapSignInState = rememberOneTapSignInState()
    val authenticated = remember { mutableStateOf(false) }
    
    OneTapSignInWithGoogle(state = OneTapSignInState,
        clientId ="839462309195-cb1rcplvt31e2dnisk82em0ifjh796m2.apps.googleusercontent.com" ,
        onTokenIdReceived ={tokenId->
            authenticated.value = true
            Log.d("OneTapLogIn",tokenId)
        } ,
        onDialogDismissed ={
                message->
            Log.d("OneTapLogIn",message)
        } )
    
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { OneTapSignInState.open() },
        enabled = !OneTapSignInState.opened
            ) {
            Text(text = "Sign IN")
        }
        Spacer(modifier = Modifier.height(20.dp))
        if(authenticated.value){
            Text(text = "Successfully Authenticated")
        }

    }
}